/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import com.tech.configurations.tools.customvalidators.interfaces.CustomValidator;
import com.tech.models.dtos.superclass.BaseDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class Validator {
    private static final HashMap<Integer,CustomValidator> AVAILABLE_VALIDATORS = new HashMap();
    private static final List<String> AVAILABLE_PRIVILEGES = new ArrayList();
    private static final List<String> AVAILABLE_METHODS = new ArrayList();
    private static ResponseEntity response = new ResponseEntity(HttpStatus.NOT_MODIFIED);
    public static ResponseEntity getResponse() {
        return response;
    }
    
    /**
     * Tests a name if the formations / data are acceptable for the program
     * General nameValidator. for specific validations write your own :)
     * @param name
     * @return true only if the name is not null, doesn't contain spaces or 
     *         special characters and is smaller than 16 characters
     */
    public static boolean nameValidation(String name) {
        
        //String was empty
        if (name.isEmpty()) {
            return false;
        }
        
        //String contained spaces.
        if (name.trim().length() != name.length()) {
            return false;
        }
        
        //String length was outside our boundries
        if (name.length() > 16 ) {
            return false;
        }
        
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(name);
        
        //String contains special characters
        if (m.find()) {
            return false;
        }   
        
        p = Pattern.compile("^[A-Za-z]");
        m = p.matcher(name);
        
        //String starts with character
        if (!m.find()){
            return false;
        }
        
        return true;               
    }
    
    /**
     * General passwordValidator. for specific validations write your own :)
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
        
        if(password.length() > 128){
            return false;
        }
        
        return true;
    }
   
    /**
    * 
    * @param accessMethod
    * @return returns true if AccessMethod is either blacklist or whitelist
    */
    public static boolean accessMethodValidator(String accessMethod){
        if(accessMethod.isEmpty()){
            return false;
        }
        
        for (String vLookUp:AVAILABLE_METHODS){
            if (accessMethod.equals(vLookUp)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean roomPrivilegeValidator(String privilege){
        if (privilege.isEmpty()){
            return false;
        }
        
        for(String vLookUp:AVAILABLE_PRIVILEGES){
            if(privilege.equals(vLookUp)){
                return true;
            }
        }
        return false;
    }
    /**
     * General idValidator. for specific validations write your own :)
     * @param id
     * @return returns true if the id is not null or negative
     */
    public static boolean idValidator(Long id){
        if (id == null){
            return false;
        }
        
        if (id <= 0) {
            return false;
        }
        
        return true;
    }
    
    public static Pair validateDTO (BaseDTO DTO) {
        int code = DTO.getDTOName().toLowerCase().hashCode();
        if (AVAILABLE_VALIDATORS.containsKey(code)) {
            response = new ResponseEntity(Responses.VALIDATOR_NOT_FOUND.getData(),HttpStatus.NOT_FOUND);
            return new Pair(false, response);
        }
        boolean booleanResponse = AVAILABLE_VALIDATORS.get(code).validate(DTO);
        return new Pair(booleanResponse, AVAILABLE_VALIDATORS.get(code).response());
    }
    
    public static boolean registerDTOValidator(CustomValidator DTOvalidator) {
        if (AVAILABLE_VALIDATORS.containsKey(DTOvalidator.hashCode())) {
            return false;
        }
        AVAILABLE_VALIDATORS.put(DTOvalidator.hashCode(),DTOvalidator);
        return true;
    }
    
    public static boolean registerPrivilege(String newPrivilege){
        if (AVAILABLE_PRIVILEGES.contains(newPrivilege)){
            return false;
        }
        AVAILABLE_PRIVILEGES.add(newPrivilege);
        return true;
    }
    
    public static boolean registerMethod(String newMethod){
        if (AVAILABLE_PRIVILEGES.contains(newMethod)){
            return false;
        }
        AVAILABLE_PRIVILEGES.add(newMethod);
        return true;
    }
}
