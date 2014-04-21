package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;

public class Circle implements ShapePrototype
{
    private String name = null;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Circle clone() throws CloneNotSupportedException {
        return (Circle) super.clone();
    }
    @Override
    public String toString() {
        return "Circle";
    }
}

