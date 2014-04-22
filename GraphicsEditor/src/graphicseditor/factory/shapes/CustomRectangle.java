package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Rectangle;

public class CustomRectangle extends Rectangle implements ShapePrototype {

    public CustomRectangle(){
        super(40, 20);
        factor = 1;
    }
    private String name = null;
    private double factor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public CustomRectangle clone() throws CloneNotSupportedException {
        return (CustomRectangle) super.clone();
    }

    @Override
    public void setPosition(double x, double y) {
        super.setX(x - (super.getWidth() / 2) * factor);
        super.setY(y - (super.getHeight() / 2) * factor);
    }

    @Override
    public void setScale(double factor){
        this.factor = factor;
        super.setScaleX(factor);
        super.setScaleY(factor);
    }

    @Override
    public String toString() {
        return "Rectangle";
    }
}

