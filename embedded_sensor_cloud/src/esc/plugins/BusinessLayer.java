package esc.plugins;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
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
        if(!parameters.isEmpty() && parameters.size() == 1) {
            String key = (String) parameters.keySet().toArray()[0];
            if(key.equals("name") || key.equals("firstName") || key.equals("lastName") || key.equals("uid")) {
                String value = parameters.get(key);
                return dataAccessLayer.searchContacts(key, value);
            }
        }
        return new ArrayList<>();
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
            return dataAccessLayer.editContact(contact);
        }
        catch(JsonParseException e){
            log.error("Cannot parse JSON '"+json+"'-", e);
            return false;
        }
    }
}
