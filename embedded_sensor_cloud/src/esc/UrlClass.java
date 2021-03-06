package esc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Alex
 */
public class UrlClass {
    private String rawUrl;
    private String fullPath;
    private String[] splitFullPath;
    private HashMap<String, String> parameters;
    private String pluginPath;
    private FileThing file;

    UrlClass(String raw) {
        try {
            this.rawUrl = URLDecoder.decode(raw, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Uh oh, something bad happened!");
            return;
        }
        parameters = new HashMap<>();
        file = new FileThing();
        splitFullPath = null;
    }

    public String[] getSplitFullPath(){
        return splitFullPath;
    }
    public String getRawUrl(){
        return rawUrl;
    }

    public String getFullPath(){
        return fullPath;
    }

    public String getPluginPath(){
        return pluginPath;
    }

    public HashMap getParameters(){
        return parameters;
    }

    public FileThing getFile(){
        return file;
    }

    public boolean parseUrl() {
        try{
            String[] urlParts = rawUrl.split("\\?");
            this.fullPath = urlParts[0];
            String[] pathParts = fullPath.split("/");
            if(pathParts.length > 0) {
                pluginPath = pathParts[1];
                splitFullPath =  Arrays.copyOfRange(pathParts, 1, pathParts.length);
            }
            else {
                pluginPath = fullPath = "/";
                return true;
            }
            String[] foo = pathParts[pathParts.length - 1].split("\\.");
            if(foo.length > 1) {
                file.setName(foo[0]);
                file.setExtension(foo[1]);
            }
            if(urlParts.length > 1){
                parseParameters(urlParts[1]);
            }
            return true;
        }
        catch(ArrayIndexOutOfBoundsException | NullPointerException | IllegalArgumentException e){
            System.out.println("URL parse error!");
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public void parseParameters(String parameterString){
        HashMap foo = new HashMap<String, String>();
        try{
            if(parameterString != null && !parameterString.equals("") && parameterString.length() > 1){
                String[] bar = parameterString.split("&");
                if(bar.length > 0)
                {
                    for(String part : bar){
                        String[] temp = part.split("=");
                        if(temp.length == 0 || part.equals("")) continue;
                        if(temp.length >= 2)
                        {
                            if(!temp[0].equals("")) foo.put(temp[0], temp[1]);
                        } else {
                            foo.put(temp[0], "");
                        }
                    }
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException | NullPointerException | IllegalArgumentException e){
            System.out.println("Error parsing parameters!");
            e.printStackTrace();
        }
        finally{
            this.parameters = foo;
        }
    }

    public boolean hasParameters(){
        return !this.parameters.isEmpty();
    }

    public String getParameterAsString(String key){
        if(this.parameters.containsKey(key)) {
            return this.parameters.get(key);
        }
        else throw new IllegalArgumentException("Cannot find parameter: '"+key+"'.");
    }
}

class FileThing {
    private String name;
    private String extension;

    String getName(){
        return name;
    }

    void setName(String name){
        this.name = name;
    }

    String getExtension(){
        return extension;
    }

    void setExtension(String extension){
        this.extension = extension;
    }
}
