package graphicseditor;

import javafx.scene.shape.Shape;

import java.util.LinkedList;

/**
 * @author Alex
 */
public class ObjectModel {

    private LinkedList<Shape> selectionList = new LinkedList<Shape>();
    private LinkedList<Shape> objectList = new LinkedList<Shape>();
    private static ObjectModel ourInstance = new ObjectModel();

    public static ObjectModel getInstance() {
        return ourInstance;
    }

    private ObjectModel() {
    }

    public void addShapeToModel(Shape shape){
        objectList.add(shape);
    }

    public void addObject(double x, double y){

    }

    public void addObjectToSelection(double x, double y, boolean isCtrlDown){
        for(Shape shape : objectList){
            if(shape.contains(x, y)){
                if(!isCtrlDown) selectionList.clear();
                selectionList.add(shape);
            }
        }
    }

}
