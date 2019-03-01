/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs.entities;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author tomas
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BandTest {
    
    private Band b = new Band();
    private final String testDataName = "Testband Ivana Mládka";
    
    @Before
    public void setUp() {
        b.setName(testDataName);
    }
    
    @Test
    public void test_1_SetName() {
        b.setName("Testband Ivana Mládka a další");
        assertEquals("Testband Ivana Mládka a další", b.getName());
    }
    
    @Test
    public void test_2_GetName() {
        assertEquals("Testband Ivana Mládka", b.getName());
    }

    @Test
    public void test_3_ToString() {
        assertEquals("Band{name=Testband Ivana Mládka}", b.toString());
    }
    
}
