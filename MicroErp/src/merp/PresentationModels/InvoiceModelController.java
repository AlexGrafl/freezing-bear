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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import merp.Models.Contact;
import merp.Models.Invoice;
import merp.Models.ProxySingleton;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class InvoiceModelController extends AbstractController {

    /* Control */
    @FXML
    Button btnNext, btnCancel;
    /* Textfields */
    @FXML
    private TextField contactText;
    @FXML
    private DatePicker dueDate;
    @FXML
    private TextArea commentTextArea, messageTextArea;
    @FXML
    private Label messageLabel, contactResultLabel;
    @FXML
    private RadioButton radioCompany, radioPerson;

    private Invoice tmpInvoice;
    private ImageView ivValid, ivInvalid;
    private boolean isValid;
    private FoundContactDialogController foundContactDialogController;

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

        tmpInvoice = new Invoice();
        tmpInvoice.setContactID(null);

        ToggleGroup radioGroup = new ToggleGroup();

        radioCompany.setToggleGroup(radioGroup);
        radioCompany.setSelected(true);
        radioPerson.setToggleGroup(radioGroup);

        dueDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        dueDate.getCalendarView().todayButtonTextProperty().set("Today");
        dueDate.getCalendarView().setShowWeeks(false);
        dueDate.getStylesheets().add("/DatePicker.css");


        contactText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                            if(contactText.getText().equals("")) setValid();
                            else {
                                String result = "";
                                if (radioCompany.isSelected())
                                    result = findCompany(contactText.getText(), contactText.getText(),
                                            "", "");
                                if (radioPerson.isSelected()) {
                                    String[] splitStr = contactText.getText().split("\\s+");
                                    String firstName = "", lastName = "";
                                    if (splitStr.length == 1) lastName = splitStr[0];
                                    else {
                                        firstName = splitStr[0];
                                        lastName = splitStr[1];
                                    }
                                    result = findCompany("", "", firstName, lastName);
                                }
                                if (!result.equals("")) contactText.setText(result);
                            }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void setValid(){
        isValid = true;
        contactResultLabel.setGraphic(ivValid);
    }

    private void setInvalid(){
        isValid = false;
        contactResultLabel.setGraphic(ivInvalid);
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
            tmpInvoice.setContactID(companyList.get(0).getContactID());
            if(radioPerson.isSelected()) result = companyList.get(0).getFirstName() + " " + companyList.get(0).getLastName();
            if(radioCompany.isSelected()) result = companyList.get(0).getName();
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

            tmpInvoice.setContactID(tmp.getContactID());
            if(radioPerson.isSelected()) result = tmp.getFirstName()  + " " + tmp.getLastName();
            if(radioCompany.isSelected()) result = tmp.getName();
            setValid();
        }
        return result;
    }
    //ToDo: input-error handling through binding
    @FXML
    private void onNext() throws IOException, ParseException {
        //Todo: Properties binden

        if(isValid) {
            tmpInvoice.setInvoiceID(-1);
            tmpInvoice.setDueDate(dueDate.getSelectedDate());
            tmpInvoice.setMessage(messageTextArea.getText());
            tmpInvoice.setComment(commentTextArea.getText());

            //open InvoiceItem Dialog
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InvoiceItemModel.fxml"));
            Pane root = (Pane)fxmlLoader.load();

            Stage secondStage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            secondStage.setScene(scene);
            secondStage.setTitle("MERP - Contact");

            InvoiceItemModelController controller = fxmlLoader.getController();
            controller.initDialog(tmpInvoice);
            secondStage.show();

            ((Stage) btnNext.getScene().getWindow()).close();
        }
    }

    private Contact openModalDialog(List<Contact> companyList) throws IOException {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(FoundContactDialogController.class.getResource("FoundContactDialog.fxml"));
        Parent root = (Parent)fxmlLoader.load();

        foundContactDialogController = fxmlLoader.getController();
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
    private void onCancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

	private void applyBindings() {

        SimpleStringProperty stringProperty = new SimpleStringProperty();
//        firstName.textProperty().bindBidirectional( stringProperty);

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
