package merp;

import javafx.fxml.FXML;
import merp.Models.Person;
import merp.Models.ProxySingleton;

import java.io.IOException;
import java.util.List;

public class MainController extends AbstractController {

    @FXML private javafx.scene.control.TableView displayTable;

    @FXML
	public void onPresentationModel() throws IOException {
		Person model = getPersonModelFromBusinessLayer();
		showDialog("Samples/PresentationModel.fxml", model, "PresentationModel");
	}
    @FXML
    public void onShowData() throws IOException {
        /*
            Display ResultSet sent by Server
            - Via Proxy
            - display in Table
        */
        List<Contact> contactList = ProxySingleton.getInstance().proxyMain("Grafl");

        for(Contact tmp : contactList) {
            displayTable.getItems().add(tmp);
        }

    }

	private Person getPersonModelFromBusinessLayer() {
		Person result = new Person();
		result.setVorname("Peter");
		result.setNachname("Mayer");
		return result;
	}
}
