package graphicseditor.factory;

public interface ShapePrototype extends Cloneable {

    public ShapePrototype clone() throws CloneNotSupportedException;
    public void setPosition(double x, double y);
    public double getX();
    public double getY();
    public void setScale(double factor);
    public double getDragDeltaX();
    public void setDragDeltaX(double deltaX);
    public double getDragDeltaY();
    public void setDragDeltaY(double deltaY);
}