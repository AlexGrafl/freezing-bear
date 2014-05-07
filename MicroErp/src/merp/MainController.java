package merp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import merp.Models.Person;
import merp.PresentationModels.AbstractController;

import java.io.IOException;

public class MainController extends AbstractController {

    @FXML private javafx.scene.control.TableView displayTable;

    @FXML
	public void onPresentationModel() throws IOException {
		Person model = getPersonModelFromBusinessLayer();
		showDialog("Samples/PresentationModel.fxml", model, "PresentationModel");
	}
    @FXML
    public void onShowData() throws IOException {


    }

	private Person getPersonModelFromBusinessLayer() {
		Person result = new Person();
		result.setVorname("Peter");
		result.setNachname("Mayer");
		return result;
	}

    public void onCreateContact(ActionEvent actionEvent) {
    }
}
