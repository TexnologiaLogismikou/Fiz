package com.tech.models.dtos;

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


public class MessageDTO extends BaseDTO {
    
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
    public Pair<Boolean,ResponseEntity> validate() {  
        
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
    
    private String message;
    private String username;
    private String chatroom_name;
    private float lng;
    private float lat;
    private int ttl;
    
    @Override
    public String getDTOName() {
        return "MessageDTO";
    }

    public MessageDTO() {
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getChatroom_name() {
        return chatroom_name;
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }

    public int getTtl() {
        return ttl;
    }
}
