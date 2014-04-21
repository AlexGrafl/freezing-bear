package graphicseditor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
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
                //Rectangle selection = new Rectangle(shape.getLayoutX(), shape.getLayoutY());
                selectionList.add(shape);
            }
        }
    }

    //public void drawSelection(GraphicsContext graphicsContext2D) {
   //     for(Shape)
   // }

   // public void drawObjects(GraphicsContext graphicsContext2D) {
  //
  //  }
}
