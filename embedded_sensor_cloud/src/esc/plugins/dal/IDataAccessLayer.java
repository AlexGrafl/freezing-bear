package esc.plugins.dal;

import esc.plugins.Contact;
import esc.plugins.Invoice;

import java.util.HashMap;
import java.util.List;

/**
 * @author Alex
 */
public interface IDataAccessLayer {
    public List<Contact> searchContacts(HashMap<String, String> parameters);

    boolean insertNewContact(Contact newContact);

    boolean editContact(Contact contact);

    List<Contact> findCompany(String company);

    boolean createInvoice(Invoice newInvoice);
}
