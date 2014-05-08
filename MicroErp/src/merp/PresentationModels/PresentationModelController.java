package merp.PresentationModels;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import merp.Models.Contact;
import merp.Models.ProxySingleton;

import javax.ejb.Init;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class PresentationModelController extends AbstractController {
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
    private TextField address;
    @FXML
    private TextField invoiceAddress;
    @FXML
    private TextField shippingAddress;

    private Contact result;

	@Override
	public void setModel(Object model) {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		applyBindings();
        birthDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        birthDate.getCalendarView().todayButtonTextProperty().set("Today");
        birthDate.getCalendarView().setShowWeeks(false);
        birthDate.getStylesheets().add("merp/DatePicker.css");

	}
    public void initDialog(Contact result, boolean isEdit)
    {
        this.result = result;

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
            this.result.setContactID(-1);
        }



        this.result = result;
    }

    //ToDo: input-error handling
    @FXML
    private void onSave() throws IOException, ParseException {
        //Todo: Properties binden
        result.setUid(uid.getText().equals("") ? null : Integer.parseInt(uid.getText()));
        result.setName(name.getText().equals("") ? null : name.getText());
        result.setTitle(title.getText().equals("") ? null : title.getText());
        result.setFirstName(firstName.getText().equals("") ? null : firstName.getText());
        result.setLastName(lastName.getText().equals("") ? null : lastName.getText());
        result.setBirthDate(birthDate.getSelectedDate());
        result.setSuffix(suffix.getText().equals("") ? null : suffix.getText());
        result.setAddress(address.getText().equals("") ? null : address.getText());
        result.setInvoiceAddress(invoiceAddress.getText().equals("") ? null : invoiceAddress.getText());
        result.setShippingAddress(shippingAddress.getText().equals("") ? null : shippingAddress.getText());

        if(this.result.getContactID() == -1){
           ProxySingleton.getInstance().createContact(result);
        }
        else {
            ProxySingleton.getInstance().editContact(result);
        }
        ((Stage)btnSave.getScene().getWindow()).close();
    }
    @FXML
    private void onCancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

	private void applyBindings() {

        // Todo: disable fields (e.g. Name for Person and Firstname for Firm)
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
