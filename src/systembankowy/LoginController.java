/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systembankowy;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;

/**
 *
 * @author Andrew
 */
public class LoginController implements Initializable {
    
    @FXML    private PasswordField passwordField;
    @FXML    private TextField loginField;
    @FXML    private Label zalogowano;
    
    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
        String login;        
        String password;
        login = loginField.getText();        
        password = passwordField.getText();
        boolean success = checkLogin(login,password);
        zalogowano.setVisible(success);
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        stage.setResizable(false);
        Parent root;
        root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));        
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.show();
    }
    private boolean checkLogin(String login, String pass) {
        boolean success = false;
        if("root".equals(login) && "toor".equals(pass)) success=true;
        return success;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
