package merp.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class InvoicePresentationModel {

    private ObjectProperty <Date> dueDate = new SimpleObjectProperty();
    private StringProperty message = new SimpleStringProperty("");
    private StringProperty comment = new SimpleStringProperty("");


    public boolean validateInput(){

        return dueDate.get() != null;

    }
    public void setModel(Invoice model) {

    }

    public Invoice updateModel(Invoice model) {
        model.setDueDate(dueDate.get());
        model.setComment(comment.get());
        model.setMessage(message.get());

        return model;
    }

    /*****************************/
    /*** classic setter/getter ***/
    /*****************************/

    public Date getDueDate() {
        return dueDate.get();
    }
    public void setDueDate(Date dueDate){
        this.dueDate.set(dueDate);
    }

    public String getMessage() {
        return message.get();
    }
    public void setMessage(String message) {
        this.message.set(message);
    }

    public String getComment() {
        return comment.get();
    }
    public void setComment(String comment) {
        this.comment.set(comment);
    }


    /**************************/
    /*** Property - getters ***/
    /**************************/
    public final ObjectProperty <Date> dueDateProperty() {
        return dueDate;
    }

    public final StringProperty messageProperty() {
        return message;
    }

    public final StringProperty commentProperty() {
        return comment;
    }

}
