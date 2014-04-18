package graphicseditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    @FXML
    private ScrollPane canvasPane;

    Canvas canvas;



   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       canvas = new Canvas(300, 250);
       canvasPane.setContent(canvas);
       GraphicsContext gc = canvas.getGraphicsContext2D();
    }


}
