package graphicseditor.factory;


import graphicseditor.factory.shapes.*;

public class ShapeFactory
{
    public static class ModelType
    {
        public static final String CIRCLE = "Circle";
        public static final String ELLIPSE = "Ellipse";
        public static final String RECTANGLE = "Rectangle";
        public static final String SQUARE = "Square";
        public static final String PEN = "Pen";
        public static final String TRIANGLE = "Triangle";
    }

    private static java.util.Map<String , ShapePrototype> prototypes = new java.util.HashMap<String , ShapePrototype>();

    static
    {
        prototypes.put(ModelType.CIRCLE, new CustomCircle());
        prototypes.put(ModelType.ELLIPSE, new CustomEllipse());
        prototypes.put(ModelType.RECTANGLE, new CustomRectangle());
        prototypes.put(ModelType.SQUARE, new Square());
        prototypes.put(ModelType.PEN, new Pen());
        prototypes.put(ModelType.TRIANGLE, new Triangle());
    }

    public static ShapePrototype getInstance(final String s) throws CloneNotSupportedException {
        return ((ShapePrototype) prototypes.get(s)).clone();
    }
}