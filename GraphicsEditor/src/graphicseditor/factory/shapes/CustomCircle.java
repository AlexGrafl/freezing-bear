package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Circle;

public class CustomCircle extends Circle implements ShapePrototype  {

    public CustomCircle(){
        super(15);
    }

    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public CustomCircle clone() throws CloneNotSupportedException {
        return (CustomCircle) super.clone();
    }

    @Override
    public void setPosition(double x, double y) {
        super.setCenterY(y);
        super.setCenterX(x);
    }

    @Override
    public double getX() {
        return super.getCenterX();
    }

    @Override
    public double getY() {
        return super.getCenterY();
    }

    @Override
    public String toString() {
        return "Circle";
    }
}

