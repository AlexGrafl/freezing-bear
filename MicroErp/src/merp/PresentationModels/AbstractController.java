package merp.PresentationModels;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractController implements Initializable {
	private Stage stage;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage temp) {
		stage = temp;
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
	}

	private AbstractController show(String resource, Object model, String title, Modality m,
			String... cssList) throws IOException {
		FXMLLoader fl = new FXMLLoader();
		fl.setLocation(getClass().getResource(resource));
		fl.load();
		Parent root = fl.getRoot();

		Stage newStage = new Stage(StageStyle.DECORATED);
		newStage.initModality(m);
		newStage.initOwner(this.getStage());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(
				getClass().getResource("/application.css").toExternalForm());
		// more css by code
		for (String css : cssList) {
			scene.getStylesheets().add(
					getClass().getResource(css).toExternalForm());
		}

		this.setStage(newStage);
		this.setModel(model);
		newStage.setScene(scene);
		newStage.setTitle(title);
        newStage.show();
        return fl.getController();
	}


	public AbstractController show(String resource, Object model, String title, String... cssList) throws IOException {
		return show(resource, model, title, Modality.NONE, cssList);
	}

	public AbstractController showDialog(String resource, Object model, String title, String... cssList) throws IOException {
		return show(resource, model, title, Modality.APPLICATION_MODAL, cssList);
	}

	public AbstractController show(String resource, String title, String... cssList) throws IOException {
		return show(resource, null, title, Modality.NONE, cssList);
	}

	public AbstractController showDialog(String resource, String title, String... cssList) throws IOException {
		return show(resource, null, title, Modality.APPLICATION_MODAL, cssList);
	}

	public void setModel(Object model) {
		// optional set model in derived classes
	}

}
