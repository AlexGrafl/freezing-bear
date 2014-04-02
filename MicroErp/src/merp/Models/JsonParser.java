package merp.Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import merp.Contact;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phips on 02.04.2014.
 */
public class JsonParser {
    public List<Contact> jsonToContact(String text) {
        Type arrayType = new TypeToken<ArrayList<Contact>>(){}.getType();
        Gson gson = new Gson();

        return gson.fromJson(text, arrayType);
    }
    public String contactToJson(Contact tmp){
        /**
         * To Implement
         */
        return "";
    }
}
