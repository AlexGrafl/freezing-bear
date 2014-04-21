package graphicseditor;

import graphicseditor.factory.ShapeFactory;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
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
                switch(me.getButton()){

                    case PRIMARY:
                        ColorPicker colorPicker = (ColorPicker) root.lookup("#colorPicker");
                        ComboBox comboBox = (ComboBox) root.lookup("#typeBox");
                        try {
                            Shape shape = (Shape) ShapeFactory.getInstance(comboBox.getValue().toString());
                            shape.setFill(colorPicker.getValue());
                            shape.relocate(me.getX(), me.getY());
                            ObjectModel.getInstance().addShapeToModel(shape);
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }

                        break;
                    case SECONDARY:
                        ObjectModel.getInstance().addObjectToSelection(me.getX(), me.getY(), me.isControlDown());
                        break;
                }

          //      ObjectModel.getInstance().drawObjects(canvas.getGraphicsContext2D());
            //    ObjectModel.getInstance().drawSelection(canvas.getGraphicsContext2D());


                //redraw canvas, incl. selection

            }

        });
        return canvas;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
