package graphicseditor;

import graphicseditor.factory.ShapePrototype;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import java.util.LinkedList;

/**
 * @author Alex
 */
public class ObjectModel {

    private LinkedList<Shape> selectionList = new LinkedList<Shape>();
    private LinkedList<Shape> objectList = new LinkedList<Shape>();
    private static ObjectModel ourInstance = new ObjectModel();

    public static ObjectModel getInstance() {
        return ourInstance;
    }

    private ObjectModel() {
    }

    public void addShapeToModel(Shape shape){
        objectList.add(shape);
    }

    public void addObjectToSelection(double x, double y, boolean isCtrlDown){
        for(Shape shape : objectList){
            if(shape.isHover()){
                if(!isCtrlDown) selectionList.clear();
                selectionList.add(shape);
                return;
            }
        }
    }

    public LinkedList<Rectangle> getSelectionBoxes(){
        LinkedList<Rectangle> rectangles = new LinkedList<Rectangle>();
        for(Shape shape : selectionList){
            Bounds bounds = shape.getBoundsInParent();
            Rectangle selection = new Rectangle(bounds.getMaxX() - (bounds.getMaxX() - bounds.getMinX()),
                    bounds.getMaxY() - (bounds.getMaxY() - bounds.getMinY()),
                    bounds.getWidth(), bounds.getHeight());
            selection.setFill(new Color(0, 0, 0, 0));
            selection.setStroke(Color.BLACK);
            selection.setStrokeType(StrokeType.CENTERED);
            selection.getStrokeDashArray().add(3.0);
            rectangles.add(selection);
        }
        return rectangles;
    }


    public LinkedList<Shape> getModel() {
        return objectList;
    }
    public LinkedList<Shape> getSelected(){
        return selectionList;
    };

    public boolean isSomethingSelected(){
        return !selectionList.isEmpty();
    }

    public void scaleSelection(double value) {
        for(Shape shape : selectionList){
            ((ShapePrototype)shape).setScale(value);
        }
    }

    public void clearSelection(){
        selectionList.clear();
    }

    public void changeColor(Color color) {
        for(Shape shape : selectionList){
            shape.setFill(color);
        }
    }
}
