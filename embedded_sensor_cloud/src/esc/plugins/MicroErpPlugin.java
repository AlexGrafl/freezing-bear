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
    public void runPlugin(Socket socket, UrlClass url){
        if(url.getFullPath().contains("searchContacts")){

            Gson gson = new Gson();
            List<Contact> contactList = businessLayer.searchContacts(url.getParameters());
            Type arrayType = new TypeToken<ArrayList<Contact>>(){}.getType();
            String json = gson.toJson(contactList, arrayType);
            sendResponse(json, socket);
        }

        if(url.getFullPath().contains("insertNewContact")){
            boolean inserted = businessLayer.insertNewContact(url.getParameterAsString("json"));
            sendResponse(String.valueOf(inserted), socket);
        }

        if(url.getFullPath().contains("editContact")){
            boolean edited = businessLayer.editContact(url.getParameterAsString("json"));
            sendResponse(String.valueOf(edited), socket);
        }

        if(url.getFullPath().contains("findCompany")){

            Gson gson = new Gson();
            List<Contact> contactList = businessLayer.findCompany(url.getParameterAsString("company"));
            Type arrayType = new TypeToken<ArrayList<Contact>>(){}.getType();
            String json = gson.toJson(contactList, arrayType);
            sendResponse(json, socket);
        }

        if(url.getFullPath().contains("createInvoice")){
            boolean created = businessLayer.createInvoice(url.getParameterAsString("json"));
            sendResponse(String.valueOf(created), socket);
        }

        if(url.getFullPath().contains("addInvoiceItems")){
            boolean added = businessLayer.addInvoiceItems(url.getParameterAsString("json"));
            sendResponse(String.valueOf(added), socket);
        }

    }

    private void sendResponse(String text, Socket socket){
        log.debug("Response: " + text);
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                out.write("HTTP/1.1 200 OK\r\n");
                out.write("Content-Type: text/plain\r\n");
                out.write("Content-Length: " + text.length() + "\r\n");
                out.write("Connection: close \r\n\r\n");
                out.write(text);
                out.flush();
                log.info("Sent 200 OK to " + socket.getRemoteSocketAddress().toString());
        }
        catch(IOException | NullPointerException  e) {
            log.error(e);
            new HttpResponse(socket, 500, "bla");
        }
    }

}