package merp.PresentationModels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import merp.Models.Invoice;
import merp.Models.InvoiceItem;
import merp.Models.ProxySingleton;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayInvoiceController extends AbstractController {

    @FXML
    private TableView tableView;
    @FXML
    private Button btnClose, btnPrintPDF;
    @FXML
    private Label dueDate, issueDate, comment, message, contactID, invoiceID;
    @FXML
    private Label bruttoTotal, nettoTotal;

    private Invoice tmpInvoice;

	@Override
	public void setModel(Object model) {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

    }
    public void initDialog(Invoice invoice) throws IOException {
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        tmpInvoice = invoice;
        tmpInvoice.setInvoiceItems((ArrayList) ProxySingleton.getInstance().getInvoiceItems(tmpInvoice.getInvoiceID()));

        invoiceID.setText(((Integer)tmpInvoice.getInvoiceID()!=null) ? Integer.toString(tmpInvoice.getInvoiceID()) : "");
        contactID.setText((tmpInvoice.getContactID() !=null) ? Integer.toString(tmpInvoice.getContactID()) : "");
        dueDate.setText(date.format(tmpInvoice.getDueDate()));
        issueDate.setText(date.format(tmpInvoice.getIssueDate()));
        message.setText(tmpInvoice.getMessage());
        comment.setText(tmpInvoice.getComment());
        bruttoTotal.setText(((Double)tmpInvoice.getTotal()!=null) ? Double.toString(tmpInvoice.getTotal()) : "0");

        updateTableView();

    }


    private void updateTableView(){
        double totalPrice = 0;
        tableView.getItems().setAll(FXCollections.observableArrayList());

        ObservableList<Invoice> resultElements = FXCollections.observableArrayList();
        resultElements.addAll((List) tmpInvoice.getInvoiceItems());
        tableView.setItems(resultElements);



        for (InvoiceItem element : tmpInvoice.getInvoiceItems()) totalPrice += element.getNettoPrice();
        nettoTotal.setText(Double.toString( totalPrice));
    }
    @FXML
    private void onPrintPDF(){

        PdfCreator.generateInvoice(tmpInvoice);
    }
    @FXML
    private void onClose() {
        ((Stage) btnClose.getScene().getWindow()).close();
    }

}
