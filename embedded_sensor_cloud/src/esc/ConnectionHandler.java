package esc;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Alex
 */
public class ConnectionHandler implements Runnable{

    private static final Logger log = Logger.getLogger(ConnectionHandler.class);
    private final Socket _socket;
    ConnectionHandler(Socket socket) {
        this._socket = socket;
    }

    public void run(){
        log.info("Connected: " + _socket.getRemoteSocketAddress().toString());
        processRequest();
    }

    private void processRequest() {
        String requestHeaderLine;
        try(BufferedReader in = new BufferedReader(new InputStreamReader(_socket.getInputStream()))){
            //holt sich erste zeile
            requestHeaderLine = in.readLine();
            log.info(requestHeaderLine);
            //ruft funktion auf die die erste zeile verarbeitet
            String[] requestStrings = processRequestHeader(requestHeaderLine);

            if(requestStrings != null){
                HttpRequest request = new HttpRequest(requestStrings, this._socket);
                String line;
                while(!(line = in.readLine()).equals("")){
                    log.debug(line);
                    request.addLine(line);
                }
                if(request.getProtocol().equals("POST")){
                    StringBuilder stringBuilder = new StringBuilder();
                    while(in.ready()){
                        stringBuilder.append((char)in.read());
                    }
                    String parameterString = stringBuilder.toString();
                    log.debug(parameterString);
                    request.parsePostParameters(parameterString);
                }
                request.processRequest();
            }


        } catch (IOException | NullPointerException e) {
                log.error("Something went wrong", e);
                new HttpResponse(_socket, 500, "bla");
        }
    }

    public String[] processRequestHeader(String headLine) {
        try {
            if(headLine != null){
                //not null -> aufteilen
                String[] splitHeadLine = headLine.split(" ");
                if(splitHeadLine.length > 2) {
                    //hat 3 teile
                    if(splitHeadLine[2].startsWith("HTTP")) {
                        return splitHeadLine;
                    }
                }
            }
            log.warn("Invalid HTTP request headline: " + headLine);
            return null;
        }
        catch (Exception e) {
            log.error(e);
            new HttpResponse(_socket, 500,"bla");
            return null;
        }
    }
}
