package esc;

import esc.plugins.BusinessLayer;
import esc.plugins.Contact;
import esc.plugins.dal.FakeDataAccessLayer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
        List<Contact> contactList = businessLayer.searchContacts("Grafl");
        assertEquals(2, contactList.size());
        assertEquals("Grafl", contactList.get(0).getLastName());
        assertNull(contactList.get(0).getUid());
    }
}
