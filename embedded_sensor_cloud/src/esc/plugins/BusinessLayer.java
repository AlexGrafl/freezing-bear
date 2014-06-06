package esc.plugins;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import esc.plugins.dal.IDataAccessLayer;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alex
 */
public class BusinessLayer {
    private static final Logger log = Logger.getLogger(BusinessLayer.class);

    private final IDataAccessLayer dataAccessLayer;

    public BusinessLayer(IDataAccessLayer dataAccessLayer){
        this.dataAccessLayer = dataAccessLayer;
    }

    public ArrayList<Contact> searchContacts(HashMap<String, String> parameters){
        return dataAccessLayer.searchContacts(parameters);
    }

    public boolean insertNewContact(String json){
        try {
            Gson gson = new Gson();
            Contact newContact = gson.fromJson(json, Contact.class);
            return dataAccessLayer.insertNewContact(newContact);
        }
        catch(JsonParseException e){
            log.error("Cannot parse JSON '"+json+"'-", e);
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
            log.error("Cannot parse JSON '"+json+"'-", e);
        }
        catch(IllegalArgumentException e){
            log.error("Cannot edit contact with ID < 0!", e);
        }
        return false;
    }

    public ArrayList<Contact> findCompany(String company) {
        return dataAccessLayer.findCompany(company);
    }

    public boolean createInvoice(String json) {
        try {
            Gson gson = new Gson();
            Invoice newInvoice = gson.fromJson(json, Invoice.class);
            return dataAccessLayer.createInvoice(newInvoice);
        }
        catch(JsonParseException e){
            log.error("Cannot parse JSON '"+json+"'-", e);
            return false;
        }
    }

    public boolean addInvoiceItems(String json){
        Gson gson = new Gson();
        boolean result = false;
        BigDecimal total = BigDecimal.ZERO;
        Type arrayType = new TypeToken<ArrayList<InvoiceItem>>(){}.getType();
        try{
            ArrayList<InvoiceItem> invoiceItems = gson.fromJson(json, arrayType);
            if(invoiceItems.size() > 0) {
                for (InvoiceItem invoiceItem : invoiceItems) {
                    result = dataAccessLayer.addInvoiceItem(invoiceItem);
                    BigDecimal taxPercent = BigDecimal.valueOf(1 + (invoiceItem.getTax() / 100.0));
                    BigDecimal nettoPrice = BigDecimal.valueOf(invoiceItem.getPricePerUnit() * invoiceItem.getQuantity());
                    total = total.add(taxPercent.multiply(nettoPrice));
                }
                log.debug("Added "+ invoiceItems.size() +" invoice items, total: " + total.toString());
                result = dataAccessLayer.setTotalInInvoice(total.doubleValue(), invoiceItems.get(0).getInvoiceID());
            }
            else{
                log.info("No invoiceitems to add!");
            }
        } catch (JsonParseException e){
            log.error("Cannot parse JSON '" + json + "' - ", e);
        }
        return result;
    }

    public ArrayList<Invoice> searchInvoices(HashMap<String, String> parameters) {
        return dataAccessLayer.searchInvoices(parameters);
    }

    public ArrayList<InvoiceItem> getInvoiceItems(String invoiceId) {
        return dataAccessLayer.getInvoiceItems(invoiceId);
    }
}
