package merp.PresentationModels;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import merp.Models.Contact;
import merp.Models.ProxySingleton;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private TextField birthDate;
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
	}
    public void initDialog(Contact result, boolean isEdit)
    {
        this.result = result;

        if(isEdit){
            String date;
            //uid.setText(result.getUid().toString());
            name.setText(result.getName());
            title.setText(result.getTitle());
            firstName.setText(result.getFirstName());
            lastName.setText(result.getLastName());
            suffix.setText(result.getSuffix());
            try{
                date = result.getBirthDate().toString();

            }catch(NullPointerException e){
                date="";
            }
            birthDate.setText(date);
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
        DateFormat df = new SimpleDateFormat("MM dd, yyyy HH:mm:ss a");

        result.setUid(Integer.parseInt(uid.getText()));
        result.setName(name.getText());
        result.setTitle(title.getText());
        result.setFirstName(firstName.getText());
        result.setLastName(lastName.getText());
        //result.setBirthDate(df.parse(birthDate.getText()));
        result.setAddress(address.getText());
        result.setInvoiceAddress(invoiceAddress.getText());
        result.setShippingAddress(shippingAddress.getText());

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
