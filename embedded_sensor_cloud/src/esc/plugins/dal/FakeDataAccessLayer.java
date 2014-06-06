package esc.plugins.dal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.logging.Logger;
import esc.plugins.Contact;
import esc.plugins.Invoice;
import esc.plugins.InvoiceItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alex
 */
public class FakeDataAccessLayer implements IDataAccessLayer {
    private static final Logger log = Logger.getLogger(FakeDataAccessLayer.class);

    @Override
    public ArrayList<Contact> searchContacts(HashMap<String, String> parameters) {
        Type arrayType = new TypeToken<ArrayList<Contact>>(){}.getType();
        Gson gson = new Gson();
        if(parameters != null && !parameters.containsKey("firstName")) {
            return gson.fromJson("[{\"contactID\":1,\"name\":\"Alexander Grafl\",\"title\":\"Dr.\",\"firstName\":" +
                    "\"Alexander\",\"lastName\":\"Grafl\",\"suffix\":\"Msc\",\"birthDate\":\"Mar 28, 2014 12:00:0" +
                    "0 AM\",\"address\":\"Bergengasse 6/5/14 1220 Wien\",\"invoiceAddress\":\"Bergengasse 6/5/14 " +
                    "1220 Wien\",\"shippingAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"isActive\":false},{\"cont" +
                    "actID\":2,\"name\":\"Grafl GmbH\",\"uid\":1,\"address\":\"Bergengasse 6/5/14 1220 Wien\",\"i" +
                    "nvoiceAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"shippingAddress\":\"Bergengasse 6/5/14 12" +
                    "20 Wien\",\"isActive\":false}]", arrayType);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean insertNewContact(Contact newContact) {
        return true;
    }

    @Override
    public boolean editContact(Contact contact) {
        return true;
    }

    @Override
    public ArrayList<Contact> findCompany(String company) {
        Gson gson = new Gson();
        Type arrayType = new TypeToken<ArrayList<Contact>>(){}.getType();
        if(!company.equals("")){
            return gson.fromJson("[{\"contactID\":2,\"name\":\"Grafl GmbH\",\"uid\":1,\"address\":\"Bergengasse 6/5" +
                    "/14 1220 Wien\",\"invoiceAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"shippingAddress\":\"Berg" +
                    "engasse 6/5/14 1220 Wien\",\"isActive\":false}]", arrayType);
        }
        return new ArrayList<>();
    }

    @Override
    public int createInvoice(Invoice newInvoice) {
        if(newInvoice.getTotal() == 35.4 || newInvoice.getTotal() == 0){
            return 1;
        }

        log.info("Total: "+newInvoice.getTotal());
        return -1;
    }

    @Override
    public boolean addInvoiceItem(InvoiceItem invoiceItem, int invoiceId) {
        return true;
    }

    @Override
    public ArrayList<InvoiceItem> getInvoiceItems(String invoiceId) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Invoice> searchInvoices(HashMap<String, String> parameters) {
        return new ArrayList<>();
    }
}
