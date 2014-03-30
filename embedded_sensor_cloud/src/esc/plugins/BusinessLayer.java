package esc.plugins;

import java.util.List;

/**
 * @author Alex
 */
public class BusinessLayer {

    public List<Contact> searchContacts(String text){
        return DataAccessLayer.getInstance().searchContacts(text, true);
    }

}
