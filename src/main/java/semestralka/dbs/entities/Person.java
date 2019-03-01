/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import java.io.Serializable;
import java.util.List;
//import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="id", nullable=false)
    private Integer id;
    
    @Column(name="firstname", nullable=false)
    private String firstName;
    
    @Column(name="lastname", nullable=false)
    private String lastName;
    
    @Column(name="email", nullable=false)
    private String email;
    
    @Column(name="phone", nullable=false)
    private String phone;
        
    @ManyToMany
    @JoinTable(
        name="member",
        joinColumns=@JoinColumn(name="musician", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="band", referencedColumnName="name"))
    private List<Band> bands;
    
    public Integer countBands() {
        return bands.size();
    }

    public Person() {
        this.id = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.phone = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getColumnDetail() {
        return id.toString() + " - " + firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + '}';
    }
}

