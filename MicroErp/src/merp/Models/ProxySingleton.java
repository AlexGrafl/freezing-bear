package merp.Models;


import merp.Contact;

import java.util.List;

public class ProxySingleton {
    private static ProxySingleton ourInstance = new ProxySingleton();

    private JsonParser jp = new JsonParser();
    private HttpConnectionHandler httpHandler = new HttpConnectionHandler();

    private ProxySingleton() {}

    public static ProxySingleton getInstance() {
        return ourInstance;
    }

    public List<Contact> proxyMain(String searchString){
        String response = httpRequest(searchString);
        return jp.jsonToContact(response);
    }

    private String httpRequest(String request){
        return httpHandler.sendRequest(request);
    }
}
