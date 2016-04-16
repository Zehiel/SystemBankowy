/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systembankowy;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
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
    @FXML    private ChoiceBox searchChoice;
    @FXML    private TextField searchBox;
    
    private final Operations op = new Operations();
    private int selectedID;
    private Number selectedSearch;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        searchChoice.getItems().addAll("ID","Imie","Nazwisko","Pesel","Ulica","Admin");
        searchChoice.getSelectionModel().selectFirst();
        
        passwordBox.setTooltip(new Tooltip("Haslo musi skladac sie z min 6 znaków"));
        nameBox.setTooltip(new Tooltip("Nie moze byc puste"));
        surnameBox.setTooltip(new Tooltip("Nie moze byc puste"));
        peselBox.setTooltip(new Tooltip("Nie moze byc puste"));
        addressBox.setTooltip(new Tooltip("Nie moze byc puste"));
        
       
        
        UnaryOperator<Change> peselFilter = change -> {
            String text = change.getText();
            
            if(change.getControlNewText().length()>12){
                return null;
            }
            else{
                if (text.matches("^[0-9]*$")) {
                    return change;
                }
            }
            return null;
           };
           TextFormatter<String> textPesel = new TextFormatter<>(peselFilter);
           
        UnaryOperator<Change> alphaFilter = change -> {
            String text = change.getText();

            if (text.matches("^[A-Za-z]*$")) {
                return change;
            }

            return null;
           };
           TextFormatter<String> textAlpha = new TextFormatter<>(alphaFilter);
           TextFormatter<String> textAlpha2 = new TextFormatter<>(alphaFilter);
           
        
           
        nameBox.setTextFormatter(textAlpha);
        surnameBox.setTextFormatter(textAlpha2);
        peselBox.setTextFormatter(textPesel);
           
        
        
        
        ClientsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observable, Client oldValue, Client newValue) {
                if(newValue!=null){
                    selectedID = newValue.getAccountNumber();
                }
                
                System.out.println("Wybrano klienta o ID:" + selectedID);
            }
        });
        
        searchChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov,Number oldValue,Number newValue) {
                if(newValue!=null){
                    selectedSearch = newValue;
                }
                System.out.println("Wybrano opcje wyszukiwania nr:" + selectedSearch);
            }
        });
        
    }
    private void refreshList() throws ClassNotFoundException {
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
    private void handleAddButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potwierdz");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz dodac klienta?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
           op.AddClient(nameBox.getText(), surnameBox.getText(), peselBox.getText(), addressBox.getText(), passwordBox.getText());       
           refreshList();  
        } else {
           refreshList();   
        }
         
    }
    @FXML
    private void handleGetAllButton(ActionEvent event) throws ClassNotFoundException {
        refreshList();        
    }
    
    @FXML
    private void handleDeleteButton(ActionEvent event) throws ClassNotFoundException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potwierdz");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz usunac klienta od ID:" +selectedID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            op.DeleteClient(selectedID);
            int selectedIdx = ClientsList.getSelectionModel().getSelectedIndex();         
            final int newSelectedIdx =
               (selectedIdx == ClientsList.getItems().size() - 1)
                  ? selectedIdx - 1
                  : selectedIdx;
            refreshList();
            ClientsList.getSelectionModel().select(newSelectedIdx); 
        } else {
           refreshList();   
        }
    }
    @FXML
    private void handleSearchButton(ActionEvent event) {
        switch(selectedSearch.intValue()){
            case 0:
                
            case 1:
                
            case 2:
                
            case 3:
                
            case 4:
                
            case 5:
                
            case 6:
        }
    }
    
}
