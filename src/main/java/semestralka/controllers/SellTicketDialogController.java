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
import javax.persistence.RollbackException;
import semestralka.aplikace.TicketPrinter;
import semestralka.dbs.DBConnector;
import semestralka.dbs.Validator;
import semestralka.dbs.entities.Band;
import semestralka.dbs.entities.Event;
import semestralka.dbs.entities.Ticket;

/**
 * FXML Controller class
 *
 * @author Tomáš
 */
public class SellTicketDialogController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button submit, close;
    
    @FXML
    private TextField input_price;
    
    @FXML
    private ChoiceBox choiceBox_event;
    
    private List<Event> event_list;
    
    private static final Logger LOG = Logger.getLogger(SellTicketDialogController.class.getName());
    
    DBConnector DBC = DBConnector.getInstance();
    
    /**
     * Initializes the controller class. Loads events to ChoiceBox and selects first.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        event_list = DBC.getFutureEvents();
        choiceBox_event.getItems().addAll(event_list);
//        for(Event e : event_list) {
//            choiceBox_event.getItems().add(e.getName()+" @ "+e.getId().getClub());
//        }
        //Select first one so we don't have to validate it on submit
        choiceBox_event.getSelectionModel().selectFirst();
    }

    @FXML
    private void submit() {
        Event selectedEvent = (Event) choiceBox_event.getSelectionModel().getSelectedItem();
        Integer errors = 0;
        String priceText = input_price.getText().trim();
        Integer price = null;
        
        if(!Validator.isStringParsableToNumber(priceText)) {
            errors++;
            input_price.setText("");
            input_price.getStyleClass().add("error");
            input_price.setPromptText("Invalid number");
        } else {
            price = Integer.valueOf(priceText);
            if(price<=0) errors++;
        }
        
        if(errors==0) {
            try {
                LOG.log(Level.SEVERE, "Creating ticket to {0}", selectedEvent);
                Ticket t = new Ticket();
                t.setEventKey(selectedEvent.getId());
                t.setPrice(price);
                LOG.log(Level.SEVERE, "Saving ticket to database");
                DBC.save(t);
                LOG.log(Level.SEVERE, "Ticket saved, creating PDF");
                System.out.println(t.getId());
                
                TicketPrinter.print(t);
//                close();
            } catch (RollbackException e) {
                
            }
        }
    }
    
    
    @FXML
    private void close() {
        input_price.setPromptText("Price");
        input_price.getStyleClass().remove("error");
        anchorPane.getScene().getWindow().hide();
    }  
}
