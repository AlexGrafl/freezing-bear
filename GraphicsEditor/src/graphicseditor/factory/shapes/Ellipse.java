package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;

public class Ellipse implements ShapePrototype
{
    private String name = null;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Ellipse clone() throws CloneNotSupportedException {
        return (Ellipse) super.clone();
    }
    @Override
    public String toString() {
        return "Ellipse";
    }
}

