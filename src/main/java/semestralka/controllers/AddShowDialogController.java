/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.persistence.RollbackException;
import semestralka.dbs.DBConnector;
import semestralka.dbs.Validator;
import semestralka.dbs.entities.Band;
import semestralka.dbs.entities.Event;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class AddShowDialogController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button submit, close;
    
    @FXML
    private TextField input_hours, input_minutes;
    
    @FXML
    private ChoiceBox choiceBox_event, choiceBox_band;
    
    private List<Event> event_list;
    private List<Band> band_list;
    
    DBConnector DBC = DBConnector.getInstance();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        event_list = DBC.getFutureEvents();
        for(Event e : event_list) {
            choiceBox_event.getItems().add(e.getName()+" @ "+e.getId().getClub());
        }
        choiceBox_event.getSelectionModel().selectFirst();
        
        band_list = DBC.getBands();
        for(Band b : band_list) {
            choiceBox_band.getItems().add(b.getName());
        }
        choiceBox_band.getSelectionModel().selectFirst();
    }

    @FXML
    private void submit() {
        
        
        
        Event event = event_list.get(choiceBox_event.getSelectionModel().getSelectedIndex());
        Band band = band_list.get(choiceBox_band.getSelectionModel().getSelectedIndex());
        int errors = 0;
        
        if(!Validator.isStringParsableToNumber(input_hours.getText())) {
            errors++;
            input_hours.getStyleClass().add("error");
            input_hours.setPromptText("Invalid");
        }
        if(!Validator.isStringParsableToNumber(input_minutes.getText())) {
            errors++;
            input_minutes.getStyleClass().add("error");
            input_minutes.setPromptText("Invalid");
        }
        
        if(errors==0) {
            input_hours.getStyleClass().remove("error");
            input_minutes.getStyleClass().remove("error");
            input_minutes.setPromptText("Minutes");
            input_hours.setPromptText("Hours");
            
            Integer hours = Integer.valueOf(input_hours.getText());
            Integer minutes = Integer.valueOf(input_minutes.getText());
            if(Validator.validateTime(hours, minutes)) {
                String showtime = hours.toString()+":"+minutes.toString()+":00";

                try {
                    DBC.addShow(event, band, showtime);
                    close();
                } catch (RollbackException e) {

                }  
            } else {
                input_hours.getStyleClass().add("error");
                input_minutes.getStyleClass().add("error");
                input_minutes.setPromptText("Invalid");
                input_hours.setPromptText("Invalid");
            }
            
        }
        
        
              
        
    }
    
    @FXML
    private void close() {
        anchorPane.getScene().getWindow().hide();
    }  
}
