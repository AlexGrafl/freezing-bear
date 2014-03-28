package merp;

import java.io.IOException;

import merp.Models.Person;
import javafx.fxml.FXML;

public class MainController extends AbstractController {

    @FXML
	public void onPresentationModel() throws IOException {
		Person model = getPersonModelFromBusinessLayer();
		showDialog("Samples/PresentationModel.fxml", model, "PresentationModel");
	}

	private Person getPersonModelFromBusinessLayer() {
		Person result = new Person();
		result.setVorname("Peter");
		result.setNachname("Mayer");
		return result;
	}
}
