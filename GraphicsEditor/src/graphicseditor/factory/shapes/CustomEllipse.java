package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Ellipse;

public class CustomEllipse extends Ellipse implements ShapePrototype {

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
    public String toString() {
        return "CustomEllipse";
    }
}

