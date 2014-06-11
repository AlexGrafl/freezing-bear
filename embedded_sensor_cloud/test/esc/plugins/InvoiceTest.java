package esc.plugins;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class InvoiceTest {

    @Test
    public void testCalculateTotal() throws Exception {
        String json = "{\"invoiceID\":19,\"issueDate\":\"Jun 6, 2014 12:00:00 AM\",\"dueDate\":\"Jun 21, 2014 12:00" +
                ":00 AM\",\"comment\":\"a\n\ta\n\t\ta\n\t" +
                "\t\ta\",\"message\":\"\t\t\tb\n\t\tb\n\tb\nb\",\"contactID\":3,\"invoiceItems\":[{" +
                "\"invoiceItemID\":0,\"invoiceID\":19,\"quantity\":10,\"nettoPrice\":2.22222,\"pricePerUnit\":0.22" +
                "2222,\"tax\":10,\"description\":\"Cheese\"},{\"invoiceItemID\":0,\"invoiceID\":19,\"quantity\":100," +
                "\"nettoPrice\":3099.9999,\"pricePerUnit\":30.999999,\"tax\":15,\"description\":\"Whisky\"},{\"invoic" +
                "eItemID\":0,\"invoiceID\":19,\"quantity\":1,\"nettoPrice\":25.22,\"pricePerUnit\":25.22,\"tax\":15" +
                ",\"description\":\"Whisky II\"},{\"invoiceItemID\":0,\"invoiceID\":19,\"quantity\":2,\"nettoPrice\":" +
                "19.98,\"pricePerUnit\":9.99,\"tax\":15,\"description\":\"Whisky III\"},{\"invoiceItemID\":0,\"invoic" +
                "eID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"description\":\"Whis" +
                "ky III\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\"" +
                ":9.99,\"tax\":15,\"description\":\"Whisky III\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"quantity\":" +
                "2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"description\":\"Whisky III\"},{\"invoiceIt" +
                "emID\":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"des" +
                "cription\":\"Whisky III\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98" +
                ",\"pricePerUnit\":9.99,\"tax\":15,\"description\":\"Whisky III\"},{\"invoiceItemID\":0,\"invoiceID\"" +
                ":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"description\":\"Whisky II" +
                "I\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99" +
                ",\"tax\":15,\"description\":\"Whisky III\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"quantity\":2,\"n" +
                "ettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"description\":\"Whisky III\"},{\"invoiceItemID\""+
                ":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"descripti" +
                "on\":\"Whisky III\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pri" +
                "cePerUnit\":9.99,\"tax\":15,\"description\":\"Whisky III\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"" +
                "quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"description\":\"Whisky III\"},{" +
                "\"invoiceItemID\":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax" +
                "\":15,\"description\":\"Whisky III\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPr" +
                "ice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"description\":\"Whisky III\"},{\"invoiceItemID\":0,\"" +
                "invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"description\":" +
                "\"Whisky III\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePer" +
                "Unit\":9.99,\"tax\":15,\"description\":\"Whisky III\"},{\"invoiceItemID\":0,\"invoiceID\":-1,\"quant" +
                "ity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15,\"description\":\"Whisky III\"},{\"inv" +
                "oiceItemID\":0,\"invoiceID\":-1,\"quantity\":2,\"nettoPrice\":19.98,\"pricePerUnit\":9.99,\"tax\":15" +
                ",\"description\":\"Whisky III\"}]}";
        Gson gson = new Gson();
        Invoice invoice = gson.fromJson(json, Invoice.class);
        assertEquals(0, invoice.getTotal(), 0.0001);
        invoice.calculateTotal();
        assertEquals(4010.03, invoice.getTotal(), 0.01);
    }

    @Test
    public void createInvoiceTest(){
        Invoice invoice = new Invoice();
        assertEquals(null, invoice.getContactID());
    }
    @Test
    public void createAnotherInvoiceTest(){
        Invoice invoice = new Invoice();
        invoice.setInvoiceID(1);
        invoice.setComment("woo");
        invoice.setMessage("woo");
        assertEquals("woo", invoice.getMessage());
        assertEquals("woo", invoice.getComment());
        assertEquals(1, invoice.getInvoiceID());
    }
    @Test
    public void createYetAnotherInvoiceTest(){
        Invoice invoice = new Invoice();
        invoice.setTotal(123);
        invoice.setContactID(123);
        Date date = new Date();
        invoice.setIssueDate(date);
        invoice.setDueDate(date);
        assertEquals(date, invoice.getDueDate());
        assertEquals(date, invoice.getIssueDate());
        assertEquals(123, invoice.getTotal(), 0.11);
        assertEquals(123, (int)invoice.getContactID());
    }

    @Test
    public void addInvoiceItemsTest(){
        Invoice invoice = new Invoice();
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setPricePerUnit(2.1);
        invoiceItem.setQuantity(3);
        invoice.addInvoiceItems(invoiceItem);
        invoiceItem = new InvoiceItem();
        invoiceItem.setQuantity(4);
        invoiceItem.setPricePerUnit(4.1);
        invoice.addInvoiceItems(invoiceItem);
        List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        assertEquals(2, invoiceItems.size());
        invoice.calculateTotal();
        assertEquals(22.7 , invoice.getTotal(), 0.001);
        invoice.setInvoiceItems((java.util.LinkedList<InvoiceItem>) invoiceItems);
    }
}