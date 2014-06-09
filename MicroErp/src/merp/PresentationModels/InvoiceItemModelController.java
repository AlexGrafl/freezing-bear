package merp.PresentationModels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import merp.Models.Invoice;
import merp.Models.InvoiceItem;
import merp.Models.ProxySingleton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InvoiceItemModelController extends AbstractController {

    @FXML
    private TableView tableView;
    @FXML
    private Button btnFinish, btnCancel;
    @FXML
    private TextField textDesc, textQuantity, textPriceUnit, textTax;
    @FXML
    private Label labelTotal;

    private Invoice tmpInvoice;

    private ArrayList<InvoiceItem> invoiceItemList = new ArrayList<>();

	@Override
	public void setModel(Object model) {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

    }
    public void initDialog(Invoice invoice) {
        tmpInvoice = invoice;
    }

    @FXML
    private void onAdd(){
        //TODO: input validation
        if(true) {
            InvoiceItem tmpItem = new InvoiceItem();

            tmpItem.setInvoiceID(tmpInvoice.getInvoiceID());
            tmpItem.setDescription(textDesc.getText());
            tmpItem.setPricePerUnit(Double.parseDouble(textPriceUnit.getText()));
            tmpItem.setQuantity(Integer.parseInt((textQuantity.getText())));
            tmpItem.setNettoPrice(tmpItem.getPricePerUnit() * tmpItem.getQuantity());
            tmpItem.setTax(Integer.parseInt(textTax.getText()));

            invoiceItemList.add(tmpItem);
            updateTableView();
        }
    }


    @FXML
    private void onFinish() throws IOException {
        tmpInvoice.setInvoiceItems(invoiceItemList);
        ProxySingleton.getInstance().createInvoice(tmpInvoice);

        onCancel();
    }

    @FXML
    private void onDelete() {
        int index = tableView.getSelectionModel().getSelectedIndex();
        if(index >= 0){
            //delete index from List
            invoiceItemList.remove(index);
            updateTableView();
        }

    }

    private void updateTableView(){
        double totalPrice = 0;
        tableView.getItems().setAll(FXCollections.observableArrayList());

        ObservableList<Invoice> resultElements = FXCollections.observableArrayList();
        resultElements.addAll((List) invoiceItemList);
        tableView.setItems(resultElements);



        for (InvoiceItem element : invoiceItemList) totalPrice += element.getNettoPrice();
        labelTotal.setText(Double.toString( totalPrice));
    }
    @FXML
    private void onCancel() {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

}
