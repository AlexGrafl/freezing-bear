package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Polygon;

public class Triangle extends Polygon implements ShapePrototype {

    public Triangle() {
        super(  15.0, 0.0,
                0.0, 30.0,
                30.0, 30.0);
        factor = 1;
    }
    private String name = null;
    private double initRadius = 15;
    private double factor;
    private double dragDeltaX;
    private double dragDeltaY;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Triangle clone() throws CloneNotSupportedException {
        return new Triangle();
    }

    @Override
    public void setPosition(double x, double y) {
        super.setLayoutX(x - initRadius * factor);
        super.setLayoutY(y - initRadius * factor);
    }

    @Override
    public double getX() {
        return super.getLayoutX();
    }

    @Override
    public double getY() {
        return super.getLayoutY();
    }

    @Override
    public void setScale(double factor){
        this.factor = factor;
        super.setScaleX(factor);
        super.setScaleY(factor);
    }

    @Override
    public String toString() {
        return "Triangle";
    }


}

