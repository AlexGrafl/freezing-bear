package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Rectangle;

public class CustomRectangle extends Rectangle implements ShapePrototype {

    public CustomRectangle(){
        super(40, 20);
    }
    private String name = null;
    private double factor = 1;
    private double initRadius = 20;

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
    public String toString() {
        return "Rectangle";
    }
}

