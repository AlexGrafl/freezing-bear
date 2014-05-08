package merp.PresentationModels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import merp.Models.Contact;
import merp.Models.ProxySingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainDialogController extends AbstractController {


    /*** javafx elements for Contacts  ***/
    @FXML
    private Button btnCreateContact, btnEditContact, btnSearchContact;
    @FXML
    private TableView<Contact> tableContact;
    @FXML
    private TextField textFirstNameCont, textSurnameCont, textNameCont, textUIDCont;
   /*
    @FXML
    TableColumn itemFirstNameCol, itemLastNameCol, itemNameCol, itemUIDCol, itemShippingAddressCol;      */

    /*** javafx elements for Invoices  ***/

    public MainDialogController(){
    }


    @FXML
	public void onCreateContact() throws IOException {
        openContactDialog(new Contact(),false);
    }
    @FXML
    public void onEditContact() throws IOException {
        int index = tableContact.getSelectionModel().getSelectedIndex();
        if(index >= 0){
            openContactDialog((tableContact.getItems().get(index)),true);
        }
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

        ObservableList<Contact> resultElements = FXCollections.observableArrayList();
        resultElements.addAll(contactList);
        tableContact.setItems(resultElements);
        tableContact.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
                    int index = tableContact.getSelectionModel().getSelectedIndex();
                    if(index >= 0){
                        openContactDialog((contactList.get(index)),true);
                    }
                }
            }
        });
    }
    private void openContactDialog(Contact result, boolean isEdit) {
        if(result == null) result = new Contact();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PresentationModel.fxml"));
            Pane root = (Pane)fxmlLoader.load();

            Stage secondStage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root, 364, 457);
            scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
            secondStage.setScene(scene);
            secondStage.setTitle("MERP - Contact");

            PresentationModelController controller = fxmlLoader.getController();
            controller.initDialog(result, isEdit);
            secondStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
