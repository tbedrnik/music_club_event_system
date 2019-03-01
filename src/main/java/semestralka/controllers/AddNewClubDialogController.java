/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.persistence.RollbackException;
import semestralka.dbs.DBConnector;
import semestralka.dbs.entities.Club;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class AddNewClubDialogController implements Initializable {

   @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button submit, close;
    
    @FXML
    private TextField input_name, input_street, input_city, input_postalcode;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void submit() {
        String name = input_name.getText().trim().replaceAll(" +", " ");
        String street = input_street.getText().trim().replaceAll(" +", " ");
        String city = input_city.getText().trim().replaceAll(" +", " ");
        String postalcode = input_postalcode.getText().trim().replaceAll(" +", " ");
        
        Club c = new Club();
        int errors = 0;
        
        if(name.length()>0) {
            c.setName(name);
            input_name.getStyleClass().remove("error");
        } else {
            input_name.getStyleClass().add("error");
            input_name.setPromptText("Invalid name.");
            input_name.setText("");
            errors++;
        }
        
        if(street.length()>0) {
            c.setStreet(street);
            input_street.getStyleClass().remove("error");
        } else {
            input_street.getStyleClass().add("error");
            input_street.setPromptText("Invalid street.");
            input_street.setText("");
            errors++;
        }
        
        if(city.length()>0) {
            c.setCity(city);
            input_city.getStyleClass().remove("error");
        } else {
            input_city.getStyleClass().add("error");
            input_city.setPromptText("Invalid city.");
            input_city.setText("");
            errors++;
        }
        
        if(postalcode.length()==5) {
            c.setPostalcode(Integer.valueOf(postalcode));
            input_postalcode.getStyleClass().remove("error");
        } else {
            input_postalcode.getStyleClass().add("error");
            input_postalcode.setPromptText("Invalid postalcode.");
            input_postalcode.setText("");
            errors++;
        }
        
        if(errors==0) {
            try {
                DBConnector.getInstance().save(c);
                close();
            } catch (RollbackException e) {
                Throwable t;
                for (t = e.getCause(); t != null; t = t.getCause()) {
                    Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, t.toString());
                }
            }
        }
    }
    
    @FXML
    private void close() {
        anchorPane.getScene().getWindow().hide();
    }  
}
