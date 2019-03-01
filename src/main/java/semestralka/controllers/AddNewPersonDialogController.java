package semestralka.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import semestralka.dbs.entities.Person;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class AddNewPersonDialogController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button submit, close;
    
    @FXML
    private TextField input_firstName, input_lastName, input_email, input_phone;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void submit() {
        String firstName = input_firstName.getText().trim().replaceAll(" +", " ");
        String lastName = input_lastName.getText().trim().replaceAll(" +", " ");
        String email = input_email.getText().trim().replaceAll(" +", " ");
        String phone = input_phone.getText().trim().replaceAll(" +", " ");
        
        Person p = new Person();
        int errors = 0;
        
        if(firstName.length()>0) {
            p.setFirstName(firstName);
            input_firstName.getStyleClass().remove("error");
        } else {
            input_firstName.getStyleClass().add("error");
            input_firstName.setPromptText("Invalid name.");
            input_firstName.setText("");
            errors++;
        }
        
        if(lastName.length()>0) {
            p.setLastName(lastName);
            input_lastName.getStyleClass().remove("error");
        } else {
            input_lastName.getStyleClass().add("error");
            input_lastName.setPromptText("Invalid name.");
            input_lastName.setText("");
            errors++;
        }
        
        if(email.length()>0) {
            p.setEmail(email);
            input_email.getStyleClass().remove("error");
        } else {
            input_email.getStyleClass().add("error");
            input_email.setPromptText("Invalid email.");
            input_email.setText("");
            errors++;
        }
        
        if(phone.length()>0) {
            p.setPhone(phone);
            input_phone.getStyleClass().remove("error");
        } else {
            input_phone.getStyleClass().add("error");
            input_phone.setPromptText("Invalid phone.");
            input_phone.setText("");
            errors++;
        }
        
        if(errors==0) {
            try {
                DBConnector.getInstance().save(p);
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
