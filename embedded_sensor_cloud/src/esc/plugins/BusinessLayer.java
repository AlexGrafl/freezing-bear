package esc.plugins;

import esc.plugins.dal.DataAccessLayer;
import esc.plugins.dal.IDataAccessLayer;

import java.util.List;

/**
 * @author Alex
 */
public class BusinessLayer {

    private IDataAccessLayer dataAccessLayer;

    public BusinessLayer(IDataAccessLayer dataAccessLayer){
        this.dataAccessLayer = dataAccessLayer;
    }

    public List<Contact> searchContacts(String text){
        return dataAccessLayer.searchContacts(text, true);
    }

}
