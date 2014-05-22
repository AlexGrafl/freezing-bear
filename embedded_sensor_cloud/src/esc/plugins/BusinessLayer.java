package esc.plugins;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sun.media.jfxmediaimpl.MediaDisposer;
import esc.plugins.dal.IDataAccessLayer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alex
 */
public class BusinessLayer {
    private static final Logger log = Logger.getLogger(BusinessLayer.class);

    private IDataAccessLayer dataAccessLayer;

    public BusinessLayer(IDataAccessLayer dataAccessLayer){
        this.dataAccessLayer = dataAccessLayer;
    }

    public List<Contact> searchContacts(HashMap<String, String> parameters){
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

    public List<Contact> findCompany(String company) {
        return dataAccessLayer.findCompany(company);
    }

    public boolean createInvoice(HashMap parameters) {

        return false;
    }
}
