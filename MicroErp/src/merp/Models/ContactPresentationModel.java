package merp.Models;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.text.ParseException;
import java.util.Date;

public class ContactPresentationModel {


    private IntegerProperty contactID = new SimpleIntegerProperty(-1);
    private StringProperty firstName = new SimpleStringProperty("");
    private StringProperty lastName = new SimpleStringProperty("");
    private StringProperty name = new SimpleStringProperty("");
    private IntegerProperty uid = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty("");
    private StringProperty suffix = new SimpleStringProperty("");
    private ObjectProperty <Date> birthDate = new SimpleObjectProperty();
    private StringProperty address = new SimpleStringProperty("");
    private StringProperty invoiceAddress = new SimpleStringProperty("");
    private StringProperty shippingAddress = new SimpleStringProperty("");
    private BooleanProperty isActive = new SimpleBooleanProperty(false);

    //private HashSet<Invoice> invoiceList;

    private BooleanBinding isCompany = new BooleanBinding() {
        @Override
        protected boolean computeValue() {
            return !Utils.isNullOrEmpty(getName());
        }
    };

    private BooleanBinding disableEditPerson = new BooleanBinding() {
        @Override
        protected boolean computeValue() {

            return ( !Utils.isNullOrEmpty(getName()) || !Utils
                    .isNullOrEmpty(naturalNumberToString(uid.get())))
                    && (Utils.isNullOrEmpty(getFirstName()) || Utils
                    .isNullOrEmpty(getLastName()) || Utils
                    .isNullOrEmpty(getTitle()) || Utils
                    .isNullOrEmpty(getSuffix()) );
        }
    };

    private BooleanBinding disableEditCompany = new BooleanBinding() {
        @Override
        protected boolean computeValue() {
            return ( Utils.isNullOrEmpty(getName()) ||
                    (uid.get() == 0 || Utils
                    .isNullOrEmpty(naturalNumberToString(uid.get()))))
                    && (!Utils.isNullOrEmpty(getFirstName()) || !Utils
                    .isNullOrEmpty(getLastName()) || !Utils
                    .isNullOrEmpty(getTitle()) || !Utils
                    .isNullOrEmpty(getSuffix()) );
        }
    };

    public ContactPresentationModel() {
        ChangeListener<String> canEditListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                isCompany.invalidate();
                disableEditPerson.invalidate();
                disableEditCompany.invalidate();
            }
        };


        //Company
        name.addListener(canEditListener);

        final ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue,
                                Object newValue) {
                isCompany.invalidate();
                disableEditPerson.invalidate();
                disableEditCompany.invalidate();
            }
        };
        uid.addListener(changeListener);

        //Person
        firstName.addListener(canEditListener);
        lastName.addListener(canEditListener);
        title.addListener(canEditListener);
        suffix.addListener(canEditListener);
        //birthDate.addListener(canEditListener);

    }

    public boolean validateInput(){
        boolean isValid = false;
        if(isCompany()){
           if(!name.get().equals("")) isValid = true;
        }
        else{
            if(!firstName.get().equals("") &&
                    !lastName.get().equals("") &&
                    birthDate.get() != null)
                isValid = true;
        }
        return isValid;
    }
    public void setModel(Contact model) {

        this.contactID.set(model.getContactID());
        this.firstName.set(model.getFirstName());
        this.lastName.set(model.getLastName());
        this.name.set(model.getName());
        this.uid.set(model.getUid());
        this.title.set(model.getTitle());
        this.suffix.set(model.getSuffix());
        this.birthDate.set(model.getBirthDate());
        this.address.set(model.getAddress());
        this.invoiceAddress.set(model.getInvoiceAddress());
        this.shippingAddress.set(model.getShippingAddress());
        this.isActive.set(model.getIsActive());
    }

    public Contact updateModel(Contact model) throws ParseException {
        model.setCompanyID(this.contactID.get());
        model.setFirstName(this.firstName.get());
        model.setLastName(this.lastName.get());
        model.setName(this.name.get());
        model.setUid(this.uid.get());
        model.setTitle(this.title.get());
        model.setSuffix(this.suffix.get());
        model.setBirthDate(this.birthDate.get());
        model.setAddress(this.address.get());
        model.setInvoiceAddress(this.invoiceAddress.get());
        model.setShippingAddress(this.shippingAddress.get());
        model.setIsActive(this.isActive.get());

        return model;
    }

    private static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    private String naturalNumberToString(Integer num)
    {
        String test = "";
        try
        {
            test = Integer.toString(num);
            if(num == 0) test = "";
        }
        catch(NumberFormatException | NullPointerException e)
        {
            return "";
        }
        return test;
    }

    public BooleanBinding disableEditPersonBinding() {
        return disableEditPerson;
    }

    public BooleanBinding disableEditCompanyBinding() {
        return disableEditCompany;
    }

    public boolean isCompany() {
        return isCompany.get();
    }

    public boolean disableEditPerson() {
        return disableEditPerson.get();
    }

    public boolean disableEditCompany() {
        return disableEditCompany.get();
    }

    /*****************************/
    /*** classic setter/getter ***/
    /*****************************/
    public Integer getContactID() {
        return contactID.get();
    }
    public void setContactID(Integer contactID) {
        this.contactID.set(contactID);
    }

    public String getTitle() {
        return title.get();
    }
    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public Integer getUid() {
        return uid.get();
    }
    public void setUid(Integer uID) {
        uid.set(uID);
    }

    public String getSuffix(){
        return suffix.get();
    }
    public void setSuffix(String suffix){
        this.suffix.set(suffix);
    }

    public Date getBirthDate() throws ParseException {
        return birthDate.get();
    }
    public void setBirthDate(Date birthDate) throws ParseException {
        this.birthDate.set(birthDate);
    }

    public String getAddress() {
        return address.get();
    }
    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getInvoiceAddress() {
        return invoiceAddress.get();
    }
    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress.set(invoiceAddress);
    }

    public String getShippingAddress() {
        return shippingAddress.get();
    }
    public void SetShippingAddress(String shippingAddress) { this.shippingAddress.set(shippingAddress); }

    public boolean getIsActive() {
        return isActive.get();
    }
    public void setIsActive(boolean isActive) {
        this.isActive.set(isActive);
    }

    /**************************/
    /*** Property - getters ***/
    /**************************/
    public final IntegerProperty contactIDProperty() {
        return contactID;
    }

    public final StringProperty titleProperty() {
        return title;
    }

    public final StringProperty firstNameProperty() {
        return firstName;
    }

    public final StringProperty lastNameProperty() {
        return lastName;
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final IntegerProperty uidProperty() {
        return uid;
    }

    public final BooleanBinding isCompanyBinding() {
        return isCompany;
    }

    public final StringProperty suffixProperty() {
        return suffix;
    }

    public final ObjectProperty <Date> birthDateProperty() {
        return birthDate;
    }

    public final StringProperty addressProperty() {
        return address;
    }

    public final StringProperty invoiceAddressProperty() {
        return invoiceAddress;
    }

    public final StringProperty shippingAddressProperty() {
        return shippingAddress;
    }

    public final BooleanProperty isActiveProperty() {
        return isActive;
    }
}
