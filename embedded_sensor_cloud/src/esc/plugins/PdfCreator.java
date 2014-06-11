package esc.plugins;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;

/**
 * @author Alex
 */
public class PdfCreator {
    private final static Logger log = Logger.getLogger(PdfCreator.class);
    private static Font font = FontFactory.getFont("Verdana", 12);
    private static Font fontbold = FontFactory.getFont("Verdana", 12, Font.BOLD);

    public static void generateInvoice(Invoice invoice, String pathToPdf){
        Document pdf = new Document();
        try {
            PdfWriter.getInstance(pdf, new FileOutputStream(pathToPdf));
            pdf.open();
            pdf.setPageSize(PageSize.A5);
            addMetaData(pdf);
            addInvoiceInfo(invoice, pdf);
            addInvoiceItems(invoice, pdf);
            pdf.close();
        } catch (DocumentException | FileNotFoundException e) {
            log.error("Error creating PDF", e);
        }
    }

    private static void addInvoiceItems(Invoice invoice, Document pdf) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidths(new int[]{1, 2, 1, 1, 1, 1});
        addInvoiceItemsHeader(table);
        PdfPCell cell;
        for(InvoiceItem invoiceItem : invoice.getInvoiceItems()){
            cell = new PdfPCell(new Phrase(String.valueOf(invoiceItem.getQuantity()), font));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(invoiceItem.getDescription(), font));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(invoiceItem.getPricePerUnit()), font));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(invoiceItem.getNettoPrice()), font));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(invoiceItem.getTax()), font));
            table.addCell(cell);
            BigDecimal taxPercent = BigDecimal.valueOf(1 + (invoiceItem.getTax() / 100.0));
            BigDecimal nettoPrice = BigDecimal.valueOf(invoiceItem.getPricePerUnit() * invoiceItem.getQuantity());
            cell = new PdfPCell(new Phrase(String.valueOf(taxPercent.multiply(nettoPrice)), font));
            table.addCell(cell);
        }
        cell = new PdfPCell( new Phrase("Total: " + invoice.getTotal(), fontbold));
        cell.setColspan(6);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
        pdf.add(table);
    }

    private static void addInvoiceItemsHeader(PdfPTable table) {
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Quantity", fontbold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Description", fontbold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Price per Unit", fontbold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Netto", fontbold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Tax", fontbold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Brutto", fontbold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private static void addMetaData(Document document){
        document.addTitle("ERP");
        document.addSubject("Invoice");
        document.addKeywords("Wow, such, very");
        document.addAuthor("Alex");
        document.addCreator("Alex");
    }

    private static void addInvoiceInfo(Invoice invoice, Document pdf) throws DocumentException{
        pdf.add(new Paragraph("Invoice: " + invoice.getInvoiceID(), fontbold));
        pdf.add(new Paragraph("Issue Date: " + invoice.getIssueDate().toString(), font));
        pdf.add(new Paragraph("Due Date: " + invoice.getDueDate().toString(), font));
        pdf.add(new Paragraph("Contact: " + invoice.getContactID().toString(), font));
        pdf.add(new Paragraph("Message: " + invoice.getMessage(), font));
        pdf.add(new Paragraph("Comment: " + invoice.getComment(), font));
    }

}
