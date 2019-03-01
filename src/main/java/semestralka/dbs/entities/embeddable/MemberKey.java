/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities.embeddable;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Tomáš
 */
@Embeddable
public class MemberKey implements Serializable {
    @Column(name="band", nullable=false)
    private String band;
    
    @Column(name="musician", nullable=false)
    private Integer musician;

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public Integer getMusician() {
        return musician;
    }

    public void setMusician(Integer musician) {
        this.musician = musician;
    }
}
