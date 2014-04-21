package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle implements ShapePrototype {

    //height = width, aber ka noch wie, man kann die setHeight, setWidth vom parent nicht überschreiben...
    public Square (){
        super(30, 30);
    }

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
    public void setPosition(double x, double y) {
        super.setX(x - (super.getHeight() / 2));
        super.setY(y - (super.getWidth() / 2));
    }

    @Override
    public String toString() {
        return "Square";
    }


}

