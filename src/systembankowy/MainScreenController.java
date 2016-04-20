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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andrew
 */


public class MainScreenController implements Initializable {

    @FXML   private Label userLabel;
    @FXML   private Label fundsLabel;
    @FXML   private TextField toIDField;
    @FXML   private TextField paymentField;
    @FXML   private TextField withdrawField;
    @FXML   private TextField transactionField;
    
    private final Operations op = new Operations();    
    Client customer=LoginController.getCustomer();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        String dane = customer.getName() + " " + customer.getSurname();
        userLabel.setText("Uzytkownik: " + dane);        
        fundsLabel.setText("Stan konta: " + customer.getFunds() + "$");
        
        UnaryOperator<TextFormatter.Change> idFilter = change -> {
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
        
        UnaryOperator<TextFormatter.Change> amountFilter = change -> {
            String text = change.getText();
            
            if(change.getControlNewText().length()>9){
                return null;
            }
            else{
                if (text.matches("^[0-9.]*$")) {
                    return change;
                }
            }
            return null;
           };
        
        TextFormatter<String> textID = new TextFormatter<>(idFilter);
        TextFormatter<String> textWithdraw = new TextFormatter<>(amountFilter);
        TextFormatter<String> textPayment = new TextFormatter<>(amountFilter);
        TextFormatter<String> textTransaction = new TextFormatter<>(amountFilter);
        toIDField.setTextFormatter(textID);
        paymentField.setTextFormatter(textPayment);
        withdrawField.setTextFormatter(textWithdraw);
        transactionField.setTextFormatter(textTransaction);
        
        
    } 
    
    @FXML
    void handlePaymentButton(ActionEvent event) throws ClassNotFoundException{
        op.Load("C:/java/clients.txt");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdz");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz wplacic " + Double.parseDouble(paymentField.getText()) + "$?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                op.Payment(customer.getAccountNumber(),Double.parseDouble(paymentField.getText()));
                paymentField.clear();
                op.Save("C:/java/clients.txt");
                RefreshClient();
                fundsLabel.setText("Stan konta: " + customer.getFunds() + "$");
            }catch (Operations.IncorrectTransaction ex) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Nieprawidlowe parametry transakcji");
                alert2.setHeaderText("Nieprawidlowe parametry");
                alert2.setContentText("Nie podano kwoty,id ,ktores z nich jest nie prawidlowe lub nie posiadasz odpowiednich srodkow");

                alert2.showAndWait();
            }        
        }
        else{
            
        }        
    }
    
    @FXML
    void handleWithdrawButton(ActionEvent event) throws ClassNotFoundException{
        op.Load("C:/java/clients.txt");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdz");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz wyplacic " + Double.parseDouble(withdrawField.getText()) + "$?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                op.Withdraw(customer.getAccountNumber(),Double.parseDouble(withdrawField.getText()));
                withdrawField.clear();
                op.Save("C:/java/clients.txt");
                RefreshClient();
                fundsLabel.setText("Stan konta: " + customer.getFunds() + "$");
            }catch (Operations.IncorrectTransaction ex) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Nieprawidlowe parametry transakcji");
                alert2.setHeaderText("Nieprawidlowe parametry");
                alert2.setContentText("Nie podano kwoty,id ,ktores z nich jest nie prawidlowe lub nie posiadasz odpowiednich srodkow");

                alert2.showAndWait();
            }        
        }
        else{
            
        }        
        
    }
    
    @FXML
    void handleTransactionButton(ActionEvent event) throws ClassNotFoundException{
        op.Load("C:/java/clients.txt");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdz");
        alert.setHeaderText("Potwierdzenie");
        alert.setContentText("Czy na pewno chcesz przelac " + Double.parseDouble(transactionField.getText()) + "$ na konto o nr " + Integer.parseInt(toIDField.getText()) + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                op.Transaction(customer.getAccountNumber(),Integer.parseInt(toIDField.getText()),Double.parseDouble(transactionField.getText()));
                toIDField.clear();
                transactionField.clear();                
                op.Save("C:/java/clients.txt");
                RefreshClient();
                fundsLabel.setText("Stan konta: " + customer.getFunds() + "$");
            }catch (Operations.IncorrectTransaction ex) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Nieprawidlowe parametry transakcji");
                alert2.setHeaderText("Nieprawidlowe parametry");
                alert2.setContentText("Nie podano kwoty,id ,ktores z nich jest nie prawidlowe lub nie posiadasz odpowiednich srodkow");

                alert2.showAndWait();
            }        
        }
        else{
            
        }        
        
    }
    void RefreshClient() throws ClassNotFoundException {
        op.Load("C:/java/clients.txt");
        for (Client current : op.clients){
            if(current.getAccountNumber()==customer.getAccountNumber()){
                customer=current;                
            }                     
        }
    }
    
    @FXML
    void handleLogOutButton(ActionEvent event) throws IOException{
            toIDField.clear();
            paymentField.clear();
            withdrawField.clear();
            transactionField.clear();
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
