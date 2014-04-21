package graphicseditor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

    Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("editor.fxml"));
        primaryStage.setTitle("Graphics Editor");
        StackPane canvasPane = (StackPane) root.lookup("#canvasPane");
        canvasPane.getChildren().add(setUpCanvas(primaryStage));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private Canvas setUpCanvas(Stage primaryStage) {
        final Canvas canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
        canvas.setCursor(Cursor.CROSSHAIR);
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                ColorPicker colorPicker = (ColorPicker) root.lookup("#colorPicker");
                GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                graphicsContext.setFill(colorPicker.getValue());
                graphicsContext.fillRect(me.getX(), me.getY(), 20, 20);
            }
        });
        return canvas;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
