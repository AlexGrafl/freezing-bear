package merp.Models;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Alex
 */
@Entity
public class InvoiceItem {
    @Column(name="invoiceItemID")
    private int invoiceItemID;
    @Column(name="invoiceID")
    private int invoiceID;
    @Column(name="quantity")
    private int quantity;
    @Column(name="nettoPrice")
    private double nettoPrice;
    @Column(name="pricePerUnit")
    private double pricePerUnit;
    @Column(name="tax")
    private int tax;
    @Column(name="description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInvoiceItemID() {
        return invoiceItemID;
    }

    public void setInvoiceItemID(int invoiceItemID) {
        this.invoiceItemID = invoiceItemID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getNettoPrice() {
        return nettoPrice;
    }

    public void setNettoPrice(double nettoPrice) {
        this.nettoPrice = nettoPrice;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

}
