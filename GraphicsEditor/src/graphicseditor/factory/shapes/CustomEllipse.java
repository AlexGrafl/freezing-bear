package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Ellipse;

public class CustomEllipse extends Ellipse implements ShapePrototype {

    public CustomEllipse(){
        super(40, 20);
    }
    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public CustomEllipse clone() throws CloneNotSupportedException {
        return (CustomEllipse) super.clone();
    }

    @Override
    public void setPosition(double x, double y) {
        super.setCenterX(x);
        super.setCenterY(y);
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
        return "Ellipse";
    }
}

