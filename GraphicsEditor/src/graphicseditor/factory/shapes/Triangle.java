package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Polygon;

public class Triangle extends Polygon implements ShapePrototype {

    public Triangle() {
        super(  15.0, 0.0,
                0.0, 30.0,
                30.0, 30.0);
    }
    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Triangle clone() throws CloneNotSupportedException {
        return (Triangle) super.clone();
    }

    @Override
    public void setPosition(double x, double y) {
        //Todo: its not centered yet!
        super.setLayoutX(x );
        super.setLayoutY(y);
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
        super.setScaleX(factor);
        super.setScaleY(factor);
    }

    @Override
    public String toString() {
        return "Triangle";
    }


}

