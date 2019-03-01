/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.RollbackException;
import semestralka.dbs.DBConnector;
import semestralka.dbs.entities.Club;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class AddEventDialogController implements Initializable {
    
    private Stage stage;
    
    public void setStage(Stage stage) {
         this.stage = stage;
    }
    
    public void selectClub(Club c) {
        choiceBox_club.getSelectionModel().select(c.getName()+" ("+c.getCity()+")");
        choiceBox_club.setDisable(true);
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button submit, close;
    
    @FXML
    private TextField input_name, input_day, input_month, input_year, input_hours, input_minutes;
    
    @FXML
    private ChoiceBox choiceBox_club;
    
    private List<Club> club_list;
    
    DBConnector DBC = DBConnector.getInstance();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        club_list = DBC.getClubs();
        for(Club c : club_list) {
            choiceBox_club.getItems().add(c.getName()+" ("+c.getCity()+")");
        }
        choiceBox_club.getSelectionModel().selectFirst();
    }

    @FXML
    private void submit() {
        System.out.println(choiceBox_club.getSelectionModel().getSelectedItem());
        String name = input_name.getText().trim().replaceAll(" +", " ");
        Integer day = Integer.valueOf(input_day.getText());
        Integer month = Integer.valueOf(input_month.getText());
        Integer year = Integer.valueOf(input_year.getText());
        Integer hours = Integer.valueOf(input_hours.getText());
        Integer minutes = Integer.valueOf(input_minutes.getText());
        Club club = club_list.get(choiceBox_club.getSelectionModel().getSelectedIndex());
        
        String date = year.toString()+"-"+month.toString()+"-"+day.toString();
        String time = hours.toString()+":"+minutes.toString()+":00";
        
        //TODO: Date and time validation

        int errors = 0;
        
        if(name.length()>0) {
            input_name.getStyleClass().remove("error");
        } else {
            input_name.getStyleClass().add("error");
            input_name.setPromptText("Invalid name.");
            input_name.setText("");
            errors++;
        }
        
        if(errors==0) {
            try {
                DBC.addEvent(name, date, time, club);
                close();
            } catch (RollbackException e) {
                
            }
            
        }
        
        
    }
    
    @FXML
    private void close() {
        anchorPane.getScene().getWindow().hide();
    }  
}
