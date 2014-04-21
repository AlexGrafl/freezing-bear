package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;

public class Square implements ShapePrototype
{
    private String name = null;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Square clone() throws CloneNotSupportedException {
        return (Square) super.clone();
    }
    @Override
    public String toString() {
        return "Square";
    }
}

