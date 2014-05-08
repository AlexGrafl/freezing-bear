package esc;

import esc.plugins.BusinessLayer;
import esc.plugins.Contact;
import esc.plugins.dal.FakeDataAccessLayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Alex
 */
@RunWith(JUnit4.class)
public class BusinessLayerTest {

    private BusinessLayer businessLayer;

    public BusinessLayerTest(){
        this.businessLayer = new BusinessLayer(new FakeDataAccessLayer());
    }

    @Test
    public void searchContactsTest(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", "Grafl");
        List<Contact> contactList = businessLayer.searchContacts(parameters);
        assertEquals(2, contactList.size());
        assertEquals("Grafl", contactList.get(0).getLastName());
        assertNull(contactList.get(0).getUid());
    }

    @Test
    public void searchContactsNullText(){
        List<Contact> contactList = businessLayer.searchContacts(null);
        assertEquals(new ArrayList<Contact>(), contactList);
    }

    @Test
    public void insertNewContactSuccessTest(){
        String json = "{\"contactID\":1, \"name\":\"Alexander Grafl\",\"title\":\"Dr.\",\"firstName\":\"Alexander\"," +
                "\"lastName\":\"Grafl\",\"suffix\":\"Msc\",\"birthDate\":\"Mar 28, 2014 12:00:00 AM\",\"address\":\"" +
                "Bergengasse 6/5/14 1220 Wien\",\"invoiceAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"shippingAddres" +
                "s\":\"Bergengasse 6/5/14 1220 Wien\",\"isActive\":false}";
        boolean result = businessLayer.insertNewContact(json);
        assertTrue(result);
    }

    @Test
    public void insertNewContactFailsTest(){
        //JSON is malformed, ending curly bracket got "lost"
        String json = "{\"contactID\":1, \"name\":\"Alexander Grafl\",\"title\":\"Dr.\",\"firstName\":\"Alexander\"," +
                "\"lastName\":\"Grafl\",\"suffix\":\"Msc\",\"birthDate\":\"Mar 28, 2014 12:00:00 AM\",\"address\":\"" +
                "Bergengasse 6/5/14 1220 Wien\",\"invoiceAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"shippingAddres" +
                "s\":\"Bergengasse 6/5/14 1220 Wien\",\"isActive\":false";
        boolean result = businessLayer.insertNewContact(json);
        assertFalse(result);
    }
}
