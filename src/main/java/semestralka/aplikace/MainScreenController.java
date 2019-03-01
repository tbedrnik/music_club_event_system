/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.aplikace;

import semestralka.dialogs.DialogMaker;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import semestralka.dialogs.DialogWindow;
import semestralka.dbs.DBConnector;
import semestralka.dbs.entities.Band;
import semestralka.dbs.entities.Club;
import semestralka.dbs.entities.Person;

/**
 * FXML Controller class
 *
 * @author tomas
 */
public class MainScreenController implements Initializable {
    
    /**
     * Main buttons
     *
    */
    @FXML
    private Pane browseBandsPane, browseClubsPane, browsePeoplePane, addEventPane, addShowPane, sellTicketPane;
    
    
    /**
     * Main windows
     */
    @FXML
    private AnchorPane browseBandsWindow, browseClubsWindow, browsePeopleWindow;
    
    /**
     * browseBandsWindow
     */
    @FXML
    private Label bands_total, bands_famous;
    
    @FXML
    private TableView<Band> browseBandsTable;
    
    @FXML
    private TableColumn<Band, String> bandsTableName;
    
    @FXML
    private TableColumn<Band, Integer> bandsTableMembersCount;
    
    private final ObservableList<Band> bandsObservableList = FXCollections.observableArrayList();

    
    /**
     * browseBandsWindow
     */
    @FXML
    private Label clubs_total;
    
    @FXML
    private TableView<Club> browseClubsTable;
    
    @FXML
    private TableColumn<Club, String> clubsTableName, clubsTableStreet, clubsTableCity;
    
    @FXML
    private TableColumn<Club, Number> clubsTablePostalcode;
    
        @FXML
        private AnchorPane browseBandsPane_addNewBand;

        @FXML
        private TextField addNewBand_input;

        @FXML
        private Button addNewBand_submit;
    
    private final ObservableList<Club> clubsObservableList = FXCollections.observableArrayList();
    
    
    /**
     * browsePeopleWindow
     */
    @FXML
    private Label people_total, people_musicians, people_employees;
    
    @FXML
    private TableView<Person> browsePeopleTable;
    
    @FXML
    private TableColumn<Person, String> peopleTableID, peopleTableFirstName, peopleTableLastName, peopleTableEmail, peopleTablePhone;
    
    private final ObservableList<Person> peopleObservableList = FXCollections.observableArrayList();
    
    
    
    //DB
    DBConnector DBC = DBConnector.getInstance();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Browse people
         */
        peopleTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        peopleTableFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        peopleTableLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        peopleTableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        peopleTablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        /**
         * Browse bands
         */
        bandsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        //bandsTableMembersCount.setCellValueFactory(new PropertyValueFactory<>("membersCount"));

        
        /**
         * Browse clubs
         */
        clubsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clubsTableStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        clubsTableCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        clubsTablePostalcode.setCellValueFactory(new PropertyValueFactory<>("postalcode"));
    }

    private void openDialog(DialogWindow dw) {

        Stage stage = new DialogMaker(dw);
        stage.centerOnScreen();
        stage.setOnHidden((WindowEvent event) -> {
            switch(dw) {
                case AddNewBand:
                    updateBandsTable();
                    break;
                case AddNewClub:
                case EditClub:
                    updateClubsTable();
                    break;
                case AddNewPerson:
                case EditPerson:
                    updatePeopleTable();
                    break;
            }
        });
        stage.showAndWait();

    }
    
    private void windowSlideUp(Pane newPane, String title) {
        Stage stage = (Stage) newPane.getScene().getWindow();
        stage.setTitle("MCES // "+title);
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(200),newPane);
        tt.setToY(0);
        tt.play();
    }
    
    private void windowSlideDown(Pane newPane) {
        Stage stage = (Stage) newPane.getScene().getWindow();
        stage.setTitle("MCES");
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(200),newPane);
        tt.setToY(540);
        tt.play();
    }
    
    @FXML
    private void openPeopleWindow() {
        updatePeopleTable();
        windowSlideUp(browsePeopleWindow,"People");
    }
    
    @FXML
    private void closePeopleWindow() {
        windowSlideDown(browsePeopleWindow);
    }
    
    @FXML
    private void openBandsWindow() {
        updateBandsTable();
        windowSlideUp(browseBandsWindow,"Bands");
    }
    
    @FXML
    private void closeBandsWindow() {
        windowSlideDown(browseBandsWindow);
    }
    
    @FXML
    private void openClubsWindow() {
        updateClubsTable();
        windowSlideUp(browseClubsWindow,"Clubs");
    }
    
    @FXML
    private void closeClubsWindow() {
        windowSlideDown(browseClubsWindow);
    }
    
    @FXML
    private void bands_addNewBand() {
        openDialog(DialogWindow.AddNewBand);
    }
    
    @FXML
    private void bands_removeSelected() {
        Band bandToRemove = browseBandsTable.getSelectionModel().getSelectedItem();
        if(bandToRemove!=null) {
            DBC.delete(bandToRemove);
            updateBandsTable();
        }
    }
       
    @FXML
    public void clubs_showDetail() {
        Club selectedClub = browseClubsTable.getSelectionModel().getSelectedItem();
        if(selectedClub != null) {
            Stage stage = new DialogMaker(DialogWindow.ClubDetail, selectedClub);
            stage.showAndWait();
        } else {
            JOptionPane.showMessageDialog(null, "You must first select a club!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    public void clubs_addNewClub() {
        openDialog(DialogWindow.AddNewClub);
    }
    
    @FXML
    public void clubs_editSelected() {
        Club selectedClub = browseClubsTable.getSelectionModel().getSelectedItem();
        if(selectedClub != null) {
            Stage stage = new DialogMaker(DialogWindow.EditClub, selectedClub);
            stage.showAndWait();
            updateClubsTable();
        } else {
            JOptionPane.showMessageDialog(null, "You must first select a club!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    public void clubs_removeSelected() {
        Club clubToRemove = browseClubsTable.getSelectionModel().getSelectedItem();
        if(clubToRemove!=null) {
            DBC.delete(clubToRemove);
            updateClubsTable();
        } else {
            JOptionPane.showMessageDialog(null, "You must first select a club!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void people_showDetail() {
        Person selectedPerson = browsePeopleTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            Stage stage = new DialogMaker(DialogWindow.PersonDetail, selectedPerson);
            stage.showAndWait();
        } else {
            JOptionPane.showMessageDialog(null, "You must first select a person!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void people_addNewPerson() {
        openDialog(DialogWindow.AddNewPerson);
    }
    
    @FXML
    private void people_editSelected() {
        Person selectedPerson = browsePeopleTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            Stage stage = new DialogMaker(DialogWindow.EditPerson, selectedPerson);
            stage.showAndWait();
            updatePeopleTable();
        } else {
            JOptionPane.showMessageDialog(null, "You must first select a person!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void people_removeSelected() {
        Person personToRemove = browsePeopleTable.getSelectionModel().getSelectedItem();
        if(personToRemove!=null) {
            DBC.delete(personToRemove);
            updatePeopleTable();
        } else {
            JOptionPane.showMessageDialog(null, "You must first select a person!", "Nothing Selected", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    private void openAddEventDialog() {
        openDialog(DialogWindow.AddEvent);
    }
    
    @FXML
    private void openAddShowDialog() {
        openDialog(DialogWindow.AddShow);
    }
    
    @FXML
    private void openSellTicketDialog() {
        openDialog(DialogWindow.SellTicket);
    }
    
    private void updateBandsTable() {
        bands_total.setText(DBC.getBandsTotal().toString());
//        bands_famous.setText(DBC.getFamousBandsTotal().toString());
        bandsObservableList.clear();
        bandsObservableList.addAll(DBC.getBands());
        browseBandsTable.setItems(bandsObservableList);
        bandsTableName.setSortType(TableColumn.SortType.ASCENDING);
        browseBandsTable.getSortOrder().add(bandsTableName);
    }

    private void updateClubsTable() {
        clubs_total.setText(DBC.getClubsTotal());
        clubsObservableList.clear();
        clubsObservableList.addAll(DBC.getClubs());
        browseClubsTable.setItems(clubsObservableList);
        clubsTableName.setSortType(TableColumn.SortType.ASCENDING);
        browseClubsTable.getSortOrder().add(clubsTableName);
    }

    private void updatePeopleTable() {
        people_total.setText(DBC.getPeopleTotal().toString());
//        people_musicians.setText(DBC.getMusiciansTotal().toString());
        people_employees.setText(DBC.getEmployeesTotal().toString());

        peopleObservableList.clear();
        peopleObservableList.addAll(DBC.getPeople());
        browsePeopleTable.setItems(peopleObservableList);
        peopleTableFirstName.setSortType(TableColumn.SortType.ASCENDING);
        peopleTableLastName.setSortType(TableColumn.SortType.ASCENDING);
        peopleTableID.setSortType(TableColumn.SortType.ASCENDING);
        browsePeopleTable.getSortOrder().addAll(peopleTableLastName, peopleTableFirstName, peopleTableID);
    }
}
