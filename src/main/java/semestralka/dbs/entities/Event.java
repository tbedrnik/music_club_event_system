/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;
import semestralka.dbs.entities.embeddable.EventKey;

/**
 *
 * @author Tomáš
 */
@Entity
@Table(name="event")
public class Event implements Serializable {

    @EmbeddedId
    private EventKey id;

    @Column(name="name")
    private String name;

    public EventKey getId() {
        return id;
    }

    public void setId(EventKey id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Date getDate() {
        return id.getDate();
    }
    
    public String getColumnDetail() {
        return id.getDate().toString() + " - " + name;
    }

    @Override
    public String toString() {
        return name + " @ " + id.getClub();
    }
    
    
}
