package esc.plugins;

import esc.plugins.dal.FakeDataAccessLayer;
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

    private final BusinessLayer businessLayer;

    public BusinessLayerTest(){
        this.businessLayer = new BusinessLayer(new FakeDataAccessLayer());
    }

    @Test
    public void searchContactsTest() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", "Grafl");
        parameters.put("uid", "1234");
        List<Contact> contactList = businessLayer.searchContacts(parameters);
        assertEquals(2, contactList.size());
        assertEquals("Grafl", contactList.get(0).getLastName());
        assertNull(contactList.get(0).getUid());
    }

    @Test
    public void searchContactsNull(){
        List<Contact> contactList = businessLayer.searchContacts(null);
        assertEquals(new ArrayList<Contact>(), contactList);
    }

    @Test
    public void searchContactsCannotFind(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("firstName", "woop");
        List<Contact> contactList = businessLayer.searchContacts(parameters);
        assertEquals(new ArrayList<Contact>(), contactList);
    }

    @Test
    public void insertNewContactSuccessTest(){
        String json = "{\"name\":\"Alexander Grafl\",\"title\":\"Dr.\",\"firstName\":\"Alexander\"," +
                "\"lastName\":\"Grafl\",\"suffix\":\"Msc\",\"birthDate\":\"Mar 28, 2014 12:00:00 AM\",\"address\":\"" +
                "Bergengasse 6/5/14 1220 Wien\",\"invoiceAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"shippingAddres" +
                "s\":\"Bergengasse 6/5/14 1220 Wien\",\"isActive\":false}";
        boolean result = businessLayer.insertNewContact(json);
        assertTrue(result);
    }

    @Test
    public void insertNewContactFailsTest(){
        //JSON is malformed, ending curly bracket got "lost"
        String json = "{\"name\":\"Alexander Grafl\",\"title\":\"Dr.\",\"firstName\":\"Alexander\"," +
                "\"lastName\":\"Grafl\",\"suffix\":\"Msc\",\"birthDate\":\"Mar 28, 2014 12:00:00 AM\",\"address\":\"" +
                "Bergengasse 6/5/14 1220 Wien\",\"invoiceAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"shippingAddres" +
                "s\":\"Bergengasse 6/5/14 1220 Wien\",\"isActive\":false";
        boolean result = businessLayer.insertNewContact(json);
        assertFalse(result);
    }

    @Test
    public void editContactSuccess(){
        String json = "{\"contactID\":1, \"name\":\"Alexander Grafl\",\"title\":\"Dr.\",\"firstName\":\"Alexander\"," +
                "\"lastName\":\"Grafl\",\"suffix\":\"Msc\",\"birthDate\":\"Mar 28, 2014 12:00:00 AM\",\"address\":\"" +
                "Bergengasse 6/5/14 1220 Wien\",\"invoiceAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"shippingAddres" +
                "s\":\"Bergengasse 6/5/14 1220 Wien\",\"isActive\":false}";
        boolean result = businessLayer.editContact(json);
        assertTrue(result);
    }

    @Test
    public void editContactFailsByArgument(){
        String json = "{\"name\":\"Alexander Grafl\",\"title\":\"Dr.\",\"firstName\":\"Alexander\"," +
                "\"lastName\":\"Grafl\",\"suffix\":\"Msc\",\"birthDate\":\"Mar 28, 2014 12:00:00 AM\",\"address\":\"" +
                "Bergengasse 6/5/14 1220 Wien\",\"invoiceAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"shippingAddres" +
                "s\":\"Bergengasse 6/5/14 1220 Wien\",\"isActive\":false}";
        boolean result = businessLayer.editContact(json);
        assertFalse(result);
    }

    @Test
    public void editContactFailsByJson(){
        String json = "\"name\":\"Alexander Grafl\",\"title\":\"Dr.\",\"firstName\":\"Alexander\"," +
                "\"lastName\":\"Grafl\",\"suffix\":\"Msc\",\"birthDate\":\"Mar 28, 2014 12:00:00 AM\",\"address\":\"" +
                "Bergengasse 6/5/14 1220 Wien\",\"invoiceAddress\":\"Bergengasse 6/5/14 1220 Wien\",\"shippingAddres" +
                "s\":\"Bergengasse 6/5/14 1220 Wien\",\"isActive\":false}";
        boolean result = businessLayer.editContact(json);
        assertFalse(result);
    }

    @Test
    public void findCompanySuccess(){
        String company = "Grafl";
        List<Contact> contacts = businessLayer.findCompany(company);
        assertEquals(1, contacts.size());

        company = "";
        contacts = businessLayer.findCompany(company);
        assertEquals(new ArrayList<Contact>(), contacts);
    }

    @Test
    public void createInvoiceTestSuccess(){
        String json = "{\"dueDate\":\"Mar 28, 2014 12:00:00 AM\", \"invoiceItems\":[{\"invoiceItemID\":1," +
                " \"invoiceID\":1, " +
                "\"quantity\":2, \"nettoPrice\":10.60, \"pricePerUnit\":5.30, \"tax\":20, \"description\":" +
                "\"wooop\"}, {\"invoiceItemID\":2, \"invoiceID\":1, \"quantity\":1, \"nettoPrice\":18.90, " +
                "\"pricePerUnit\":18.90, \"tax\":20, \"description\":\"wooopieh\"}]}";
        boolean success = businessLayer.createInvoice(json);
        assertTrue(success);
    }

    @Test
    public void createInvoiceTestNotSuccess(){
        String json = "{\"dueDate\":\"Mar 28, 2014 12:00:00 AM\", \"invoiceItems\":[{\"invoiceItemID\":1," +
                " \"invoiceID\":1, " +
                "\"quantity\":2, \"nettoPrice\":10.60, \"pricePerUnit\":5.30, \"tax\":20, \"description\":" +
                "\"wooop\"}, {\"invoiceItemID\":2, \"invoiceID\":1, \"quantity\":1, \"nettoPrice\":18.90, " +
                "\"pricePerUnit\":12.90, \"tax\":20, \"description\":\"wooopieh\"}]}";
        boolean success = businessLayer.createInvoice(json);
        assertFalse(success);
    }

    @Test
    public void createInvoiceTestFails(){
        String json = "\"dueDate\":\"Mar 28, 2014 12:00:00 AM\"}";
        boolean success = businessLayer.createInvoice(json);
        assertFalse(success);
    }
    @Test
    public void createInvoiceTestNoInvoiceItems(){
        String json = "{\"dueDate\":\"Mar 28, 2014 12:00:00 AM\", \"invoiceItems\":[]}";
        boolean success = businessLayer.createInvoice(json);
        assertFalse(success);
    }

    @Test
    public void getInvoiceItemsTest(){
        ArrayList<InvoiceItem> invoiceItems = businessLayer.getInvoiceItems("");
        assertEquals(new ArrayList<InvoiceItem>(), invoiceItems);
    }
    @Test
    public void getInvoicesTest(){
        ArrayList<Invoice> invoices = businessLayer.searchInvoices(new HashMap<String, String>());
        assertEquals(new ArrayList<Invoice>(), invoices);
    }

}
