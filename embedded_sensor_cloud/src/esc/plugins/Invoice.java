package esc.plugins;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.LinkedList;

/**
 * @author Alex
 */

@Entity
public class Invoice {
    @Column(name="invoiceID")
    private int invoiceID;
    @Column(name="issueDate")
    private Date issueDate;
    @Column(name="dueDate")
    private Date dueDate;
    @Column(name="comment")
    private String comment;
    @Column(name="message")
    private String message;
    @Column(name="contactID")
    private int contactID;
    @Column(name="total")
    private double total;

    private LinkedList<InvoiceItem> invoiceItems = new LinkedList<>();

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LinkedList<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }
    public void setInvoiceItems(LinkedList<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
    public void addInvoiceItems(InvoiceItem invoiceItem){
        invoiceItems.add(invoiceItem);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
