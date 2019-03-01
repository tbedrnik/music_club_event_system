/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.*;
import semestralka.dbs.entities.embeddable.ShowKey;

/**
 *
 * @author Tomáš
 */
@Entity
@Table(name="show")
public class Show implements Serializable {

    @EmbeddedId
    private ShowKey id;
    
    @Column(name="showtime")
    private Time showtime;

    public ShowKey getId() {
        return id;
    }

    public void setId(ShowKey id) {
        this.id = id;
    }

    public Time getShowtime() {
        return showtime;
    }

    public void setShowtime(Time showtime) {
        this.showtime = showtime;
    }

    @Override
    public String toString() {
        return "Show{" + "id=" + id + ", showtime=" + showtime + '}';
    }
    
    public String getPrintableShowtime() {
        return showtime.toString().substring(0, 5);
    }
    
}
