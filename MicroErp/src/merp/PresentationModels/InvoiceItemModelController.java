package merp.PresentationModels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import merp.Models.*;

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
    private Label labelTotal, labelMessage;

    private Invoice tmpInvoice;

    private ArrayList<InvoiceItem> invoiceItemList = new ArrayList<>();
    private InvoiceItemPresentationModel invoiceItemPresentationModel;

	@Override
	public void setModel(Object model) {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

    }
    public void initDialog(Invoice invoice) {

        tmpInvoice = invoice;
        invoiceItemPresentationModel = new InvoiceItemPresentationModel();
        applyBindings();

        EventHandler handleEnter = new EventHandler<KeyEvent>()  {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    onAdd();
                }
            }
        };

        textDesc.setOnKeyPressed(handleEnter);
        textQuantity.setOnKeyPressed(handleEnter);
        textPriceUnit.setOnKeyPressed(handleEnter);
        textTax.setOnKeyPressed(handleEnter);
    }

    @FXML
    private void onAdd(){
        labelMessage.setText("");
        labelMessage.setTextFill(Color.BLACK);

        if(invoiceItemPresentationModel.validateInput()) {
            InvoiceItem tmpItem = new InvoiceItem();

            tmpItem.setInvoiceID(tmpInvoice.getInvoiceID());
            tmpItem = invoiceItemPresentationModel.updateModel(tmpItem);

            /*
            tmpItem.setDescription(textDesc.getText());
            tmpItem.setPricePerUnit(Double.parseDouble(textPriceUnit.getText()));
            tmpItem.setQuantity(Integer.parseInt((textQuantity.getText())));
            tmpItem.setTax(Integer.parseInt(textTax.getText()));
            */
            tmpItem.setNettoPrice(tmpItem.getPricePerUnit() * tmpItem.getQuantity());

            invoiceItemList.add(tmpItem);
            updateTableView();
        }
        else{
            labelMessage.setText("Please check your input!");
            labelMessage.setTextFill(Color.RED);
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

    private void applyBindings() {
        textDesc.textProperty().bindBidirectional(invoiceItemPresentationModel.descriptionProperty());
        textQuantity.textProperty().bindBidirectional(invoiceItemPresentationModel.quantityProperty(), new NumberStringConverter());
        textPriceUnit.textProperty().bindBidirectional(invoiceItemPresentationModel.pricePerUnitProperty(), new NumberStringConverter());
        textTax.textProperty().bindBidirectional(invoiceItemPresentationModel.taxProperty(), new NumberStringConverter());
    }

}
