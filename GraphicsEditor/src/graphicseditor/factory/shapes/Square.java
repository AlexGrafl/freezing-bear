package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle implements ShapePrototype {

    //height = width, aber ka noch wie, man kann die setHeight, setWidth vom parent nicht überschreiben...
    public Square (){
        super(30, 30);
    }

    private String name = null;
    private double factor = 1;
    private double initRadius = 15;
    private double dragDeltaX;
    private double dragDeltaY;

    public double getDragDeltaX(){
        return this.dragDeltaX;
    }
    public void setDragDeltaX(double deltaX){
        this.dragDeltaX = deltaX;
    }
    public double getDragDeltaY(){
        return this.dragDeltaY;
    }
    public void setDragDeltaY(double deltaY){
        this.dragDeltaY = deltaY;
    }

    @Override
    public void setColor(Paint color) {
        this.setFill(color);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Square clone() throws CloneNotSupportedException {
        return new Square();
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
        return "Square";
    }


}

