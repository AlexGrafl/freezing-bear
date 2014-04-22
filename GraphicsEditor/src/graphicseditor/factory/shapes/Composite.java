package graphicseditor.factory.shapes;

import com.sun.javafx.geom.GeneralShapePair;
import graphicseditor.factory.ShapePrototype;
import javafx.geometry.Bounds;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Alex
 */
public class Composite implements ShapePrototype {

    private LinkedList<ShapePrototype> shapes = new LinkedList<ShapePrototype>();
    private double dragDeltaX;
    private double dragDeltaY;

    public Composite(){
        super();
    }

    @Override
    public ShapePrototype clone() throws CloneNotSupportedException {
        return new Composite();
    }

    @Override
    public void setPosition(double x, double y) {
        //don't need
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public void setScale(double factor) {
        for(ShapePrototype shapePrototype : shapes){
            shapePrototype.setScale(factor);
        }
    }

    @Override
    public double getDragDeltaX() {
        return dragDeltaX;
    }

    @Override
    public void setDragDeltaX(double deltaX) {
        dragDeltaX = deltaX;
    }

    @Override
    public double getDragDeltaY() {
        return dragDeltaY;
    }

    @Override
    public void setDragDeltaY(double deltaY) {
        dragDeltaY = deltaY;
    }

    @Override
    public void setColor(Paint color) {
        for(ShapePrototype shapePrototype : shapes){
            shapePrototype.setColor(color);
        }
    }

    public void addToComposite(ShapePrototype shape){
        shapes.add(shape);
    }

    public void removeFromComposite(ShapePrototype shape){
        shapes.remove(shape);
    }

    public LinkedList<ShapePrototype> getShapes(){
        return shapes;
    }

    public Bounds getBounds() {
             return null;
        //duh
    }
}
