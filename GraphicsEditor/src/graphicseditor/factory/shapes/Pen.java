package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;

public class Pen implements ShapePrototype {

    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Pen clone() throws CloneNotSupportedException {
        return (Pen) super.clone();
    }

    @Override
    public String toString() {
        return "Pen";
    }
}
