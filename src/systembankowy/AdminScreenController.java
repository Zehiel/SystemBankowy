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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.lang.String;

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
    @FXML    private TextArea ClientsArea;
    
    Operations op = new Operations();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    private void handleAddButton(ActionEvent event) throws IOException, ClassNotFoundException {
        
        op.AddClient(nameBox.getText(), surnameBox.getText(), peselBox.getText(), addressBox.getText(), passwordBox.getText());
        addSuccess.setVisible(true);
    }
    @FXML
    private void handleGetAllButton(ActionEvent event) throws ClassNotFoundException{
        op.Load("C:/Java/clients.txt");
        String napis = new String();
        for (Client item : op.clients) {   
            napis = napis + item.getAccountNumber() + " " + item.getName() + " " + item.getSurname() + " " + item.getFunds() + "\n";
        }
        ClientsArea.setText(napis);
    }
    
}
