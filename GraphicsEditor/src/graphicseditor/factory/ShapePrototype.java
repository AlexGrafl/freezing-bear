package graphicseditor.factory;

public interface ShapePrototype extends Cloneable {

    public ShapePrototype clone() throws CloneNotSupportedException;

}