package merp.Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phips on 02.04.2014.
 */
public class JsonParser {

    //Contact
    public List<Contact> jsonToContactList(String text) {
        Type arrayType = new TypeToken<ArrayList<Contact>>(){}.getType();
        Gson gson = new Gson();

        return gson.fromJson(text, arrayType);
    }
    public String contactToJson(Contact obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    //Invoice
    public List<Invoice> jsonToInvoiceList(String text) {
        Type arrayType = new TypeToken<ArrayList<Invoice>>(){}.getType();
        Gson gson = new Gson();

        return gson.fromJson(text, arrayType);
    }
    public String invoiceToJson(Invoice obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    //InvoiceItems
    public List<InvoiceItem> jsonToInvoiceItemList(String text) {
        Type arrayType = new TypeToken<ArrayList<InvoiceItem>>(){}.getType();
        Gson gson = new Gson();

        return gson.fromJson(text, arrayType);
    }
}
