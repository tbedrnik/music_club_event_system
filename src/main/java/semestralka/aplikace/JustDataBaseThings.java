/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.aplikace;

import java.sql.Date;
import java.sql.Time;
import semestralka.dbs.DBConnector;
import semestralka.dbs.entities.Band;
import semestralka.dbs.entities.Event;
import semestralka.dbs.entities.embeddable.EventKey;

/**
 *
 * @author Tomáš
 */
public class JustDataBaseThings {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBConnector DBC = DBConnector.getInstance();
        
        Event e = DBC.getFutureEvents().get(0);
        
        Band b = DBC.getBands().get(0);
        
        DBC.addShow(e, b, "12:00:00");
    }
    
}
