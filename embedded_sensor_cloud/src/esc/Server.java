package esc;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author Alex
 */
public class Server {
    public static final Logger log = Logger.getLogger(Server.class);
    private static final int SERVER_PORT = 8080; // port

    public static void main(String[] args) {


        // erstellt listener und lauscht
        try(ServerSocket listener = new ServerSocket(SERVER_PORT)){
            log.info("Listening on Port " + SERVER_PORT);
            while(true){
                //akzeptiert verbindungen
                Socket sock = listener.accept();
                //neuer ConnectionHandler erstellen und in neuem Thread starten
                Thread t = new Thread(new ConnectionHandler(sock));
                t.start();
            }
        }
        catch(IOException ex){
            log.error(ex);
        }
    }
}

