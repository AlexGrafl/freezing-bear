package merp.Models;

import javafx.beans.property.*;

public class ContactTableModel {
    /* Properties related to Contact */
    private IntegerProperty contactID = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty uid = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty suffix = new SimpleStringProperty();
    private StringProperty birthDate = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty invoiceAddress = new SimpleStringProperty();
    private StringProperty shippingAddress = new SimpleStringProperty();
    private BooleanProperty isActive = new SimpleBooleanProperty();

    public ContactTableModel(Integer contactID, String name, Integer uid, String title, String firstName, String lastName,
                             String suffix, String birthDate, String address, String invoiceAddress,
                             String shippingAddress, Boolean isActive){

        this.contactID.set(contactID);
        this.name.set(name);
        this.uid.set(uid);
        this.title.set(title);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.suffix.set(suffix);
        this.birthDate.set(birthDate);
        this.address.set(address);
        this.invoiceAddress.set(invoiceAddress);
        this.shippingAddress.set(shippingAddress);
        this.isActive.set(isActive);

    }

    /* Getters for Properties */
    public IntegerProperty uidProperty() {
        return uid;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty suffixProperty() {
        return suffix;
    }

    public StringProperty birthDateProperty() {
        return birthDate;
    }

    public StringProperty addressProperty() {
        return address;
    }

    /*	Getters and Setters	*/
    public Integer getUID() {
        return uid.get();
    }

    public void setUID(Integer uid) {
        this.uid.set(uid);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String Name) {
        this.name.set(Name);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getSuffix() {
        return suffix.get();
    }

    public void setSuffix(String suffix) {
        this.suffix.set(suffix);
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.setValue(firstName);
    }

    public IntegerProperty getContactID() {
        return contactID;
    }

    public void setContactID(IntegerProperty contactID) {
        this.contactID = contactID;
    }

    public StringProperty getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(StringProperty invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public StringProperty getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(StringProperty shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BooleanProperty getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanProperty isActive) {
        this.isActive = isActive;
    }
}
