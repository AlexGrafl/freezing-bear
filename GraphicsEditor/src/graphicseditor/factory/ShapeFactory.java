package graphicseditor.factory;


import graphicseditor.factory.shapes.*;

public class ShapeFactory
{
    public static class ModelType
    {
        public static final String Circle = "Circle";
        public static final String Ellipse = "Ellipse";
        public static final String Rectangle = "Rectange";
        public static final String Square = "Square";
        public static final String Pen = "Pen";
    }

    private static java.util.Map<String , ShapePrototype> prototypes = new java.util.HashMap<String , ShapePrototype>();

    static
    {
        prototypes.put(ModelType.Circle, new CustomCircle());
        prototypes.put(ModelType.Ellipse, new CustomEllipse());
        prototypes.put(ModelType.Rectangle, new CustomRectange());
        prototypes.put(ModelType.Square, new Square());
        prototypes.put(ModelType.Pen, new Pen());
    }

    public static ShapePrototype getInstance(final String s) throws CloneNotSupportedException {
        return ((ShapePrototype) prototypes.get(s)).clone();
    }
}