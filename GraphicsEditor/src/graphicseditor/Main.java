package graphicseditor;

import graphicseditor.factory.ShapeFactory;
import graphicseditor.factory.ShapePrototype;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


public class Main extends Application {

    Parent root;
    Pane canvasPane;
    Label scaleLabel;
    Slider scaleSlide;


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

        /*
         *  Scale Slider onChange Event
         */
        scaleSlide = (Slider) root.lookup("#scaleSlide");
        scaleLabel = (Label) root.lookup("#scaleValue");
        scaleLabel.setText(Math.round(scaleSlide.getValue()) + " %");

        scaleSlide.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == null) {
                    scaleLabel.setText("");
                    return;
                }
                scaleLabel.setText(Math.round(newValue.intValue()) + "");
                // change currently selected items
            }
        });

        /*
         *  MouseEvent Listener
         */
        canvasPane.setCursor(Cursor.CROSSHAIR);
        canvasPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                canvasPane.getChildren().clear();
                switch (me.getButton()) {

                    case PRIMARY:
                        ColorPicker colorPicker = (ColorPicker) root.lookup("#colorPicker");
                        ComboBox comboBox = (ComboBox) root.lookup("#typeBox");
                        try {
                            Shape shape = (Shape) ShapeFactory.getInstance(comboBox.getValue().toString());
                            shape.setFill(colorPicker.getValue());
                            ((ShapePrototype) shape).setPosition(me.getX(), me.getY());

                            //((ShapePrototype) shape).setScale(3);
                            ((ShapePrototype) shape).setScale((scaleSlide.getValue()) / 100);
                            ObjectModel.getInstance().addShapeToModel(shape);
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }

                        break;
                    case SECONDARY:
                        ObjectModel.getInstance().addObjectToSelection(me.getX(), me.getY(), me.isControlDown());
                        break;
                }
                //(re-)draw all Objects
                drawStuff();
            }

        });
        canvasPane.setOnMouseDragEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                System.out.println("YO");

                switch (me.getButton()) {

                    case SECONDARY:
                        for (Shape tmpShape : ObjectModel.getInstance().getSelected()) addDragExitHandler(tmpShape);
                        break;
                }
            }

        });

    }

    private void addDragExitHandler(final Shape tmpShape){

        tmpShape.setOnMouseDragExited(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent mouseDragEvent) {

                canvasPane.getChildren().clear();

                tmpShape.setLayoutX(mouseDragEvent.getX());
                tmpShape.setLayoutY(mouseDragEvent.getY());
                tmpShape.removeEventHandler(MouseDragEvent.MOUSE_DRAG_EXITED, this );

                drawStuff();
            }
        });
    }
    private void drawStuff(){
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
    public static void main(String[] args) {
        launch(args);
    }

}
