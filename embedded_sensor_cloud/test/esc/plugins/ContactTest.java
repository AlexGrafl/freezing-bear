package esc.plugins;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ContactTest {

    @Test
    public void contactCreateTest(){
        Contact contact = new Contact();
        assertEquals(-1, contact.getContactID());
        assertFalse(contact.isActive());
        contact.setBirthDate(new Date());
        contact.setIsActive(true);
        contact.getInvoiceList();
    }

    @Test
    public void contactCreateCompanyTest(){
        Contact contact = new Contact(1, "Alex GmbH", 123, null, null, null, null, null, null, "Bergengasse","Bergengasse",
                "Bergengasse", true);
        assertTrue(contact.isCompany());
    }

    @Test
    public void contactCreatePersonTest(){
        Contact contact = new Contact(2, null, null, 1, "Master", "Alex", "Grafl", "woo", new Date(), "Bergengasse",
                "Bergengasse", "Bergengasse", true);
        assertFalse(contact.isCompany());
        assertEquals(2, contact.getContactID());
        assertEquals(1, (int) contact.getCompanyID());
        assertEquals("Master", contact.getTitle());
        assertEquals("Alex", contact.getFirstName());
        assertEquals("Grafl", contact.getLastName());
        assertEquals("woo", contact.getSuffix());
        assertTrue(contact.isActive());

    }

    @Test
    public void createContactTest(){
        Contact contact = new Contact();
        contact.setContactID(1);
        contact.setName("woo");
        contact.setUid(123);
        assertEquals(1, contact.getContactID());
        assertEquals(123, (int)contact.getUid());
        assertEquals("woo", contact.getName());
    }

    @Test
    public void createAnotherContactTest(){
        Contact contact = new Contact();
        contact.setTitle("woo");
        contact.setFirstName("woo");
        contact.setLastName("woo");
        assertEquals("woo", contact.getTitle());
        assertEquals("woo", contact.getFirstName());
        assertEquals("woo", contact.getLastName());
    }

    @Test
    public void createYetAnotherContactTest(){
        Contact contact = new Contact();
        contact.setSuffix("woo");
        Date date = new Date();
        contact.setBirthDate(date);
        assertEquals("woo", contact.getSuffix());
        assertEquals(date, contact.getBirthDate());
    }

    @Test
    public void createSomeContactTest(){
        Contact contact = new Contact();
        contact.setAddress("woo");
        contact.setInvoiceAddress("woo");
        contact.setShippingAddress("woo");
        assertEquals("woo", contact.getAddress());
        assertEquals("woo", contact.getInvoiceAddress());
        assertEquals("woo", contact.getShippingAddress());
    }

    @Test
    public void createSomeInvoiceContactTest(){
        Contact contact = new Contact();
        ArrayList<Invoice> invoices = new ArrayList<>();
        contact.setInvoiceList(invoices);
        contact.setCompanyID(1);
        assertEquals(1, (int)contact.getCompanyID());
        assertEquals(invoices, contact.getInvoiceList());
    }


}