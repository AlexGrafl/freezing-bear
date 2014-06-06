package merp.Models;


import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ProxySingleton {
    private static ProxySingleton ourInstance = new ProxySingleton();

    private JsonParser jp = new JsonParser();
    private HttpConnectionHandler httpHandler = new HttpConnectionHandler();

    private ProxySingleton() {}

    public static ProxySingleton getInstance() {
        return ourInstance;
    }


    /*******************************Contact*******************************/
    public List<Contact> searchContacts(String firstName, String surName, String name, String uid) throws IOException {
        //if (name.equals("")) name= "''";
        String searchString = "firstName="+ firstName + "&lastName="+ surName + "" +
                "&name="+ name + "" + "&uid="+ uid;

        String response = httpHandler.sendPostSearchContactRequest(firstName, surName, name, uid);
        return jp.jsonToContactList(response);
    }

    public void editContact(Contact contact) throws IOException {
        Boolean success = httpHandler.sendPostEditContactRequest(jp.contactToJson(contact));
    }
    public void createContact(Contact contact) throws IOException {
        Boolean success = httpHandler.sendPostCreateContactRequest(jp.contactToJson(contact));
    }

    /*******************************Invoice*******************************/
    public List<Invoice> searchInvoice(Date startDate, Date endDate, Double startAmount, Double endAmount, Integer contactId) throws IOException {
        String response = httpHandler.sendPostSearchInvoicesRequest(startDate, endDate, startAmount, endAmount, contactId);
        return jp.jsonToInvoiceList(response);
    }
    public void createInvoice(Invoice invoice) throws IOException {
        Boolean success = httpHandler.sendPostCreateInvoiceRequest(jp.invoiceToJson(invoice));
    }
}
