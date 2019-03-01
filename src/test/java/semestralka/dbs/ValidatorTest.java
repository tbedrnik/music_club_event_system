/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomáš
 */
public class ValidatorTest {
    
    public ValidatorTest() {
    }

    @Test
    public void testValidateTime() {
        
        assertTrue("Test time: 0:0", Validator.validateTime(0, 0));
        
        assertTrue("Test time: 23:59", Validator.validateTime(23, 59));
        
        assertFalse("Test time: 24:60", Validator.validateTime(24, 60));
        
        assertFalse("Test time: -1:-1", Validator.validateTime(-1, -1));
        
        assertFalse("Test time: 23:60", Validator.validateTime(23, 60));
        
        assertFalse("Test time: 24:59", Validator.validateTime(24, 59));
        
        assertFalse("Test time: 23:-1", Validator.validateTime(23, -1));
        
        assertFalse("Test time: -1:59", Validator.validateTime(-1, 59));
        
    }

    @Test
    public void testValidateDate() {
        
        assertFalse("Test date: 31.4.2018", Validator.validateDate(31, 4, 2018));
        
        assertTrue("Test date: 30.4.2018", Validator.validateDate(30, 4, 2018));
        
        assertTrue("Test date: 29.2.2020", Validator.validateDate(29, 2, 2020));
        
        assertFalse("Test date: 29.2.2021", Validator.validateDate(29, 2, 2021));
        
        assertTrue("Test date: 31.12.2000", Validator.validateDate(31, 12, 2000));
        
        assertFalse("Test date: 12.12.333", Validator.validateDate(12, 12, 333));
        
    }

    @Test
    public void testIsStringParsableToNumber() {
        String toParse;
        
        toParse = "";
        assertFalse(toParse, Validator.isStringParsableToNumber(toParse));
        
        toParse = "Ahojky";
        assertFalse(toParse, Validator.isStringParsableToNumber(toParse));
        
        toParse = "Koblížek22";
        assertFalse(toParse, Validator.isStringParsableToNumber(toParse));
        
        toParse = "222";
        assertTrue(toParse, Validator.isStringParsableToNumber(toParse));
        
        toParse = "22 Koblížek";
        assertFalse(toParse, Validator.isStringParsableToNumber(toParse));
        
        toParse = " 123 ";
        assertTrue(toParse, Validator.isStringParsableToNumber(toParse));
    }

    @Test
    public void testValidatePostalCode() {
        int pc = 11025;
        assertTrue(Validator.validatePostalCode(pc));
        pc = 01234;
        assertFalse(Validator.validatePostalCode(pc));
        pc = 100000;
        assertFalse(Validator.validatePostalCode(pc));
        pc = 99999;
        assertTrue(Validator.validatePostalCode(pc));     
    }

    @Test
    public void testValidateString_String() {
        String str = "Koblížeček Pomazánka Ocet Vejfuk";
        assertTrue(str,Validator.validateString(str));
        str="      ";
        assertFalse(str,Validator.validateString(str));
        str="  2222 ";
        assertTrue(str,Validator.validateString(str));
        str="-";
        assertTrue(str,Validator.validateString(str));
    }

    @Test
    public void testValidateString_3args() {
        String str = " abc ";
        int MIN = 4;
        int MAX = 10;
        assertFalse(str,Validator.validateString(str, MIN, MAX));
        str = "abcd    ";
        assertTrue(str,Validator.validateString(str, MIN, MAX));
        str = "Leporelo!!";
        assertTrue(str,Validator.validateString(str, MIN, MAX));
        str = "Leporelo!!!";
        assertFalse(str,Validator.validateString(str, MIN, MAX));
    }

    @Test
    public void testValidateEmail() {
        String email = "email@email.email";
        assertTrue(email, Validator.validateEmail(email));
        email = "kolo@brnda";
        assertFalse(email, Validator.validateEmail(email));
        email = "kobka@@seznam.com";
        assertFalse(email, Validator.validateEmail(email));
        email = "hotel@na.zavisti.cz";
        assertTrue(email, Validator.validateEmail(email));
    }
    
}