package esc;

import junit.framework.TestCase;

import java.net.Socket;

public class PluginManagerTest extends TestCase {

    public void testFindPlugin() throws Exception {
        PluginManager pluginManager = new PluginManager();
        UrlClass url = new UrlClass("/server/woop");
        url.parseUrl();
        pluginManager.findPlugin(new Socket(), url);
    }
}