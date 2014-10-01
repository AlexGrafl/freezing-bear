package esc.plugins;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class PdfCreatorTest {

    @Test
    public void testGenerateInvoice() throws Exception {
        Gson gson = new Gson();
        String json = "{\"invoiceID\":19,\"issueDate\":\"Jun 6, 2014 12:00:00 AM\",\"dueDate\":\"Jun 21, 2014 12:00" +
                ":00 AM\",\"comment\":\"a\n\ta\n\t\ta\n\t" +
                "\t\ta\",\"message\":\"\t\t\tb\n\t\tb\n\tb\nb\",\"contactID\":3,\"total\":4010.03,\"invoiceItems\":[{" +
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
        Invoice invoice = gson.fromJson(json, Invoice.class);
        PdfCreator.generateInvoice(invoice, "foo.pdf");
    }
    @Test
    public void createPdfCreator(){
        PdfCreator pdfCreator = new PdfCreator();
    }
}