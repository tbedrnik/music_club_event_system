package semestralka.dbs.entities.embeddable;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;

/**
 * Composite primary key of Show entity
 */
@Embeddable
public class ShowKey implements Serializable {
    @Column(name="band")
    private String band;
    
    @Embedded
    private EventKey event;

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public EventKey getEvent() {
        return event;
    }

    public void setEvent(EventKey event) {
        this.event = event;
    }    
    
}
