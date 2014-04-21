package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Circle;

public class CustomCircle extends Circle implements ShapePrototype  {

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
    public String toString() {
        return "CustomCircle";
    }
}

