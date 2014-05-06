package esc.plugins;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import esc.plugins.dal.IDataAccessLayer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
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

    public List<Contact> searchContacts(String text){
        if(text != null) {
            return dataAccessLayer.searchContacts(text, true);
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
            log.error(e);
            return false;
        }
    }

}
