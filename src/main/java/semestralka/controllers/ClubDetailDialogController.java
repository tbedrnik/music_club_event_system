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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.RollbackException;
import javax.swing.JOptionPane;
import semestralka.calendar.CalendarView;
import semestralka.dbs.DBConnector;
import semestralka.dbs.entities.Club;
import semestralka.dbs.entities.Event;
import semestralka.dbs.entities.Person;
import semestralka.dialogs.DialogMaker;
import semestralka.dialogs.DialogWindow;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class ClubDetailDialogController implements Initializable {
    
    private Stage stage;
    
    public void setStage(Stage stage) {
         this.stage = stage;
    }

    private Club selectedClub;
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button close, addEmployeeButton;
    
    @FXML
    private Label nameLabel, streetLabel, cityLabel, postalcodeLabel;
    
    private final ObservableList<Person> oblistEmployees = FXCollections.observableArrayList();
    private final ObservableList<Event> oblistEvents = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Person> employeesTable;
    
    @FXML
    private TableColumn<Person, String> employeesTableColumn;
    
    @FXML
    private TableView<Event> eventsTable;
    
    @FXML
    private TableColumn<Event, String> eventsTableColumn;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeesTableColumn.setCellValueFactory(new PropertyValueFactory<>("columnDetail"));
        eventsTableColumn.setCellValueFactory(new PropertyValueFactory<>("columnDetail"));
    }
    
    @FXML
    private void close() {
        stage.hide();
    }
    
    public void setClub(Club c) {
        selectedClub = c;
        nameLabel.setText(selectedClub.getName());
        streetLabel.setText(selectedClub.getStreet());
        cityLabel.setText(selectedClub.getPostalcode().toString()+", "+selectedClub.getCity());
        updateEventsTable();
        updateEmployeesTable();
    }
    
    @FXML
    private void addEvent() {
        Stage stg = new DialogMaker(DialogWindow.AddEvent, selectedClub);
        stg.centerOnScreen();
        stg.setOnHidden((WindowEvent event) -> {
            updateEventsTable();
        });
        stg.showAndWait();
    }
    
    @FXML
    private void showLineUp() {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            CalendarView cw = new CalendarView(selectedEvent);
            cw.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "You must first select an event!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void removeEvent() {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            DBConnector.getInstance().deleteEvent(eventsTable.getSelectionModel().getSelectedItem());
            updateEventsTable();
        } else {
            JOptionPane.showMessageDialog(null, "You must first select an event!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void addEmployee() {
        Stage stg = new DialogMaker(DialogWindow.AddEmployee, selectedClub);
        stg.centerOnScreen();
        stg.setOnHidden((WindowEvent event) -> {
            updateEmployeesTable();
        });
        stg.showAndWait();
    }
    
    @FXML
    private void showPersonDetail() {
        Person selectedPerson = employeesTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            Stage stg = new DialogMaker(DialogWindow.PersonDetail, employeesTable.getSelectionModel().getSelectedItem());
            stg.showAndWait();
        } else {
            JOptionPane.showMessageDialog(null, "You must first select a person!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void removeEmployee() {
        Person selectedPerson = employeesTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            try {
                DBConnector.getInstance().removeEmployee(selectedClub, selectedPerson);
                updateEmployeesTable();
            } catch (RollbackException e) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "You must first select a person!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateEventsTable() {
        oblistEvents.clear();
        oblistEvents.addAll(DBConnector.getInstance().getEventsAtClub(selectedClub));
        eventsTable.setItems(oblistEvents);
    }
    
    private void updateEmployeesTable() {
        oblistEmployees.clear();
        oblistEmployees.addAll(DBConnector.getInstance().getPeopleWhoWorkInClub(selectedClub));
        employeesTable.setItems(oblistEmployees);
        
        if(oblistEmployees.size()==DBConnector.getInstance().getPeopleTotal()) addEmployeeButton.setDisable(true);
    }
}
