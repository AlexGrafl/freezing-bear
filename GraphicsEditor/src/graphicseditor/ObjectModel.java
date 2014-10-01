package graphicseditor;

import graphicseditor.factory.ShapePrototype;
import graphicseditor.factory.shapes.Composite;
import graphicseditor.factory.shapes.Pen;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alex
 */
public class ObjectModel {

    private LinkedList<ShapePrototype> selectionList = new LinkedList<ShapePrototype>();
    private LinkedList<ShapePrototype> objectList = new LinkedList<ShapePrototype>();
    private static ObjectModel ourInstance = new ObjectModel();

    public static ObjectModel getInstance() {
        return ourInstance;
    }

    private ObjectModel() {
    }

    public void addShapeToModel(ShapePrototype shape){
        objectList.add(shape);
    }

    public void addObjectToSelection(boolean isCtrlDown){
        for(ShapePrototype shape : objectList){
            if(shape.getClass().isAssignableFrom(Composite.class)) {
                for(ShapePrototype shapePrototype : ((Composite)shape).getShapes()){
                    if(((Shape)shapePrototype).isHover()){
                        if(!isCtrlDown) selectionList.clear();
                        selectionList.add(shape);
                        return;
                    }
                }
                continue;
            }
            if(((Shape)shape).isHover()){
                if(!isCtrlDown) selectionList.clear();
                selectionList.add(shape);
                return;
            }
        }
    }

    public LinkedList<Rectangle> getSelectionBoxes(LinkedList<ShapePrototype> list){
        LinkedList<Rectangle> rectangles = new LinkedList<Rectangle>();
        for(ShapePrototype shape : list){
            if(shape.getClass().isAssignableFrom(Composite.class)) {
                for(Rectangle rectangle : getSelectionBoxes(((Composite)shape).getShapes())){
                    rectangles.add(rectangle);
                }
                continue;
            }
            Bounds bounds = ((Shape)shape).getBoundsInParent();
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


    public LinkedList<ShapePrototype> getModel() {
        return objectList;
    }
    public LinkedList<ShapePrototype> getSelected(){
        return selectionList;
    };

    public boolean isSomethingSelected(){
        return !selectionList.isEmpty();
    }

    public void scaleSelection(double value) {
        for(ShapePrototype shape : selectionList){
            shape.setScale(value);
        }
    }

    public void clearSelection(){
        selectionList.clear();
    }

    public void changeColor(Color color) {
        for(ShapePrototype shape : selectionList){
            shape.setColor(color);
        }
    }

    public void deleteSelection() {
        for(ShapePrototype shape : selectionList){
            objectList.remove(shape);
        }
        clearSelection();
    }

    public void upgroupSelection() {
        for(ShapePrototype shape : selectionList){
            if(shape.getClass().isAssignableFrom(Composite.class)){
                List<ShapePrototype> shapes = ((Composite)shape).getShapes();
                objectList.remove(shape);
                for(ShapePrototype shapePrototype : shapes){
                    objectList.add(shapePrototype);
                }
                clearSelection();
            }
        }
    }

    public void groupSelection() {
        Composite composite = new Composite();
        for(ShapePrototype shapePrototype : selectionList){
            composite.addToComposite(shapePrototype);
            objectList.remove(shapePrototype);
        }
        objectList.add(composite);
        clearSelection();
    }

}
