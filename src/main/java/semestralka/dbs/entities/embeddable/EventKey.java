package semestralka.dbs.entities.embeddable;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;

@Embeddable
public class EventKey implements Serializable {
    @Column(name="date")
    private Date date;
    
    @Column(name="time")
    private Time time;
    
    @Column(name="club")
    private String club;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }
    
    public String getPrintableDateTime() {
        String[] d = date.toString().split("-");
        String[] t = time.toString().split(":");
        return d[2]+"."+d[1]+"."+d[0]+" "+t[0]+":"+t[1];
    }
}
