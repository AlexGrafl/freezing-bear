package esc.plugins;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import esc.plugins.dal.IDataAccessLayer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alex
 */
public class BusinessLayer {
    private static final Logger LOG = Logger.getLogger(BusinessLayer.class);

    private final IDataAccessLayer dataAccessLayer;

    public BusinessLayer(IDataAccessLayer dataAccessLayer) {
        this.dataAccessLayer = dataAccessLayer;
    }

    public ArrayList<Contact> searchContacts(HashMap<String, String> parameters) {
        return dataAccessLayer.searchContacts(parameters);
    }

    public boolean insertNewContact(String json) {
        try {
            Gson gson = new Gson();
            Contact newContact = gson.fromJson(json, Contact.class);
            return dataAccessLayer.insertNewContact(newContact);
        }
        catch(JsonParseException e){
            LOG.error("Cannot parse JSON '" + json + "'-", e);
            return false;
        }
    }

    public boolean editContact(String json) {
        try {
            Gson gson = new Gson();
            Contact contact = gson.fromJson(json, Contact.class);
            if(contact.getContactID() < 0) throw new IllegalArgumentException();
            return dataAccessLayer.editContact(contact);
        }
        catch(JsonParseException e){
            LOG.error("Cannot parse JSON '" + json + "'-", e);
        }
        catch(IllegalArgumentException e){
            LOG.error("Cannot edit contact with ID < 0!", e);
        }
        return false;
    }

    public Contact findCompany(String company) {
        Contact companyContact = dataAccessLayer.findCompany(company);
        if(companyContact != null && companyContact.isCompany()) return companyContact;
        else return null;
    }

    public boolean createInvoice(String json) {
        try {
            LOG.debug("Invoice: " + json);
            Gson gson = new Gson();
            Invoice newInvoice = gson.fromJson(json, Invoice.class);
            newInvoice.calculateTotal();
            boolean result = false;
            int invoiceId = dataAccessLayer.createInvoice(newInvoice);
            if(invoiceId != -1){
                if(newInvoice.getInvoiceItems().size() > 0) {
                    for (InvoiceItem invoiceItem : newInvoice.getInvoiceItems()) {
                        result = dataAccessLayer.addInvoiceItem(invoiceItem, invoiceId);
                    }
                    LOG.debug("Added " + newInvoice.getInvoiceItems().size() + " invoice items");
                }
                else{
                    LOG.info("No invoiceitems to add!");
                    result = true;
                }
            }
            else throw new IllegalArgumentException();
            return result;
        }
        catch(JsonParseException e) {
            LOG.error("Cannot parse JSON '" + json + "'-", e);
        }
        catch (IllegalArgumentException e) {
            LOG.error("Error while inserting invoice!");
        }
        return false;
    }

    public ArrayList<Invoice> searchInvoices(HashMap<String, String> parameters) {
        return dataAccessLayer.searchInvoices(parameters);
    }

    public ArrayList<InvoiceItem> getInvoiceItems(String invoiceId) {
        return dataAccessLayer.getInvoiceItems(invoiceId);
    }
}
