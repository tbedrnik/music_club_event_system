/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.dbs;

import java.util.stream.IntStream;

public final class Validator {
    
    public static boolean validateTime(int h, int m) {
        return h>=0 && h<24 && m>=0 && m<60;
    }
    
    public static boolean validateDate(int d, int m, int y) {
        //Basic
        if(y<1000 || y>9999) return false;
        if(m<1 || m>12) return false;
        if(d<1 || d>31) return false;
        
        //February
        if(m==2) {
            //LeapYear
            if(y%4==0) {
                if(d>29) return false;
            } else {
                if(d>28) return false;
            }
        }
        
        //30-day months
        int[] d30 = new int[]{4,6,9,11};
        
        if(IntStream.of(d30).anyMatch(x -> x==m)) {
            if(d>30) return false;
        }
        
        return true;
    }
    
    public static boolean isStringParsableToNumber(String s) {
        try {
            int i = Integer.valueOf(s.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean validatePostalCode(int pc) {
        return pc>=10000 && pc<=99999;
    }
    
    public static boolean validateString(String s) {
        return s.trim().length()>0;
    }
    
    public static boolean validateString(String s, int min, int max) {
        int sl = s.trim().length();
        return sl>=min && sl<=max;
    }
    
    public static boolean validateEmail(String email) {
        String user, domain, tld;
        
        String[] splittedByAt = email.split("@");
        if(splittedByAt.length != 2) return false;
        
        user = splittedByAt[0];
        if(!validateString(user)) return false;
        
        String[] afterAt = splittedByAt[1].split("\\.");
        if(afterAt.length < 2) return false;
        
        tld = afterAt[afterAt.length-1];
        if(!validateString(tld)) return false;
        
        domain = splittedByAt[1].substring(0, splittedByAt[1].length() - tld.length() - 1);
        if(!validateString(domain)) return false;
        
        return true;
    }
    
}
