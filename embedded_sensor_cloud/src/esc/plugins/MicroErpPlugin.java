package esc.plugins;

import esc.HttpResponse;
import esc.IPlugin;
import esc.UrlClass;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;

/**
 * @author Alex
 */
public class MicroErpPlugin implements IPlugin {
    @Override
    public boolean acceptRequest(String requestUrl){
        return requestUrl.equals("server");
    }
    @Override
    public void runPlugin(Socket socket, UrlClass url){


    }


    @Override
    public void returnPluginPage(Socket socket) {


    }
}