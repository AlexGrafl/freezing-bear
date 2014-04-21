package graphicseditor;

import graphicseditor.factory.ShapeFactory;
import graphicseditor.factory.ShapePrototype;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


public class Main extends Application {

    Parent root;
    Pane canvasPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("editor.fxml"));
        primaryStage.setTitle("Graphics Editor");
        canvasPane  = (Pane) root.lookup("#canvasPane");
        setUpCanvas();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void setUpCanvas() {
        canvasPane.setCursor(Cursor.CROSSHAIR);
        canvasPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                canvasPane.getChildren().clear();
                switch(me.getButton()){

                    case PRIMARY:
                        ColorPicker colorPicker = (ColorPicker) root.lookup("#colorPicker");
                        ComboBox comboBox = (ComboBox) root.lookup("#typeBox");
                        try {
                            Shape shape = (Shape) ShapeFactory.getInstance(comboBox.getValue().toString());
                            shape.setFill(colorPicker.getValue());
                            ((ShapePrototype) shape).setPosition(me.getX(), me.getY());
                            ObjectModel.getInstance().addShapeToModel(shape);
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }

                        break;
                    case SECONDARY:
                        ObjectModel.getInstance().addObjectToSelection(me.getX(), me.getY(), me.isControlDown());
                        break;
                }
                for(Shape shape : ObjectModel.getInstance().getModel()){
                    //redraw everything
                    canvasPane.getChildren().add(shape);
                }

                for(Rectangle rectangle : ObjectModel.getInstance().getSelectionBoxes()){
                    //selection
                    canvasPane.getChildren().add(rectangle);
                }
                //redraw canvas, incl. selection

            }

        });
    }


    public static void main(String[] args) {
        launch(args);
    }

}
