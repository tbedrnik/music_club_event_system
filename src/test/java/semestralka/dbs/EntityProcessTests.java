/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs;

import java.sql.Time;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import semestralka.dbs.entities.Band;
import semestralka.dbs.entities.Club;
import semestralka.dbs.entities.Employee;
import semestralka.dbs.entities.Event;
import semestralka.dbs.entities.Person;
import semestralka.dbs.entities.Show;
import semestralka.dbs.entities.embeddable.EmployeeKey;
import semestralka.dbs.entities.embeddable.EventKey;
import semestralka.dbs.entities.embeddable.ShowKey;

/**
 *
 * @author Tomáš
 */
public class EntityProcessTests {
    
    DBConnector DBC;
    
    public EntityProcessTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DBConnector.setInstance("semestralka_aplikace_jar_1.0PU");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        DBC = DBConnector.getInstance();
        
    }
    
    @After
    public void tearDown() {
        
    }
    
    /**
     * Tests Person entity.
     */
    @Test
    public void processTest1() {
        Person p = new Person();
        p.setFirstName("Jaroslav");
        p.setLastName("Novák");
        p.setEmail("jaroslav@novak.cz");
        p.setPhone("606333222");
        
        int totalBefore = DBC.getPeopleTotal();
        DBC.save(p);
        int totalAfter = DBC.getPeopleTotal();
        
        assertEquals(totalBefore+1,totalAfter);
        
        p.setFirstName("Jarda");
        DBC.save(p);
        
        assertEquals("Jarda", p.getFirstName());
        
        DBC.delete(p);
        
        assertEquals(totalBefore,(int)DBC.getPeopleTotal());
    }
    
    /**
     * This test adds new club, event(at club) and show(at event).
     */
    @Test
    public void processTest2() {
        
        Club c = new Club();
        c.setName("Testovaci klub");
        c.setCity("TestTown");
        c.setStreet("TestStreet");
        c.setPostalcode(12345);
        
        int clubsTotalBefore = Integer.valueOf(DBC.getClubsTotal());
        DBC.save(c);
        int clubsTotalAfter = Integer.valueOf(DBC.getClubsTotal());
        
        assertEquals(clubsTotalBefore+1,clubsTotalAfter);
        
        Event e = new Event();
        EventKey ek = new EventKey();
        ek.setClub("Testovaci klub");
        ek.setDate(Date.valueOf("2020-10-10"));
        ek.setTime(Time.valueOf("20:00:00"));
        e.setId(ek);
        e.setName("Test Event");
        
        int eventsTotalBefore = DBC.getFutureEvents().size();
        DBC.save(e);
        int eventsTotalAfter = DBC.getFutureEvents().size();
        
        assertEquals(eventsTotalBefore+1,eventsTotalAfter);
        
        Show s = new Show();
        ShowKey sk = new ShowKey();
        sk.setBand("TestBand");
        sk.setEvent(ek);
        s.setId(sk);
        s.setShowtime(Time.valueOf("21:00:00"));
        
        int showsTotalBefore = DBC.getShowsAtEvent(e).size();
        DBC.save(s);
        int showsTotalAfter = DBC.getShowsAtEvent(e).size();
        
        assertEquals(showsTotalBefore+1,showsTotalAfter);
        
        DBC.delete(s);
        assertEquals(showsTotalBefore, DBC.getShowsAtEvent(e).size());
        
        DBC.delete(e);
        assertEquals(eventsTotalBefore, DBC.getFutureEvents().size());
        
        DBC.delete(c);
        clubsTotalAfter = Integer.valueOf(DBC.getClubsTotal());
        assertEquals(clubsTotalBefore, clubsTotalAfter);
        
    } 
    
    @Test
    public void processTest3() {
        
        Club c = new Club();
        c.setName("Testovaci klub");
        c.setCity("TestTown");
        c.setStreet("TestStreet");
        c.setPostalcode(12345);
        
        int clubsTotalBefore = Integer.valueOf(DBC.getClubsTotal());
        DBC.save(c);
        int clubsTotalAfter = Integer.valueOf(DBC.getClubsTotal());
        
        assertEquals(clubsTotalBefore+1,clubsTotalAfter);
        
        Person p = new Person();
        p.setFirstName("Jaroslav");
        p.setLastName("Novák");
        p.setEmail("jaroslav@novak.cz");
        p.setPhone("606333222");
        
        int personTotalBefore = DBC.getPeopleTotal();
        DBC.save(p);
        int personTotalAfter = DBC.getPeopleTotal();
        
        assertEquals(personTotalBefore+1,personTotalAfter);
        
        Employee e = new Employee();
        EmployeeKey ek = new EmployeeKey();
        
        ek.setClub("Testovaci klub");
        ek.setId(p.getId());
        
        e.setKeyId(ek);
        
        int employeeTotalBefore = DBC.getEmployeesTotal();
        DBC.save(e);
        int employeeTotalAfter = DBC.getEmployeesTotal();
        
        assertEquals(employeeTotalBefore+1, employeeTotalAfter);
        
        List<Person> clubEmployees = DBC.getPeopleWhoWorkInClub(c);
        assertEquals(1, clubEmployees.size());
        
        Person p_ = clubEmployees.get(0);
        
        assertEquals(p_, p);
        
        List<Club> personEmployements = DBC.getClubsWherePersonWorks(p);
        assertEquals(1, personEmployements.size());
        
        Club c_ = personEmployements.get(0);
        
        assertEquals(c_, c);
                
        
        DBC.delete(e);
        DBC.delete(p);
        DBC.delete(c);
    }

    
    
}
