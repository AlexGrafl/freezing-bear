package esc.plugins.dal;

import esc.plugins.Contact;

import java.util.List;

/**
 * @author Alex
 */
public interface IDataAccessLayer {
    public List<Contact> searchContacts(String key, String value);

    boolean insertNewContact(Contact newContact);

    boolean editContact(Contact contact);

}
