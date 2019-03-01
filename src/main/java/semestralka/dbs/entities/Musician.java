/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="musician")
public class Musician implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="id", nullable=false)
    private Integer musician_id;
    
    @OneToOne
    @PrimaryKeyJoinColumn(name="id",referencedColumnName="id")
    private Person person;
    
    public Musician() {
        this.musician_id = null;
    }

    public Integer getId() {
        return musician_id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Musician{" + "id=" + musician_id + '}';
    }
}

