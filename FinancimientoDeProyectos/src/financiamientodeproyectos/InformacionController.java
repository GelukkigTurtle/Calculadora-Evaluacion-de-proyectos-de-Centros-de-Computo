/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package financiamientodeproyectos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class InformacionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML //  fx:id="btnOK"
    private Button btnOK; // Value injected by FXMLLoader
      
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
     // Handler for Button[Button[id=null, styleClass=button]] onAction
    public void clickOKey(ActionEvent event) {
          btnOK.getScene().getWindow().hide();
    }

}
