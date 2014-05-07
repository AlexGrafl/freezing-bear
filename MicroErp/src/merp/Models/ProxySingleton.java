package merp.Models;


import java.io.IOException;
import java.util.List;

public class ProxySingleton {
    private static ProxySingleton ourInstance = new ProxySingleton();

    private JsonParser jp = new JsonParser();
    private HttpConnectionHandler httpHandler = new HttpConnectionHandler();

    private ProxySingleton() {}

    public static ProxySingleton getInstance() {
        return ourInstance;
    }

    public List<Contact> searchContacts(String firstName, String surName, String name, String uid) throws IOException {
        //if (name.equals("")) name= "''";
        String searchString = "firstName="+ firstName + "&lastName="+ surName + "" +
                "&q="+ name + "" + "&uid="+ uid;

        String response = httpHandler.sendGetRequest(searchString);
        //String response = httpHandler.sendPostSearchRequest(firstName,surName,name,uid);
        return jp.jsonToContactList(response);
    }

    public void editContact(Contact contact) throws IOException {
        //String response = httpHandler.sendGetEditContactRequest(jp.contactToJson(contact));
        Boolean success = httpHandler.sendPostEditContactRequest(jp.contactToJson(contact));
    }
    public void createContact(Contact contact) throws IOException {
        //String response = httpHandler.sendGetCreateContactRequest(jp.contactToJson(contact));
        Boolean success = httpHandler.sendPostCreateContactRequest(jp.contactToJson(contact));
    }
}
