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
import semestralka.dbs.entities.Band;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class AddNewBandDialogController implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button submit, close;
    
    @FXML
    private TextField input_name;
    
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
        
        if(name.length()>0) {
            
            Band bandToInsert = new Band();
            bandToInsert.setName(name);
            
            try {
                
                DBConnector.getInstance().save(bandToInsert);
                input_name.getStyleClass().remove("error");
                input_name.setPromptText("Enter band's name");
                close();
                
            } catch (RollbackException e) {
                
                Throwable t;
                for (t = e.getCause(); t != null; t = t.getCause()) {
                    Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, t.toString());
                }
                input_name.getStyleClass().add("error");
                input_name.setPromptText("Name "+name+" is taken.");
                
            }
        } else {
            
            input_name.getStyleClass().add("error");
            input_name.setPromptText("Invalid name.");
            
        }
        
        input_name.setText("");
    }
    
    @FXML
    private void close() {
        anchorPane.getScene().getWindow().hide();
    }
    
}
