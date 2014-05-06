package esc.plugins.dal;

import esc.plugins.Contact;

import java.util.List;

/**
 * @author Alex
 */
public interface IDataAccessLayer {
    public List<Contact> searchContacts(String text, boolean onlyActive);

    boolean insertNewContact(Contact newContact);
}
