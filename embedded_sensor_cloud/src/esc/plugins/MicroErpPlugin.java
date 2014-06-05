package esc.plugins;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import esc.HttpResponse;
import esc.IPlugin;
import esc.UrlClass;
import esc.plugins.dal.DataAccessLayer;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex
 */
public class MicroErpPlugin implements IPlugin {
    private static final Logger log = Logger.getLogger(MicroErpPlugin.class);

    private BusinessLayer businessLayer = new BusinessLayer(new DataAccessLayer());

    @Override
    public boolean acceptRequest(String requestUrl){
        return requestUrl.equals("server");
    }
    @Override
    public void runPlugin(OutputStream outputStream, UrlClass url){
        if(url.getFullPath().contains("searchContacts")){

            Gson gson = new Gson();
            List<Contact> contactList = businessLayer.searchContacts(url.getParameters());
            Type arrayType = new TypeToken<ArrayList<Contact>>(){}.getType();
            String json = gson.toJson(contactList, arrayType);
            sendResponse(json, outputStream);
        }

        if(url.getFullPath().contains("insertNewContact")){
            boolean inserted = businessLayer.insertNewContact(url.getParameterAsString("json"));
            sendResponse(String.valueOf(inserted), outputStream);
        }

        if(url.getFullPath().contains("editContact")){
            boolean edited = businessLayer.editContact(url.getParameterAsString("json"));
            sendResponse(String.valueOf(edited), outputStream);
        }

        if(url.getFullPath().contains("findCompany")){

            Gson gson = new Gson();
            List<Contact> contactList = businessLayer.findCompany(url.getParameterAsString("company"));
            Type arrayType = new TypeToken<ArrayList<Contact>>(){}.getType();
            String json = gson.toJson(contactList, arrayType);
            sendResponse(json, outputStream);
        }

        if(url.getFullPath().contains("createInvoice")){
            boolean created = businessLayer.createInvoice(url.getParameterAsString("json"));
            sendResponse(String.valueOf(created), outputStream);
        }

        if(url.getFullPath().contains("addInvoiceItems")){
            boolean added = businessLayer.addInvoiceItems(url.getParameterAsString("json"));
            sendResponse(String.valueOf(added), outputStream);
        }

        if(url.getFullPath().contains("searchInvoices")){
            Gson gson = new Gson();
            Type arrayType = new TypeToken<ArrayList<Invoice>>(){}.getType();
            List<Invoice> invoices = businessLayer.searchInvoices(url.getParameters());
            String json = gson.toJson(invoices, arrayType);
            sendResponse(json, outputStream);
        }
        if(url.getFullPath().contains("getInvoiceItems")){
            Gson gson = new Gson();
            Type arrayType = new TypeToken<ArrayList<InvoiceItem>>(){}.getType();
            ArrayList<InvoiceItem> invoiceItems= businessLayer.getInvoiceItems(url.getParameterAsString("invoiceId"));
            String json = gson.toJson(invoiceItems, arrayType);
            sendResponse(json, outputStream);
        }

    }

    private void sendResponse(String text, OutputStream outputStream){
        log.debug("Response: " + text);
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                out.write("HTTP/1.1 200 OK\r\n");
                out.write("Content-Type: text/plain\r\n");
                out.write("Content-Length: " + text.length() + "\r\n");
                out.write("Connection: close \r\n\r\n");
                out.write(text);
                out.flush();
                log.info("Sent 200 OK");
        }
        catch(IOException | NullPointerException  e) {
            log.error(e);
        }
    }

}