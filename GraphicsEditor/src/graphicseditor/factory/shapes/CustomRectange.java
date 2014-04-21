package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Rectangle;

public class CustomRectange extends Rectangle implements ShapePrototype {

    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public CustomRectange clone() throws CloneNotSupportedException {
        return (CustomRectange) super.clone();
    }

    @Override
    public String toString() {
        return "CustomRectange";
    }
}

