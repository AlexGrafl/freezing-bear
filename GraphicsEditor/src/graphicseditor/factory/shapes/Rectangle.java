package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;

public class Rectangle implements ShapePrototype
{
    private String name = null;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Rectangle clone() throws CloneNotSupportedException {
        return (Rectangle) super.clone();
    }
    @Override
    public String toString() {
        return "Rectangle";
    }
}

