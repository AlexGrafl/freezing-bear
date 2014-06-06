package merp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import merp.PresentationModels.MainDialogController;


public class Client extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PresentationModels/MainDialog.fxml"));
            TabPane root = (TabPane)fxmlLoader.load();

            primaryStage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root,400,515);
			scene.getStylesheets().add(getClass().getResource("" +
                    "/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("MERP");

            MainDialogController controller = fxmlLoader.getController();
            controller.initDialog();

			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
