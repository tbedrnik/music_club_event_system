/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import java.io.Serializable;
import javax.persistence.*;
import semestralka.dbs.entities.embeddable.EventKey;

@Entity
@Table(name="ticket")
public class Ticket implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="ticket")
    private Integer id;
    
    @Embedded
    private EventKey eventKey;
    
    @Column(name="price")
    private Integer price;

    public Ticket() {
        this.id = null;
        this.eventKey = null;
        this.price = null;
    }

    public EventKey getEventKey() {
        return eventKey;
    }

    public void setEventKey(EventKey eventKey) {
        this.eventKey = eventKey;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    } 
    
}
