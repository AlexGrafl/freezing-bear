package merp.PresentationModels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import merp.Models.Contact;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FoundContactDialogController extends AbstractController {

    @FXML
    private TableView<Contact> tableView;
    @FXML
    private Button btnSelect, btnCancel;

    public Contact selectedResult = new Contact();
    private List<Contact> resultList;


    @Override
    public void initialize(URL url, ResourceBundle rb){


    }

    public void initDialog(List<Contact> contactList ) {
        resultList = contactList;
        ObservableList<Contact> resultElements = FXCollections.observableArrayList();
        resultElements.addAll(resultList);
        tableView.setItems(resultElements);
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)){
                    onSelect();
                }
            }
        });
    }
    @FXML
    private void onSelect(){
        int index = tableView.getSelectionModel().getSelectedIndex();
        if(index >= 0){
            selectedResult = resultList.get(index);
            onCancel();
        }
    }
    @FXML
    private void onCancel(){
        this.getStage().close();
    }

}
