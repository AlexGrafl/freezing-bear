package graphicseditor;

import graphicseditor.factory.ShapeFactory;
import graphicseditor.factory.ShapePrototype;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


public class Main extends Application {

    Parent root;
    Pane canvasPane;
    Label scaleLabel;
    Slider scaleSlide;
    ColorPicker colorPicker;

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

        scaleSlide = lookupInToolBar((ToolBar) root.lookup("#toolBar"), "#scaleSlide", Slider.class);
        scaleLabel = lookupInToolBar((ToolBar) root.lookup("#toolBar"), "#scaleValue", Label.class);
        colorPicker =  lookupInToolBar((ToolBar) root.lookup("#toolBar"), "#colorPicker", ColorPicker.class);
        scaleLabel.setText(Math.round(scaleSlide.getValue()) + " %");
        scaleSlide.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == null) {
                    scaleLabel.setText("");
                    return;
                }
                scaleLabel.setText(Math.round(newValue.intValue()) + " %");
                if(ObjectModel.getInstance().isSomethingSelected()){
                    ObjectModel.getInstance().scaleSelection((double)(newValue.doubleValue() / 100.0));
                    redrawCanvas();
                }
                // change currently selected items
            }
        });

        colorPicker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color color2) {
                if(ObjectModel.getInstance().isSomethingSelected()){
                    ObjectModel.getInstance().changeColor(color2);
                    redrawCanvas();
                }
            }
        });

        /*
         *  MouseEvent Listener
         */
        canvasPane.setCursor(Cursor.CROSSHAIR);
        canvasPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                switch (me.getButton()) {

                    case PRIMARY:

                        ColorPicker colorPicker = (ColorPicker) root.lookup("#colorPicker");
                        ComboBox comboBox = (ComboBox) root.lookup("#typeBox");
                        try {
                            Shape shape = (Shape) ShapeFactory.getInstance(comboBox.getValue().toString());
                            shape.setFill(colorPicker.getValue());
                            ((ShapePrototype) shape).setPosition(me.getX(), me.getY());

                            //((ShapePrototype) shape).setScale(3);
                            ((ShapePrototype) shape).setScale((splitScaleLabel(scaleLabel.getText())) / 100);
                            ObjectModel.getInstance().addShapeToModel(shape);
                            ObjectModel.getInstance().clearSelection();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }

                        break;
                    case SECONDARY:
                        ObjectModel.getInstance().addObjectToSelection(me.getX(), me.getY(), me.isControlDown());
                        break;
                }
                //(re-)draw all Objects
                redrawCanvas();
            }

        });
    }
      /*
        canvasPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
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



    private void addDragExitHandler(final Shape tmpShape){

        tmpShape.setOnMouseDragExited(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent mouseDragEvent) {

                canvasPane.getChildren().clear();

                tmpShape.setLayoutX(mouseDragEvent.getX());
                tmpShape.setLayoutY(mouseDragEvent.getY());
                tmpShape.removeEventHandler(MouseDragEvent.MOUSE_DRAG_EXITED, this );

                redrawCanvas();
            }
        });
    }
    */

    private double splitScaleLabel(String s){
        return Double.parseDouble(s.split(" ")[0]);
    }
    private void redrawCanvas(){
        canvasPane.getChildren().clear();
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

    public <T> T lookupInToolBar(ToolBar parent, String id, Class<T> clazz) {
        for (Node node : parent.getItems()) {
            if (node.getClass().isAssignableFrom(clazz)) {
                return (T)node;
            }
        }
        throw new IllegalArgumentException("Parent " + parent + " doesn't contain node with id " + id);
    }
}
