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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.RollbackException;
import semestralka.dbs.DBConnector;
import semestralka.dbs.Validator;
import semestralka.dbs.entities.Club;
import semestralka.dbs.entities.Person;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class AddEmployeeDialogController implements Initializable {
    
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    private Person selectedPerson;
    
    private Club selectedClub;

    public void setPerson(Person p) {
        this.selectedPerson = p;
        choiceBox_person.getSelectionModel().select(p.getFirstName() + p.getLastName());
        choiceBox_person.setDisable(true);
        
        choiceBox_club.getItems().clear();
        club_list = DBC.getClubsWherePersonDoesntWork(p);
        for(Club c : club_list) {
            choiceBox_club.getItems().add(c.getName()+" ("+c.getCity()+")");
        }
        choiceBox_club.getSelectionModel().selectFirst();
    }

    public void setClub(Club c) {
        this.selectedClub = c;
        choiceBox_club.getSelectionModel().select(c.getName()+" ("+c.getCity()+")");
        choiceBox_club.setDisable(true);
        
        choiceBox_person.getItems().clear();
        person_list = DBC.getPeopleWhoDontWorkInClub(c);
        for(Person p : person_list) {
            choiceBox_person.getItems().add(p.getFirstName() + " " + p.getLastName());
        }
        choiceBox_person.getSelectionModel().selectFirst();
    }
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button submit, close;
    
    @FXML
    private ChoiceBox choiceBox_club, choiceBox_person;
    
    private List<Club> club_list;
    private List<Person> person_list;
    
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
        
        person_list = DBC.getPeople();
        for(Person p : person_list) {
            choiceBox_person.getItems().add(p.getFirstName() + " " + p.getLastName());
        }
        choiceBox_person.getSelectionModel().selectFirst();
    }

    @FXML
    private void submit() {
        Club c = club_list.get(choiceBox_club.getSelectionModel().getSelectedIndex());
        Person p = person_list.get(choiceBox_person.getSelectionModel().getSelectedIndex());

        try {
            DBC.addEmployee(c, p);
            close();
        } catch (RollbackException e) {
        }
    }
    
    @FXML
    private void close() {
        anchorPane.getScene().getWindow().hide();
    }  
}
