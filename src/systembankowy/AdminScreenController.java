/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systembankowy;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Andrew
 */
public class AdminScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML    private TextField nameBox;
    @FXML    private TextField surnameBox;
    @FXML    private TextField peselBox;
    @FXML    private TextField addressBox;
    @FXML    private PasswordField passwordBox;    
    @FXML    private Label addSuccess;
    @FXML    private ListView ClientsList;
    
    Operations op = new Operations();
    int selectedID;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observable, Client oldValue, Client newValue) {
                selectedID = newValue.getAccountNumber();
                System.out.println("Wybrano klienta o ID:" + selectedID);
            }
        });
    }
    @FXML
    private void handleAddButton(ActionEvent event) throws IOException, ClassNotFoundException {
        
        op.AddClient(nameBox.getText(), surnameBox.getText(), peselBox.getText(), addressBox.getText(), passwordBox.getText());
        addSuccess.setVisible(true);
    }
    @FXML
    private void handleGetAllButton(ActionEvent event) throws ClassNotFoundException{
        op.Load("C:/Java/clients.txt");
        ObservableList<Client> myObservableList = FXCollections.observableList(op.clients);
        ClientsList.setItems(myObservableList);
         
        ClientsList.setCellFactory(new Callback<ListView<Client>, ListCell<Client>>(){
 
            @Override
            public ListCell<Client> call(ListView<Client> p) {
                 
                ListCell<Client> cell = new ListCell<Client>(){
 
                    @Override
                    protected void updateItem(Client t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getAccountNumber() + " " + t.getName() + " " + t.getSurname()+ " " + t.getFunds() + "$ ");
                        }
                    }
 
                };
                 
                return cell;
            }
        });
       
        
    }
    
    @FXML
    private void handleDeleteButton(ActionEvent event){
            
    }
    
}
