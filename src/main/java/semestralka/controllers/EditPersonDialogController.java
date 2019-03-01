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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.RollbackException;
import semestralka.dbs.DBConnector;
import semestralka.dbs.Validator;
import semestralka.dbs.entities.Person;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class EditPersonDialogController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button submit, close;
    
    @FXML
    private TextField input_firstName, input_lastName, input_email, input_phone;
    
    @FXML
    private Label editPersonLabel;
    
    private Stage stage;
    
    public void setStage(Stage stage) {
         this.stage = stage;
    }
    
    private Person selectedPerson;
    
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
        
        try {
            int errors=0;
            DBConnector.getInstance().begin();

            if(Validator.validateString(firstName)) {
                selectedPerson.setFirstName(firstName);
                input_firstName.getStyleClass().remove("error");
            } else {
                errors++;
                input_firstName.getStyleClass().add("error");
                input_firstName.setPromptText("Invalid name.");
                input_firstName.setText("");
            }

            if(Validator.validateString(lastName)) {
                selectedPerson.setLastName(lastName);
                input_lastName.getStyleClass().remove("error");
            } else {
                errors++;
                input_lastName.getStyleClass().add("error");
                input_lastName.setPromptText("Invalid name.");
                input_lastName.setText("");
            }

            if(Validator.validateEmail(email)||email.equals("")) {
                selectedPerson.setEmail(email);
                input_email.getStyleClass().remove("error");
            } else {
                errors++;
                input_email.getStyleClass().add("error");
                input_email.setPromptText("Invalid email.");
            }

            if(Validator.validateString(phone, 0, 9)) {
                selectedPerson.setPhone(phone);
                input_phone.getStyleClass().remove("error");
            } else {
                errors++;
                input_phone.getStyleClass().add("error");
                input_phone.setPromptText("Invalid phone.");
            }
            
            DBConnector.getInstance().commit();
            
            if(errors==0) close();
            
        } catch (RollbackException ex) {
            Logger.getLogger(EditPersonDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @FXML
    private void close() {
        stage.hide();
    }  
    
    public void setPerson(Person p) {
        selectedPerson = p;
        editPersonLabel.setText("Edit person "+selectedPerson.getId().toString());
        input_firstName.setText(selectedPerson.getFirstName());
        input_lastName.setText(selectedPerson.getLastName());
        input_phone.setText(selectedPerson.getPhone());
        input_email.setText(selectedPerson.getEmail());
    }
    
}
