package esc;


import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;


/**
 * @author Alex
 */
public class HttpRequest {
    private String protocol;
    private UrlClass url;
    private Socket socket;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private HashMap requestOptions;

    HttpRequest(String[] s, Socket socket) {
        //Request headline aufteilen und decodieren
        this.protocol = s[0];
        this.url = new UrlClass(s[1]);
        @SuppressWarnings("UnusedAssignment") String httpVersion = s[2];
        this.socket = socket;
        this.requestOptions = new HashMap<String, String>();
		if(! url.parseUrl()){
            new HttpResponse(this.socket, 500, "");
        }
    }
    public String getProtocol(){
        return this.protocol;
    }
    public void processRequest(){
        if(! (new PluginManager()).findPlugin(this.socket, this.url)){
                //page not found, yo
                new HttpResponse(this.socket, 404, url.getFullPath());
        }
    }

    public void addLine(String line){
        String[] foo = line.split(": ");
        if(foo.length > 1){
            //noinspection unchecked
            this.requestOptions.put(foo[0], foo[1]);
        }
    }

    public void parsePostParameters(String line){

        try {
            url.parseParameters(URLDecoder.decode(line, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
