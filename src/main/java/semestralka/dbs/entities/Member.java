/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import java.io.Serializable;
import javax.persistence.*;
import semestralka.dbs.entities.embeddable.MemberKey;

/**
 * Member of band entity.
 * In JPQL use "BandMember" instead of "Member" (Member is reserved JPQL word)
 */
@Entity(name="BandMember")
@Table(name="member")
public class Member implements Serializable {
   
    @EmbeddedId
    private MemberKey keyId;

    public Member() {
        this.keyId = null;
    }
    
    public Member(Integer musician, String band) {
        this.keyId.setMusician(musician);
        this.keyId.setBand(band);
    }
    
}

