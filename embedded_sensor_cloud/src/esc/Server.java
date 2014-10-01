package esc;


import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * @author Alex
 */
public class Server {
    public static final Logger log = Logger.getLogger(Server.class);
    private static int SERVER_PORT = Integer.parseInt(Server.getServerProperty("port") != null
            ? Server.getServerProperty("port") : "8080"); // port aus config mit fallback
    private static Properties serverProperties = null;

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

    public static String getServerProperty(String key) {
        if(serverProperties == null){
            try {
                serverProperties = new Properties();
                InputStream in;
                File configFile = new File(".properties");
                if (configFile.exists()) {
                    in = new FileInputStream(".properties");
                }
                else{
                    serverProperties = null;
                    throw new FileNotFoundException("File '.properties' not found!");
                }
                serverProperties.load(in);
                in.close();
            }
            catch (IOException e){
                log.error("Error while reading properties", e);
                return null;
            }
        }
        if(serverProperties.containsKey(key))  {
            return serverProperties.getProperty(key);
        }
        return null;
    }
}

