/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Tomáš
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    semestralka.dbs.DBConnectorTest.class,
    semestralka.dbs.ValidatorTest.class,
    semestralka.dbs.entities.BandTest.class
})
public class UnitTestSuite {
    
}
