package esc.plugins.dal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import esc.plugins.Contact;
import esc.plugins.Invoice;
import esc.plugins.InvoiceItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alex
 */
public class FakeDataAccessLayer implements IDataAccessLayer {
    @Override
    public List<Contact> searchContacts(HashMap<String, String> parameters) {
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
    public List<Contact> findCompany(String company) {
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
    public boolean createInvoice(Invoice newInvoice) {
        return true;
    }

    @Override
    public boolean addInvoiceItem(InvoiceItem invoiceItem) {
        return true;
    }

    @Override
    public boolean setTotalInInvoice(double total, int invoiceID) {
        if(total == 35.40 && invoiceID == 1){
            return true;
        }
        return false;
    }
}
