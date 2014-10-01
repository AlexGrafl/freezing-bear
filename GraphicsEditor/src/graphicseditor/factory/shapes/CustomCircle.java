package graphicseditor.factory.shapes;

import graphicseditor.factory.ShapePrototype;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class CustomCircle extends Circle implements ShapePrototype  {

    public CustomCircle(){
        super(15);
    }

    private String name = null;
    private double factor = 1;
    private double initRadius = 7.5;
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
    public CustomCircle clone() throws CloneNotSupportedException {
        return new CustomCircle();
    }

    @Override
    public void setPosition(double x, double y) {
        super.setLayoutX(x - initRadius * factor);
        super.setLayoutY(y - initRadius * factor);
    }

    @Override
    public double getX() {
        return super.getCenterX();
    }

    @Override
    public double getY() {
        return super.getCenterY();
    }

    @Override
    public void setScale(double factor){
        this.factor = factor;
        super.setScaleX(factor);
        super.setScaleY(factor);
    }

    @Override
    public String toString() {
        return "Circle";
    }
}

