/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dialogs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import semestralka.controllers.AddEmployeeDialogController;
import semestralka.controllers.AddEventDialogController;
import semestralka.controllers.ClubDetailDialogController;
import semestralka.controllers.EditClubDialogController;
import semestralka.controllers.EditPersonDialogController;
import semestralka.controllers.PersonDetailDialogController;
import semestralka.dbs.entities.Club;
import semestralka.dbs.entities.Person;

/**
 *
 * @author Tomáš
 */
public class DialogMaker extends Stage {
    
    private FXMLLoader loader;
    private Scene scene;
    
    public DialogMaker(DialogWindow dw) {
        try {
            
            loader = new FXMLLoader(getClass().getResource(dw.filepath()));
            scene = new Scene((Parent) loader.load());
            scene.getStylesheets().add("/styles/mainscreen.css");
            this.setScene(scene);
            this.initStyle(StageStyle.UNDECORATED);
            this.initModality(Modality.APPLICATION_MODAL);
            
        } catch (IOException ex) {
            Logger.getLogger(DialogMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DialogMaker(DialogWindow dw, Person p) {
        this(dw);
        switch(dw) {
            case PersonDetail:
                PersonDetailDialogController controller = (PersonDetailDialogController) loader.getController();
                controller.setPerson(p);
                controller.setStage(this);
                break;
            case EditPerson: 
                EditPersonDialogController controller2 = (EditPersonDialogController) loader.getController();
                controller2.setPerson(p);
                controller2.setStage(this);
                break;
            case AddEmployee:
                AddEmployeeDialogController controller3 = (AddEmployeeDialogController) loader.getController();
                controller3.setPerson(p);
                controller3.setStage(this);
                break;
        }
    }
    
    public DialogMaker(DialogWindow dw, Club c) {
        this(dw);
        switch(dw) {
            case EditClub:
                EditClubDialogController controller = (EditClubDialogController) loader.getController();
                controller.setClub(c);
                controller.setStage(this);
                break;
            case ClubDetail:
                ClubDetailDialogController controller2 = (ClubDetailDialogController) loader.getController();
                controller2.setClub(c);
                controller2.setStage(this);
                break;
            case AddEvent:
                AddEventDialogController controller3 = (AddEventDialogController) loader.getController();
                controller3.selectClub(c);
                controller3.setStage(this);
                break;
            case AddEmployee:
                AddEmployeeDialogController controller4 = (AddEmployeeDialogController) loader.getController();
                controller4.setClub(c);
                controller4.setStage(this);
                break;
        }
    }   
}
