package esc.plugins;

import com.google.gson.Gson;
import esc.HttpResponse;
import esc.IPlugin;
import esc.UrlClass;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

/**
 * @author Alex
 */
public class MicroErpPlugin implements IPlugin {

    private BusinessLayer businessLayer = new BusinessLayer();

    @Override
    public boolean acceptRequest(String requestUrl){
        return requestUrl.equals("server");
    }
    @Override
    public void runPlugin(Socket socket, UrlClass url){
        if(url.getFullPath().contains("server/searchContacts")){
            System.out.println("woo");
            Gson gson = new Gson();
            List<Contact> contactList = businessLayer.searchContacts(url.getParameters().get("q").toString());
            String json = gson.toJson(contactList);
            System.out.println("JSON: " + json);
            sendResponse(json, socket);
        }

    }

    private void sendResponse(String text, Socket socket){
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                out.write("HTTP/1.1 200 OK\r\n");
                out.write("Content-Type: text/plain\r\n");
                out.write("Content-Length: " + text.length() + "\r\n");
                out.write("Connection: close \r\n\r\n");
                out.write(text);
                out.flush();
                System.out.println("Sent 200 OK to " + socket.getRemoteSocketAddress().toString());
        }
        catch(IOException | NullPointerException  e) {
            e.printStackTrace();
            new HttpResponse(socket, 500, "bla");
        }
    }

}