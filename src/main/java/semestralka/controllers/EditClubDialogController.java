package semestralka.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.RollbackException;
import semestralka.dbs.DBConnector;
import semestralka.dbs.entities.Band;
import semestralka.dbs.entities.Club;
import semestralka.dbs.entities.Event;
import semestralka.dbs.entities.Person;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class EditClubDialogController implements Initializable {
    
    private Stage stage;
    
    public void setStage(Stage stage) {
         this.stage = stage;
    }

    private Club selectedClub;
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button close, submit;
    
    @FXML
    private TextField input_name, input_street, input_city, input_postalcode;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    @FXML
    private void close() {
        stage.hide();
    }
    
    public void setClub(Club c) {
        selectedClub = c;
        
        input_name.setText(c.getName());
        input_street.setText(c.getStreet());
        input_city.setText(c.getCity());
        input_postalcode.setText(c.getPostalcode().toString());
    }
    
    @FXML
    private void submit() {
        DBConnector DBC = DBConnector.getInstance();
        try {
            DBC.begin();

            if(!input_name.getText().equals(selectedClub.getName())) {
                selectedClub.setName(input_name.getText());
            }

            if(!input_street.getText().equals(selectedClub.getStreet())) {
                selectedClub.setStreet(input_street.getText());
            }

            if(!input_city.getText().equals(selectedClub.getCity())) {
                selectedClub.setCity(input_city.getText());
            }

            if(!Integer.valueOf(input_postalcode.getText()).equals(selectedClub.getPostalcode())) {
                selectedClub.setPostalcode(Integer.valueOf(input_postalcode.getText()));
            }

            //TODO
            DBC.commit();
            
            
        } catch (RollbackException e) {
            
        }
    }
    
}
