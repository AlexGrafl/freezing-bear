package merp.Samples;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import merp.AbstractController;
import merp.Models.Person;
import merp.PresentationModels.PersonModel;
import javafx.scene.control.TitledPane;

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
