package esc.plugins.dal;

import esc.plugins.Contact;
import esc.plugins.Invoice;
import esc.plugins.InvoiceItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alex
 */
public interface IDataAccessLayer {
    public ArrayList<Contact> searchContacts(HashMap<String, String> parameters);

    boolean insertNewContact(Contact newContact);

    boolean editContact(Contact contact);

    ArrayList<Contact> findCompany(String company);

    boolean createInvoice(Invoice newInvoice);

    boolean addInvoiceItem(InvoiceItem invoiceItem);

    boolean setTotalInInvoice(double total, int invoiceID);

    ArrayList<InvoiceItem> getInvoiceItems(String invoiceId);

    ArrayList<Invoice> searchInvoices(HashMap<String, String> parameters);
}
