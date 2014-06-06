package esc;

import esc.plugins.MicroErpPlugin;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author Alex
 */
public class PluginManager {

    private LinkedList<IPlugin> pluginList = new LinkedList<>();

    public PluginManager(){
        //Alle plugins durchgehen ob sie den Request verarbeiten können/wollen
        pluginList.add(new MicroErpPlugin());

    }


    public boolean findPlugin(Socket socket, UrlClass url){
        for(IPlugin iPlugin : this.pluginList){
            if(iPlugin.acceptRequest(url.getPluginPath())){
                try {
                    iPlugin.runPlugin(socket.getOutputStream(), url);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return true;
            }
        }
        return false;
    }


}
