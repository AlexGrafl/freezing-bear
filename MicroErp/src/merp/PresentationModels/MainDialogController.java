package merp.PresentationModels;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;
import merp.Models.Contact;
import merp.Models.ContactPresentationModel;
import merp.Models.Invoice;
import merp.Models.ProxySingleton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class MainDialogController extends AbstractController {


    /*** javafx elements for Contacts  ***/
    @FXML
    private Button btnCreateContact, btnEditContact, btnSearchContact;
    @FXML
    private TableView<Contact> tableContact;
    @FXML
    private TextField textFirstNameCont, textSurnameCont, textNameCont, textUIDCont;

    /*** javafx elements for Invoices  ***/
    @FXML
    private DatePicker dateStart, dateEnd;
    @FXML
    private TextField textAmountStart, textAmountEnd, textContactInvoice;
    @FXML
    private Label labelContactCheck;
    @FXML
    private RadioButton radioPerson, radioCompany;
    @FXML
    private TableView tableInvoice;
    @FXML
    private Button btnShow, btnCreateInvoice;

    private Integer invoiceContactID;
    private boolean isValid;
    private ImageView ivValid, ivInvalid;
    private ToggleGroup radioGroup;


    ContactPresentationModel contactPresentationModel;
    List<Invoice> invoiceList = new LinkedList<>();

    public MainDialogController(){
    }

    public void initDialog(){

        contactPresentationModel = new ContactPresentationModel();
        applyBindings();

        ToggleGroup radioGroup = new ToggleGroup();

        radioCompany.setToggleGroup(radioGroup);
        radioCompany.setSelected(true);
        radioPerson.setToggleGroup(radioGroup);

        Image invalid = new Image(getClass().getResourceAsStream("/invalid.png"));
        Image valid = new Image(getClass().getResourceAsStream("/valid.png"));

        ivValid = new ImageView();
        ivValid.setImage(valid);
        ivValid.setFitWidth(30);
        ivValid.setPreserveRatio(true);
        ivValid.setSmooth(true);
        ivValid.setCache(true);

        ivInvalid = new ImageView();
        ivInvalid.setImage(invalid);
        ivInvalid.setFitWidth(30);
        ivInvalid.setPreserveRatio(true);
        ivInvalid.setSmooth(true);
        ivInvalid.setCache(true);

        setValid();
        invoiceContactID = null;

        /*** Search Contact ***/
        EventHandler handleContactEnter = new EventHandler<KeyEvent>()  {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        onSearchContact();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        textFirstNameCont.setOnKeyPressed(handleContactEnter);
        textSurnameCont.setOnKeyPressed(handleContactEnter);
        textNameCont.setOnKeyPressed(handleContactEnter);
        textUIDCont.setOnKeyPressed(handleContactEnter);

        /*** Search Invoice ***/
        EventHandler handleInvoiceEnter = new EventHandler<KeyEvent>()  {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        onSearchInvoice();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        textAmountStart.setOnKeyPressed(handleInvoiceEnter);
        textAmountEnd.setOnKeyPressed(handleInvoiceEnter);
        dateStart.setOnKeyPressed(handleInvoiceEnter);
        dateEnd.setOnKeyPressed(handleInvoiceEnter);

        dateStart.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        dateStart.getCalendarView().todayButtonTextProperty().set("Today");
        dateStart.getCalendarView().setShowWeeks(false);
        dateStart.getStylesheets().add("/DatePicker.css");

        dateEnd.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        dateEnd.getCalendarView().todayButtonTextProperty().set("Today");
        dateEnd.getCalendarView().setShowWeeks(false);
        dateEnd.getStylesheets().add("/DatePicker.css");

        textContactInvoice.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                setInvalid();
            }
        });
        textContactInvoice.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        if (radioCompany.isSelected())
                            textContactInvoice.setText(findCompany(textContactInvoice.getText(), textContactInvoice.getText(),
                                    "", ""));
                        if (radioPerson.isSelected()) {
                            String[] splitStr = textContactInvoice.getText().split("\\s+");
                            //Todo: bei eingabe ohne space, nach nachname suchen?
                            textContactInvoice.setText(findCompany("", "", splitStr[0], splitStr[1]));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /*** Contact Validation ***/
        textContactInvoice.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        if(textContactInvoice.getText().equals("")) setValid();
                        else {
                            String result = "";
                            if (radioCompany.isSelected())
                                result = findCompany(textContactInvoice.getText(), textContactInvoice.getText(),
                                        "", "");
                            if (radioPerson.isSelected()) {
                                String[] splitStr = textContactInvoice.getText().split("\\s+");
                                String firstName = "", lastName = "";
                                if (splitStr.length == 1) lastName = splitStr[0];
                                else {
                                    firstName = splitStr[0];
                                    lastName = splitStr[1];
                                }
                                result = findCompany("", "", firstName, lastName);
                            }
                            if (!result.equals("")) textContactInvoice.setText(result);
                            else invoiceContactID = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
    private void applyBindings() {
        textNameCont.textProperty().bindBidirectional(contactPresentationModel.nameProperty());
        textUIDCont.textProperty().bindBidirectional(contactPresentationModel.uidProperty(), new NumberStringConverter());

        textFirstNameCont.textProperty().bindBidirectional( contactPresentationModel.firstNameProperty());
        textSurnameCont.textProperty().bindBidirectional( contactPresentationModel.lastNameProperty());


        textNameCont.disableProperty().bind(contactPresentationModel.disableEditCompanyBinding());
        textUIDCont.disableProperty().bind(contactPresentationModel.disableEditCompanyBinding());
        textFirstNameCont.disableProperty().bind(contactPresentationModel.disableEditPersonBinding());
        textSurnameCont.disableProperty().bind(contactPresentationModel.disableEditPersonBinding());


    }

    /*** Contact ***/
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
    public void onSearchContact() throws IOException {
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

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContactModel.fxml"));
            Pane root = (Pane)fxmlLoader.load();

            Stage secondStage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            secondStage.setScene(scene);
            secondStage.setTitle("MERP - Contact");

            ContactModelController controller = fxmlLoader.getController();
            controller.initDialog(result, isEdit);
            secondStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*** Invoices ***/
    @FXML
    private void onSearchInvoice() throws IOException {
        if(isValid) {
            invoiceList = ProxySingleton.getInstance().searchInvoice(dateStart.getSelectedDate(),
                    dateEnd.getSelectedDate(),textAmountStart.getText().equals("") ? null :
                            Double.parseDouble(textAmountStart.getText()), textAmountEnd.getText().equals("")? null :
                            Double.parseDouble(textAmountEnd.getText()), invoiceContactID);

                ObservableList<Invoice> resultElements = FXCollections.observableArrayList();
                resultElements.addAll(invoiceList);
                tableInvoice.setItems(resultElements);

            tableInvoice.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
                        try {
                            onShowItems();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
    @FXML
    private void onCreateInvoice() throws IOException {
        openInvoiceDialog();
    }

    private void openInvoiceDialog() throws IOException {

        AbstractController controller = showDialog("InvoiceModel.fxml", "MERP - Invoice");
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InvoiceModel.fxml"));
        Pane root = (Pane)fxmlLoader.load();

        Stage secondStage = new Stage(StageStyle.DECORATED);
        Scene scene = new Scene(root, 364, 457);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        secondStage.setScene(scene);
        secondStage.setTitle("MERP - Contact");

        InvoiceModelController controller = fxmlLoader.getController();
        //controller.initDialog(result, isEdit);
        secondStage.show();*/
    }

    private void setValid(){
        isValid = true;
        labelContactCheck.setGraphic(ivValid);
        btnShow.setDisable(false);
        btnCreateInvoice.setDisable(false);
    }

    private void setInvalid(){
        isValid = false;
        labelContactCheck.setGraphic(ivInvalid);
        btnShow.setDisable(true);
        btnCreateInvoice.setDisable(true);
    }
    private String findCompany(String uid, String name, String firstName, String lastName) throws IOException {
        String result = "";

        List<Contact> companyList = ProxySingleton.getInstance().searchContacts(firstName,lastName,name,uid);

        if (companyList.size() == 0){
            setInvalid();
            result = "";
        }
        else if(companyList.size() == 1){
            invoiceContactID = companyList.get(0).getContactID();
            if(radioPerson.isSelected()) result = companyList.get(0).getFirstName() + " " + companyList.get(0).getLastName();
            if(radioCompany.isSelected()) result = companyList.get(0).getName();
            setValid();
        }
        else{

            Contact tmp = openModalDialog(companyList);
            if(tmp.getContactID() == -1){
                setInvalid();
                return "";
            }

            invoiceContactID = tmp.getContactID();
            if(radioPerson.isSelected()) result = tmp.getFirstName()  + " " + tmp.getLastName();
            if(radioCompany.isSelected()) result = tmp.getName();
            setValid();
        }
        return result;
    }
    private Contact openModalDialog(List<Contact> companyList) throws IOException {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(FoundContactDialogController.class.getResource("FoundContactDialog.fxml"));
        Parent root = (Parent)fxmlLoader.load();

        FoundContactDialogController foundContactDialogController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle(radioCompany.isSelected() ? "Companies" : "Persons");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.getStage());
        foundContactDialogController.initDialog(companyList);
        foundContactDialogController.setStage(stage);

        stage.showAndWait();

        return foundContactDialogController.selectedResult;
    }

    @FXML
    private void onShowItems() throws IOException {
        int index = tableInvoice.getSelectionModel().getSelectedIndex();
        if(index >= 0){
            //tableInvoice.getItems().get(index);
            openDisplayInvoiceDialog(invoiceList.get(index));
        }
    }

    private void openDisplayInvoiceDialog(Invoice invoice) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DisplayInvoice.fxml"));
        Pane root = (Pane)fxmlLoader.load();

        Stage secondStage = new Stage(StageStyle.DECORATED);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        secondStage.setScene(scene);
        secondStage.setTitle("MERP - Display Invoice");

        DisplayInvoiceController controller = fxmlLoader.getController();
        controller.initDialog(invoice);
        secondStage.show();

    }


}
