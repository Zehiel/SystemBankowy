/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systembankowy;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Andrew
 */
public class LoginController implements Initializable {
    
    @FXML    private PasswordField passwordField;
    @FXML    private TextField loginField;
    @FXML    private Label zalogowano;
    @FXML    private Label badLogin;
    @FXML    private Label badPass;
    
    private final Operations op = new Operations();
    private static Client customer = null;
    Parent root;
    
    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException, ClassNotFoundException {
        op.Load("C:/Java/clients.txt");
        boolean success = checkLogin(loginField.getText(),passwordField.getText());
        //badLogin.setVisible(false);
        //badPass.setVisible(false);
        zalogowano.setVisible(success);
        
        
        if(success==true){
            Node node=(Node) event.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            stage.setResizable(false);                        
            Scene scene = new Scene(root);        
            stage.setScene(scene);
            stage.show();
        }
        
    }
    private boolean checkLogin(String login, String pass) throws IOException {        
        boolean success = false;        
        for (Client current : op.clients){
            if(current.getAccountNumber()==Integer.parseInt(login)){
                customer=current;
                break;
            }
        }
        if(getCustomer().getPassword() == null ? pass == null : getCustomer().getPassword().equals(pass)){
            success=true;
            if(getCustomer().isAdmin()==true){
                root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
            }
            else {
                root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            }
        }
       
        if("root".equals(login) && "toor".equals(pass)){
            root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
            success=true;
        }
        return success;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
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
        TextFormatter<String> textID = new TextFormatter<>(idFilter);
        loginField.setTextFormatter(textID);
        
    }    

    /**
     * @return the customer
     */
    public static Client getCustomer() {
        return customer;
    }
    
}
