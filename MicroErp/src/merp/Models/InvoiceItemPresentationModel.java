package merp.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InvoiceItemPresentationModel {

    private StringProperty description = new SimpleStringProperty("");
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private IntegerProperty pricePerUnit = new SimpleIntegerProperty();
    private IntegerProperty tax = new SimpleIntegerProperty();


    public boolean validateInput(){

        return !description.get().equals("") &&
                quantity.get() >= 0 &&
                pricePerUnit.get() >= 0 &&
                tax.get() >= 0;

    }
    public void setModel(Invoice model) {

    }

    public InvoiceItem updateModel(InvoiceItem model) {
        model.setDescription(description.get());
        model.setQuantity(quantity.get());
        model.setPricePerUnit(pricePerUnit.get());
        model.setTax(tax.get());

        return model;
    }

    /*****************************/
    /*** classic setter/getter ***/
    /*****************************/

    public String getDescription() {
        return description.get();
    }
    public void setDescription(String description) {
        this.description.set(description);
    }

    public Integer getQuantity() {
        return quantity.get();
    }
    public void setQuantity(Integer quantity) {
        this.quantity.set(quantity);
    }

    public Integer getPricePerUnit() {
        return pricePerUnit.get();
    }
    public void setPricePerUnit(Integer pricePerUnit) {
        this.quantity.set(pricePerUnit);
    }

    public Integer getTax() {
        return tax.get();
    }
    public void setTax(Integer tax) {
        this.tax.set(tax);
    }


    /**************************/
    /*** Property - getters ***/
    /**************************/
    public final StringProperty descriptionProperty() {
        return description;
    }

    public final IntegerProperty quantityProperty() {
        return quantity;
    }

    public final IntegerProperty pricePerUnitProperty() {
       return pricePerUnit;
    }

    public final IntegerProperty taxProperty() {
        return tax;
    }

}
