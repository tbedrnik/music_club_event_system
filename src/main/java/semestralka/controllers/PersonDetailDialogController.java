package semestralka.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import semestralka.dbs.DBConnector;
import semestralka.dbs.entities.Band;
import semestralka.dbs.entities.Club;
import semestralka.dbs.entities.Person;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class PersonDetailDialogController implements Initializable {
    
    private Stage stage;
    
    public void setStage(Stage stage) {
         this.stage = stage;
    }

    private Person selectedPerson;
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button close;
    
    @FXML
    private Label firstNameLabel, lastNameLabel, emailLabel, phoneLabel;
    
    private final ObservableList<Club> oblistClubs = FXCollections.observableArrayList();
    private final ObservableList<Band> oblistBands = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Club> employeeTable;
    
    @FXML
    private TableColumn<Club, String> employeeTableColumn;
    
    @FXML
    private TableView<Band> memberTable;
    
    @FXML
    private TableColumn<Band, String> memberTableColumn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    
    @FXML
    private void close() {
        stage.hide();
    }
    
    public void setPerson(Person p) {
        selectedPerson = p;
        firstNameLabel.setText(selectedPerson.getFirstName());
        lastNameLabel.setText(selectedPerson.getLastName());
        emailLabel.setText(selectedPerson.getEmail());
        phoneLabel.setText(selectedPerson.getPhone());
        
        DBConnector DBC = DBConnector.getInstance();
        oblistClubs.addAll(DBC.getClubsWherePersonWorks(selectedPerson));
        employeeTable.setItems(oblistClubs);
        oblistBands.addAll(DBC.getBandsWherePersonPlays(selectedPerson));
        memberTable.setItems(oblistBands);
    }
    
}
