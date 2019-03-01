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
import semestralka.dbs.entities.Event;
import semestralka.dbs.entities.Person;
import semestralka.dbs.entities.Show;
import semestralka.dbs.entities.embeddable.EventKey;
import semestralka.dbs.entities.embeddable.ShowKey;

/**
 *
 * @author Tomáš
 */
public class DBConnectorTest {
    
    DBConnector DBC;
    
    public DBConnectorTest() {
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
     * Test of getInstance method, of class DBConnector.
     */
    @Test
    public void testGetInstance() {
        assertTrue(DBC instanceof DBConnector);
    }

    /**
     * Test of getEntityManager method, of class DBConnector.
     */
    @Test
    public void testGetEntityManager() {
        assertTrue(DBC.getEntityManager() instanceof EntityManager);
    }

    
    
}
