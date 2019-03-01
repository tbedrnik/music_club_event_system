/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="club")
public class Club implements Serializable {
    @Id
    @Column(name="name", nullable=false)
    private String name;
    
    @Column(name="city", nullable=false)
    private String city;
    
    @Column(name="street", nullable=false)
    private String street;
    
    @Column(name="postalcode", nullable=false)
    private Integer postalcode;

    public Club() {
        this.name = null;
        this.city = null;
        this.street = null;
        this.postalcode = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(Integer postalcode) {
        this.postalcode = postalcode;
    }

    @Override
    public String toString() {
        return "Club{" + "name=" + name + ", city=" + city + ", street=" + street + ", postalcode=" + postalcode + '}';
    }
    
}