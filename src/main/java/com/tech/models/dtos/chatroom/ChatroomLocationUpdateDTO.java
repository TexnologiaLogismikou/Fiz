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
public class ChatroomLocationUpdateDTO extends BaseDTO {
    
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
    
    private float lng;
    private float lat;
    private String room_name;
    
    public ChatroomLocationUpdateDTO(){        
    }
    
    @Override
    public String getDTOName() {
        return "ChatroomLocationUpdateDTO";
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }   

    public String getRoom_name() {
        return room_name;
    }
}
