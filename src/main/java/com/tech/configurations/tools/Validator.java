/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author KuroiTenshi
 */
public class Validator {
    
    /**
     * Tests a username if the formations / data are acceptable for the program
     * @param username
     * @return true only if the username is not null, doesn't contain spaces or 
     *         special characters and is smaller than 16 characters
     */
    public static boolean usernameValidation(String username) {
        
        //String was empty
        if (username.isEmpty()) {
            return false;
        }
        
        //String contained spaces.
        if (username.trim().length() != username.length()) {
            return false;
        }
        
        //String length was outside our boundries
        if (username.length() > 16 ) {
            return false;
        }
        
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(username);
        
        //String contains special characters
        if (m.find()) {
            return false;
        }   
        
        p = Pattern.compile("^[A-Za-z]");
        m = p.matcher(username);
        
        //String starts with character
        if (!m.find()){
            return false;
        }
        
        return true;               
    }
    
    /**
     * 
     * @param password
     * @return returns true only if password is not null, doesn't contain spaces
     *         and the size is at least 4
     */
    public static boolean passwordValidator(String password) {
        if(password.isEmpty()){
            return false;
        }
        
        if(password.trim().length() < password.length()) {
            return false;
        }
        
        if(password.length() < 4){
            return false;
        }
        
        return true;
    }
    
}
