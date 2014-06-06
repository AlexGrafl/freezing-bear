package merp.PresentationModels;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import merp.Models.Contact;
import merp.Models.ProxySingleton;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ContactModelController extends AbstractController {
    /* Control */
    @FXML
    Button btnSave, btnCancel;
    /* Textfields */
    @FXML
    private TextField uid;
    @FXML
    private TextField name;
    @FXML
    private TextField title;
    @FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
    @FXML
    private TextField suffix;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField companyText;
    @FXML
    private TextField address;
    @FXML
    private TextField invoiceAddress;
    @FXML
    private TextField shippingAddress;
    @FXML
    private Label messageLabel, companyResultLabel;

    private ImageView ivValid, ivInvalid;
    private boolean isValid;
    private Contact resultContact;

	@Override
	public void setModel(Object model) {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		applyBindings();

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

        companyText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        if(companyText.getText().equals("")) setValid();
                        else {
                            String result = "";
                                result = findCompany(companyText.getText(), companyText.getText(),
                                        "", "");
                            if (!result.equals("")) companyText.setText(result);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        birthDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        birthDate.getCalendarView().todayButtonTextProperty().set("Today");
        birthDate.getCalendarView().setShowWeeks(false);
        birthDate.getStylesheets().add("/DatePicker.css");

	}

    public void initDialog(Contact result, boolean isEdit)
    {
        this.resultContact = result;

        if(isEdit){
            if(result.getUid() != null) {
                uid.setText(result.getUid().toString());
            }
            name.setText(result.getName());
            title.setText(result.getTitle());
            firstName.setText(result.getFirstName());
            lastName.setText(result.getLastName());
            suffix.setText(result.getSuffix());
            if(result.getBirthDate() != null){
                birthDate.setSelectedDate(result.getBirthDate());
            }else{
                birthDate.setSelectedDate(null);
            }
            address.setText(result.getAddress());
            invoiceAddress.setText(result.getInvoiceAddress());
            shippingAddress.setText(result.getShippingAddress());
        }
        else {
            this.resultContact.setContactID(-1);
        }
        this.resultContact = result;
    }

    private void setValid(){
        isValid = true;
        companyResultLabel.setGraphic(ivValid);
    }

    private void setInvalid(){
        isValid = false;
        companyResultLabel.setGraphic(ivInvalid);
    }
    private String findCompany(String uid, String name, String firstName, String lastName) throws IOException {
        String result = "";

        List<Contact> companyList = ProxySingleton.getInstance().searchContacts(firstName,lastName,name,uid);

        if (companyList.size() == 0){
            messageLabel.setText("Company not found!");
            setInvalid();
            result = "";
        }
        else if(companyList.size() == 1){
            resultContact.setCompanyID(companyList.get(0).getContactID());
            result = companyList.get(0).getName();
            setValid();
            messageLabel.setText("1 Result found!");
        }
        else{
            messageLabel.setText(Integer.toString(companyList.size()) + " Results found!");

            Contact tmp = openModalDialog(companyList);
            if(tmp.getContactID() == -1){
                setInvalid();
                return "";
            }

            resultContact.setCompanyID(tmp.getContactID());
            result = tmp.getName();
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
        stage.setTitle("Companies");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.getStage());
        foundContactDialogController.initDialog(companyList);
        foundContactDialogController.setStage(stage);

        stage.showAndWait();

        return foundContactDialogController.selectedResult;
    }
    //ToDo: input-error handling
    @FXML
    private void onSave() throws IOException, ParseException {
        //Todo: Properties binden
        resultContact.setUid(uid.getText().equals("") ? null : Integer.parseInt(uid.getText()));
        resultContact.setName(name.getText().equals("") ? null : name.getText());
        resultContact.setTitle(title.getText().equals("") ? null : title.getText());
        resultContact.setFirstName(firstName.getText().equals("") ? null : firstName.getText());
        resultContact.setLastName(lastName.getText().equals("") ? null : lastName.getText());
        resultContact.setBirthDate(birthDate.getSelectedDate());
        resultContact.setSuffix(suffix.getText().equals("") ? null : suffix.getText());
        resultContact.setAddress(address.getText().equals("") ? null : address.getText());
        resultContact.setInvoiceAddress(invoiceAddress.getText().equals("") ? null : invoiceAddress.getText());
        resultContact.setShippingAddress(shippingAddress.getText().equals("") ? null : shippingAddress.getText());

        if(this.resultContact.getContactID() == -1){
           ProxySingleton.getInstance().createContact(resultContact);
        }
        else {
            ProxySingleton.getInstance().editContact(resultContact);
        }
        ((Stage)btnSave.getScene().getWindow()).close();
    }
    @FXML
    private void onCancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

	private void applyBindings() {

        SimpleStringProperty stringProperty = new SimpleStringProperty();
        // Todo: disable fields (e.g. Name for Person and Firstname for Firm)
        //firstName.textProperty().bindBidirectional( stringProperty);

		/*vorname.textProperty().bindBidirectional(presenationModel.vornameProperty());
		nachname.textProperty().bindBidirectional(presenationModel.nachnameProperty());
		firmenname.textProperty().bindBidirectional(presenationModel.firmennameProperty());
		UID.textProperty().bindBidirectional(presenationModel.UIDProperty());

		isFirma.selectedProperty().bind(presenationModel.isFirmaBinding());
		disableEditPerson.selectedProperty().bind(
				presenationModel.disableEditPersonBinding());
		disableEditFirma.selectedProperty().bind(
				presenationModel.disableEditFirmaBinding());
		
		personPane.disableProperty().bind(
				presenationModel.disableEditPersonBinding());
		firmaPane.disableProperty().bind(
				presenationModel.disableEditFirmaBinding()); */
	}
}
