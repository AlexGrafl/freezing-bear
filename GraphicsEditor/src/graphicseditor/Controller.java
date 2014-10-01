package graphicseditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{


   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void deleteSelection(ActionEvent actionEvent) {
        ObjectModel.getInstance().deleteSelection();
        Main.redrawCanvas();
    }
    @FXML
    public void ungroupSelection(ActionEvent actionEvent) {
        ObjectModel.getInstance().upgroupSelection();
        Main.redrawCanvas();
    }

    @FXML
    public void groupSelection(ActionEvent actionEvent) {
        ObjectModel.getInstance().groupSelection();
        Main.redrawCanvas();
    }
}
