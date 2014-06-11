package esc.plugins;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class InvoiceItemTest {

    @Test
    public void createInvoiceItemTest(){
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceID(1);
        invoiceItem.setTax(10);
        invoiceItem.setDescription("woop");
        invoiceItem.setInvoiceItemID(1);
        invoiceItem.setNettoPrice(10.1);
        assertEquals(1, invoiceItem.getInvoiceID());
        assertEquals(10, invoiceItem.getTax());
        assertEquals("woop", invoiceItem.getDescription());
        assertEquals(1, invoiceItem.getInvoiceItemID());
    }
}