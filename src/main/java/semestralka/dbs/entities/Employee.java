/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import java.io.Serializable;
import javax.persistence.*;
import semestralka.dbs.entities.embeddable.EmployeeKey;

@Entity
@Table(name="employee")
public class Employee implements Serializable {
    
    @EmbeddedId
    private EmployeeKey keyId;
    
    public Employee() {
        this.keyId = null;
    }
    
    public Employee(Integer id, String club) {
        this.keyId.setClub(club);
        this.keyId.setId(id);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + keyId + '}';
    }

    public EmployeeKey getKeyId() {
        return keyId;
    }

    public void setKeyId(EmployeeKey keyId) {
        this.keyId = keyId;
    }
    
    

}

