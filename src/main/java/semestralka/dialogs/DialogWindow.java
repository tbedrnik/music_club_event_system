/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dialogs;

/**
 *
 * @author Tomáš
 */
public enum DialogWindow {
    AddNewBand,
    ClubDetail,
    AddNewClub,
    EditClub,
    PersonDetail,
    AddNewPerson,
    EditPerson,
    AddEvent,
    AddShow,
    SellTicket,
    AddEmployee;
    
    public String filepath() {
        return "/fxml/"+this.name()+"Dialog.fxml";
    }
}
