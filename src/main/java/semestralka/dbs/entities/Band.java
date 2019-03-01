/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="band")
public class Band implements Serializable {
    
    @Id
    @Column(name="name", nullable=false)
    private String name;
    
    @ManyToMany
    @JoinTable(
        name="member",
        joinColumns=@JoinColumn(name="band", referencedColumnName="name"),
        inverseJoinColumns=@JoinColumn(name="musician", referencedColumnName="id"))
    private List<Person> members;
    
    @Transient
    private Integer membersCount;
    
    public Band() {
        this.name = null;
        this.membersCount = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void countMembers() {
        this.membersCount = members.size();
    }
    
    @Override
    public String toString() {
        return "Band{" + "name=" + name + '}';
    }
}

