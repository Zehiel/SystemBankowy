/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systembankowy;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
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
    @FXML    private CheckBox adminCheckBox;
    
    
    
    private final Operations op = new Operations();
    private int selectedID;
    private Number selectedSearch=0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        searchChoice.getItems().addAll("ID","Imie","Nazwisko","Pesel","Ulica","Admin");
        searchChoice.getSelectionModel().selectFirst();
        adminCheckBox.setSelected(false);
        
        passwordBox.setTooltip(new Tooltip("Haslo musi skladac sie z min 6 znaków"));
        nameBox.setTooltip(new Tooltip("Nie moze byc puste"));
        surnameBox.setTooltip(new Tooltip("Nie moze byc puste"));
        peselBox.setTooltip(new Tooltip("Nie moze byc puste"));
        addressBox.setTooltip(new Tooltip("Nie moze byc puste"));
        
        try {
            refreshList();
        } catch (ClassNotFoundException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Nie znaleziono bazy");
            alert.setHeaderText("Plik nie istnieje lub jest uszkodzony");
            alert.setContentText("Sprawdz czy plik clients.txt znajduje sie w odpowiednim katalogu.");

            alert.showAndWait();
        }
        
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
           
        UnaryOperator<Change> idFilter = change -> {
            String text = change.getText();
            
            if(change.getControlNewText().length()>5){
                return null;
            }
            else{
                if (text.matches("^[0-9]*$")) {
                    return change;
                }
            }
            return null;
           };
           
           
        UnaryOperator<Change> alphaFilter = change -> {
            String text = change.getText();

            if (text.matches("^[A-Za-z]*$")) {
                return change;
            }

            return null;
           };
           TextFormatter<String> textAlpha = new TextFormatter<>(alphaFilter);
           TextFormatter<String> textAlpha2 = new TextFormatter<>(alphaFilter);
           TextFormatter<String> textAlpha3 = new TextFormatter<>(alphaFilter);
           TextFormatter<String> textPesel2 = new TextFormatter<>(peselFilter);
           TextFormatter<String> textID = new TextFormatter<>(idFilter);
           
        
           
        nameBox.setTextFormatter(textAlpha);
        surnameBox.setTextFormatter(textAlpha2);
        peselBox.setTextFormatter(textPesel);
        
        
           
        
        
        
        ClientsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observable, Client oldValue, Client newValue) {
                if(newValue!=null){
                    selectedID = newValue.getAccountNumber();
                }
                
                
                //System.out.println("Wybrano klienta o ID:" + selectedID);
            }
        });
        
        searchChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov,Number oldValue,Number newValue) {
                if(newValue!=null){
                    selectedSearch = newValue;
                    searchBox.setText("");
                    switch(selectedSearch.intValue()){
                        case 0:
                            adminCheckBox.setVisible(false);
                            searchBox.setTextFormatter(textID);
                            searchBox.setVisible(true);
                            break;
                        case 1:
                            adminCheckBox.setVisible(false);
                            searchBox.setTextFormatter(textAlpha3);
                            searchBox.setVisible(true);
                            break;
                        case 2:
                            adminCheckBox.setVisible(false);
                            searchBox.setTextFormatter(textAlpha3);
                            searchBox.setVisible(true);
                            break;
                        case 3:
                            adminCheckBox.setVisible(false);
                            searchBox.setTextFormatter(textPesel2);
                            searchBox.setVisible(true);
                            break;
                        case 4:
                            adminCheckBox.setVisible(false);
                            searchBox.setTextFormatter(null);
                            searchBox.setVisible(true);
                            break;
                        case 5:
                            searchBox.setVisible(false);
                            adminCheckBox.setVisible(true);

                    }
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
                            setText(t.getAccountNumber() + " " + t.getName() + " " + t.getSurname()+ " " + t.getFunds() + " " + t.isAdmin());
                        }
                    }
 
                };
                 
                return cell;
            }
        });
       
    }
    
    private void clearAddInput() {
        nameBox.clear();
        surnameBox.clear();
        peselBox.clear();
        addressBox.clear();
        passwordBox.clear();
    }
    
    @FXML
    private void handleAddButton(ActionEvent event) throws IOException, ClassNotFoundException {
        
        if("".equals(nameBox.getText()) || "".equals(surnameBox.getText()) || "".equals(peselBox.getText()) || "".equals(addressBox.getText()) || "".equals(passwordBox.getText())) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Puste Pole");
            alert.setHeaderText("Nie uzupelniono wszystkich wymaganych pol!");
            alert.setContentText("Uzupelnij brakujace informacje i sprobuj ponownie");

            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Potwierdz");
            alert.setHeaderText("Potwierdzenie");
            alert.setContentText("Czy na pewno chcesz dodac klienta?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
               op.AddClient(nameBox.getText(), surnameBox.getText(), peselBox.getText(), addressBox.getText(), passwordBox.getText());       
               refreshList();
               clearAddInput();
            } else {
               refreshList();   
            }
        }        
    }
    
    @FXML
    private void handleGetAllButton(ActionEvent event) throws ClassNotFoundException {
        refreshList();        
    }
    
    @FXML
    private void handleClearButton(ActionEvent event) throws ClassNotFoundException {
        clearAddInput();     
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
    private void handleMakeAdminButton(ActionEvent event) throws ClassNotFoundException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potwierdz");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz dodac/odebrac uprawnienia klientowi:" +selectedID+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            op.Admin(selectedID);
            refreshList();
        }
        else {
            refreshList();
        }
    }
    @FXML
    private void handleSearchButton(ActionEvent event) throws ClassNotFoundException {
        op.Load("C:/Java/clients.txt");
        List<Client> searchCandidates = new ArrayList<>();
        for (Client current : op.clients){
            switch(selectedSearch.intValue()){
                case 0:
                    if(current.getAccountNumber()==Integer.parseInt(searchBox.getText())){
                        searchCandidates.add(current);
                    }
                    break;
                case 1:
                    if(current.getName() == null ? searchBox.getText() == null : current.getName().equals(searchBox.getText())){
                        searchCandidates.add(current);
                    }
                    break;
                case 2:
                    if(current.getSurname() == null ? searchBox.getText() == null : current.getSurname().equals(searchBox.getText())){
                        searchCandidates.add(current);
                    }
                    break;
                case 3:
                    if(current.getPesel() == null ? searchBox.getText() == null : current.getPesel().equals(searchBox.getText())){
                        searchCandidates.add(current);
                    }                                    
                    break;
                case 4:
                    if(current.getAddress().contains(searchBox.getText())){
                        searchCandidates.add(current);
                    }

                case 5:
                    if(current.isAdmin()==adminCheckBox.isSelected()){
                        searchCandidates.add(current);
                    }
            }
        }
        
        ObservableList<Client> SearchList = FXCollections.observableList(searchCandidates);
        ClientsList.setItems(SearchList);         
        ClientsList.setCellFactory(new Callback<ListView<Client>, ListCell<Client>>(){
 
            @Override
            public ListCell<Client> call(ListView<Client> p) {
                 
                ListCell<Client> cell = new ListCell<Client>(){
 
                    @Override
                    protected void updateItem(Client t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {                            
                            setText(t.getAccountNumber() + " " + t.getName() + " " + t.getSurname()+ " " + t.getFunds() + " " + t.isAdmin());
                        }
                    }
 
                };
                 
                return cell;
            }
        });    
    }
    @FXML
    void handleLogOutButton(ActionEvent event) throws IOException{
            clearAddInput();
            searchBox.clear();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Node node=(Node) event.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            stage.setResizable(false);                        
            Scene scene = new Scene(root);        
            stage.setScene(scene);
            stage.show();
    }
    
    
}
