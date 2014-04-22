package graphicseditor;

import graphicseditor.factory.ShapeFactory;
import graphicseditor.factory.ShapePrototype;
import graphicseditor.factory.shapes.Composite;
import graphicseditor.factory.shapes.Pen;
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
    private static Pane canvasPane;
    Label scaleLabel;
    Slider scaleSlide;
    ColorPicker colorPicker;
    private Pen tmpPen = null;
    private boolean penMode = false;

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
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == null) {
                    scaleLabel.setText("");
                    return;
                }
                scaleLabel.setText(Math.round(newValue.intValue()) + " %");
                if (ObjectModel.getInstance().isSomethingSelected()) {
                    ObjectModel.getInstance().scaleSelection((double) (newValue.doubleValue() / 100.0));
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
        canvasPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                canvasPane.setCursor(Cursor.CROSSHAIR);
            }
        });
        canvasPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                canvasPane.setCursor(Cursor.CROSSHAIR);
                if (!me.isShiftDown()) {
                    switch (me.getButton()) {

                        case PRIMARY:

                            ColorPicker colorPicker = (ColorPicker) root.lookup("#colorPicker");
                            ComboBox comboBox = (ComboBox) root.lookup("#typeBox");
                            try {
                                ShapePrototype shape = (ShapePrototype) ShapeFactory.getInstance(comboBox.getValue().toString());
                                if(comboBox.getValue().toString().equals("Pen") && tmpPen == null){
                                    tmpPen = new Pen(me.getX(), me.getY(), colorPicker.getValue());
                                    penMode = true;
                                    return;
                                }

                                shape.setColor(colorPicker.getValue());
                                shape.setPosition(me.getX(), me.getY());
                                shape.setScale((splitScaleLabel(scaleLabel.getText())) / 100);
                                ObjectModel.getInstance().addShapeToModel(shape);
                                ObjectModel.getInstance().clearSelection();
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }

                            break;
                        case SECONDARY:
                            ObjectModel.getInstance().addObjectToSelection(me.isControlDown());
                            break;
                    }
                } else {
                    if (ObjectModel.getInstance().isSomethingSelected()) {
                        for (ShapePrototype tmpShape : ObjectModel.getInstance().getSelected()) {
                            if (tmpShape.getClass().isAssignableFrom(Composite.class)) {
                                for (ShapePrototype shapee : ((Composite) tmpShape).getShapes()) {
                                    shapee.setDragDeltaX(((Shape) shapee).getLayoutX() - me.getSceneX());
                                    shapee.setDragDeltaY(((Shape) shapee).getLayoutY() - me.getSceneY());
                                }
                                continue;
                            }
                            tmpShape.setDragDeltaX(((Shape) tmpShape).getLayoutX() - me.getSceneX());
                            tmpShape.setDragDeltaY(((Shape) tmpShape).getLayoutY() - me.getSceneY());
                            canvasPane.setCursor(Cursor.MOVE);
                        }
                    }

                }
                //(re-)draw all Objects
                redrawCanvas();
            }

        });
        canvasPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override

            public void handle(MouseEvent me) {
                ComboBox comboBox = (ComboBox) root.lookup("#typeBox");
                if (me.isShiftDown() && ObjectModel.getInstance().isSomethingSelected()) {
                    canvasPane.setCursor(Cursor.MOVE);
                    for (ShapePrototype tmpShape : ObjectModel.getInstance().getSelected()) {
                        if (tmpShape.getClass().isAssignableFrom(Composite.class)) {
                            for (ShapePrototype shapee : ((Composite) tmpShape).getShapes()) {
                                ((Shape) shapee).setLayoutX(me.getSceneX() + shapee.getDragDeltaX());
                                ((Shape) shapee).setLayoutY(me.getSceneY() + shapee.getDragDeltaY());
                            }
                            continue;
                        }
                        ((Shape) tmpShape).setLayoutX(me.getSceneX() + tmpShape.getDragDeltaX());
                        ((Shape) tmpShape).setLayoutY(me.getSceneY() + tmpShape.getDragDeltaY());
                    }
                }
                else if(comboBox.getValue().toString().equals("Pen") && tmpPen != null && penMode){
                        tmpPen.addPathElement(me.getX(),me.getY());

                        //tmpPen.addPathElement(me.getX(),me.getY());

                }
                redrawCanvas();
            }

        });
        canvasPane.setOnMouseReleased (new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                ComboBox comboBox = (ComboBox) root.lookup("#typeBox");
                if(comboBox.getValue().toString().equals("Pen") && penMode) {
                    canvasPane.getChildren().clear();
                    ObjectModel.getInstance().addShapeToModel(tmpPen);
                    tmpPen = null;
                    penMode = false;
                    redrawCanvas();
                }
            }
        });
    }

    private double splitScaleLabel(String s){
        return Double.parseDouble(s.split(" ")[0]);
    }

    public static void redrawCanvas(){
        canvasPane.getChildren().clear();
        for(ShapePrototype shape : ObjectModel.getInstance().getModel()){
            //redraw everything
            if(shape.getClass().isAssignableFrom(Composite.class)){
                for(ShapePrototype shapee : ((Composite)shape).getShapes()){
                    canvasPane.getChildren().add((Shape) shapee);
                }
            }
            else canvasPane.getChildren().add((Shape)shape);
        }

        for(Rectangle rectangle : ObjectModel.getInstance().getSelectionBoxes(ObjectModel.getInstance().getSelected())){
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
