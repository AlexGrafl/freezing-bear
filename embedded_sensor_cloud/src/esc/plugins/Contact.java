package esc.plugins;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.HashSet;

/**
 * @author Alex
 */

@Entity
public class Contact {

    @Column(name="contactID")
    private int contactID = -1;

    @Column(name="name")
    private String name = null;

    @Column(name="uid")
    private Integer uid = null;

    @Column(name="companyID")
    private Integer companyID = null;

    @Column(name="title")
    private String title = null;

    @Column(name="firstName")
    private String firstName = null;

    @Column(name="lastName")
    private String lastName = null;

    @Column(name="suffix")
    private String suffix = null;

    @Column(name="birthDate")
    private Date birthDate = null;

    @Column(name="address")
    private String address = null;

    @Column(name="invoiceAddress")
    private String invoiceAddress = null;

    @Column(name="shippingAddress")
    private String shippingAddress = null;

    @Column(name="isActive")
    private boolean isActive = false;

    private HashSet<Invoice> invoiceList;

    public Contact(){}
    public Contact(int contactID, String name, int uid, String title, String firstName, String lastName,
                   String suffix, Date birthDate, String address, String invoiceAddress,
                   String shippingAddress, boolean isActive) {
        this.contactID = contactID;
        this.name = name;
        this.uid = uid;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.suffix = suffix;
        this.birthDate = birthDate;
        this.address = address;
        this.invoiceAddress = invoiceAddress;
        this.shippingAddress = shippingAddress;
        this.isActive = isActive;
    }
    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public HashSet<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(HashSet<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }
}
