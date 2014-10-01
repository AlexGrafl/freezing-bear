package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Pen extends Path implements ShapePrototype {

    private String name = null;
    private double factor = 1;
    private double initRadius = 3;
    private double dragDeltaX;
    private double dragDeltaY;
    //private Path path;

    public Pen(double x, double y, Color value) {
        super();
        super.setStrokeWidth(initRadius);
        super.setStroke(value);
        super.getElements().clear();
        super.getElements()
                .add(new MoveTo(x, y));
    }

    public double getDragDeltaX(){
        return this.dragDeltaX;
    }
    public void setDragDeltaX(double deltaX){
        this.dragDeltaX = deltaX;
    }
    public double getDragDeltaY(){
        return this.dragDeltaY;
    }
    public void setDragDeltaY(double deltaY){
        this.dragDeltaY = deltaY;
    }

    @Override
    public void setColor(Paint color) {
        super.setStroke(color);
        super.setFill(null);
    }

    public void addPathElement(double x, double y){
        super.getElements()
                .add(new LineTo(x - initRadius * factor, y - initRadius * factor));
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Pen clone() throws CloneNotSupportedException {
        return new Pen(initRadius, initRadius, Color.AQUA);
    }

    @Override
    public void setPosition(double x, double y) {
        super.setLayoutX(x - initRadius * factor);
        super.setLayoutY(y - initRadius * factor);

    }
    @Override
    public void setScale(double factor){
        this.factor = factor;
        super.setScaleX(factor);
        super.setScaleY(factor);
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
    public String toString() {
        return "Pen";
    }
}

