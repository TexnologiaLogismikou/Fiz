/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IFloatValidator;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.NoValidatorsAssignedException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.superclass.BaseDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomCreationDTO extends BaseDTO{
    
    public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) 
            throws InappropriateValidatorException, ValidatorNotListedException{

        if(newValidator instanceof IStringValidator){
            registerStringValidator((IStringValidator)newValidator, scope);
        } 
        else if (newValidator instanceof INumberValidator){
            registerNumberValidator((INumberValidator)newValidator, scope);
        }
        else if (newValidator instanceof IFloatValidator){
            registerFloatValidator((IFloatValidator)newValidator, scope);
        } else {
            throw new InappropriateValidatorException();
        }
    }    
    
    public static void cleanValidator(){
        throw new UnsupportedOperationException();
    }
    
    public static List<String> getValidatorList(ValidationScopes scope) 
            throws ValidatorNotListedException{
        
        throw new UnsupportedOperationException();        
    }  
    public static boolean removeValidator(ValidationScopes scope, int i) 
            throws ValidatorNotListedException, InappropriateValidatorException{
        
        throw new UnsupportedOperationException();                
    }     
    
    @Override
    public Pair<Boolean,ResponseEntity> validate() 
            throws InappropriateValidatorException, NoValidatorsAssignedException{  
        
        throw new UnsupportedOperationException();        
    }
    
    private static void registerStringValidator(IStringValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException{  
        
        throw new UnsupportedOperationException();                
    }
    
    private static void registerFloatValidator(IFloatValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException{   
        
        throw new UnsupportedOperationException();                
    }
    private static void registerNumberValidator(INumberValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException{ 
        
        throw new UnsupportedOperationException();                
    }
    
    /* Start of DTO */
    
    private String username;
    private String room_name;
    private String room_privilege;
    private String access_method;
    private float room_lat;
    private float room_lng;
    private int room_max_range;
    private boolean hasPassword;
    private String password;

    @Override
    public String getDTOName() {
        return "ChatroomCreationDTO";
    }

    public String getUsername() {
        return username;
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getAccess_method() {
        return access_method;
    }

    public boolean hasPassword() {
        return hasPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getRoom_privilege() {
        return room_privilege;
    }

    public float getRoom_lat() {
        return room_lat;
    }

    public float getRoom_lng() {
        return room_lng;
    }

    public int getRoom_max_range() {
        return room_max_range;
    }    
}
