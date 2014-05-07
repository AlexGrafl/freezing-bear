package merp.Samples;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import merp.Models.Contact;
import merp.Models.Person;
import merp.PresentationModels.AbstractController;
import merp.PresentationModels.PersonModel;

import java.net.URL;
import java.util.ResourceBundle;

public class PresentationModelController extends AbstractController {
	@FXML
	private TextField vorname;
	@FXML
	private TextField nachname;
	@FXML
	private TextField firmenname;
	@FXML
	private TextField UID;
	@FXML
	private CheckBox isFirma;
	@FXML
	private CheckBox disableEditPerson;
	@FXML
	private CheckBox disableEditFirma;
	@FXML
	private TitledPane personPane;
	@FXML
	private TitledPane firmaPane;
	
	private PersonModel presenationModel;
	private Person model;

	@Override
	public void setModel(Object model) {
		this.model = (Person)model;
		this.presenationModel.setModel(this.model);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.presenationModel = new PersonModel();
		applyBindings();
	}
    public void initDialog(Contact result)
    {
        vorname.setText(result.getFirstName());
        nachname.setText(result.getLastName());
        firmenname.setText(result.getName());
        //UID.setText(result.getUid().toString());
    }

	private void applyBindings() {
		vorname.textProperty().bindBidirectional(presenationModel.vornameProperty());
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
				presenationModel.disableEditFirmaBinding());
	}
}
