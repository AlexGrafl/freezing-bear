package esc;

import java.io.OutputStream;

/**
 * @author Alex
 */

//Interface für die Plugins, zum Plugin pluginnen
public interface IPlugin {
    public boolean acceptRequest(String requestUrl);
    public void runPlugin(OutputStream outputStream, UrlClass url);
}
