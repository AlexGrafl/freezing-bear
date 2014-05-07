package merp.PresentationModels;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import merp.Models.Contact;
import merp.Models.ContactModelBind;
import merp.Models.ContactTableModel;
import merp.Models.ProxySingleton;
import merp.Samples.PresentationModelController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainDialogController extends AbstractController {


    /*** javafx elements for Contacts  ***/
    @FXML
    private Button btnCreateContact, btnEditContact, btnSearchContact;
    @FXML
    private TableView<ContactTableModel> tableContact;
    @FXML
    private TextField textFirstNameCont, textSurnameCont, textNameCont, textUIDCont;
   /*
    @FXML
    TableColumn itemFirstNameCol, itemLastNameCol, itemNameCol, itemUIDCol, itemShippingAddressCol;      */

    /*** javafx elements for Invoices  ***/

    public MainDialogController(){
        /* init tableview here? */
        // Set up the invoice table
        /*ObservableList<Contact> teamMembers = ...;
        table.setItems(teamMembers);

        TableColumn<Person,String> firstNameCol = new TableColumn<Person,String>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Person,String> lastNameCol = new TableColumn<Person,String>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));

        table.getColumns().setAll(firstNameCol, lastNameCol);           */
    }


    @FXML
	public void onCreateContact() throws IOException {
        showDialog("Samples/PresentationModel.fxml", "Create Contact");
    }
    @FXML
    public void onEditContact() throws IOException {
        Contact object = new Contact();
        showDialog("Samples/PresentationModel.fxml", object, "Edit Contact");
    }
    @FXML
    public void onSearchContact(ActionEvent actionEvent) throws IOException {
        /*
            Display ResultSet sent by Server
            - Via Proxy
            - display result in Table
        */
        final List<Contact> contactList = ProxySingleton.getInstance().searchContacts(textFirstNameCont.getText(),
                textSurnameCont.getText(), textNameCont.getText(), textUIDCont.getText());

        ContactModelBind modelBind = new ContactModelBind((ArrayList)contactList);
        ObservableList<ContactTableModel> resultElements = modelBind.getItems();

        tableContact.setItems(resultElements);
        tableContact.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
                    int index = tableContact.getSelectionModel().getSelectedIndex();
                    if(index >= 0){
                        openCustomerWindow((contactList.get(index)));
                    }
                }
            }
        });
    }
    private void openCustomerWindow(Contact result) {
        if(result == null) return;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Samples/PresentationModel.fxml"));
            TabPane root = (TabPane)fxmlLoader.load();

            Stage secondStage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root, 550, 700);
            scene.getStylesheets().add(getClass().getResource("/merp/application.css").toExternalForm());
            secondStage.setScene(scene);
            secondStage.setTitle("Contact");

            PresentationModelController controller = fxmlLoader.<PresentationModelController>getController();
            controller.initDialog(result);
            secondStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
