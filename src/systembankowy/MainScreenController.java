/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systembankowy;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Andrew
 */


public class MainScreenController implements Initializable {

    @FXML
    private Label userLabel;
    @FXML
    private Label fundsLabel;
    
    Client customer=LoginController.getCustomer();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String text = customer.getName() + " " + customer.getSurname();
        userLabel.setText("Uzytkownik: " + text);        
        fundsLabel.setText("Stan konta: " + customer.getFunds() + "$");
    }    
    
}
