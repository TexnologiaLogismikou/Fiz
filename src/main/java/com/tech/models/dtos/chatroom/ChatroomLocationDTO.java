/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyFloatValidator;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IFloatValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.superclass.BaseDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomLocationDTO extends BaseDTO{
    
    private static final List<IFloatValidator> LATITUDE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyFloatValidator()));
    private static final List<IFloatValidator> LONGITUDE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyFloatValidator()));
    
    public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) 
            throws InappropriateValidatorException, ValidatorNotListedException{

        if (newValidator instanceof IFloatValidator){
            registerFloatValidator((IFloatValidator)newValidator, scope);
        } else {
            throw new InappropriateValidatorException();
        }
    }    
    
    public static void cleanValidator(){
        
        LATITUDE_VALIDATORS.clear();
        LONGITUDE_VALIDATORS.clear();
        
        LATITUDE_VALIDATORS.add(new EmptyFloatValidator());
        LONGITUDE_VALIDATORS.add(new EmptyFloatValidator());
        
        
    }
    
    public static List<String> getValidatorList(ValidationScopes scope) 
            throws ValidatorNotListedException{
        
        List<String> list = new ArrayList<>();
        int i = 0;
        switch(scope){
            case LATITUDE:
                for(ICustomValidator vLookUp:LATITUDE_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case LONGITUDE:
                for(ICustomValidator vLookUp:LONGITUDE_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            default:
                throw new ValidatorNotListedException();   
                
        }      
    }  
    public static boolean removeValidator(ValidationScopes scope, int i) 
            throws ValidatorNotListedException, InappropriateValidatorException{
        
        if(i==0){
            return false;
        }
        
        switch(scope){
            case LATITUDE:
                if(LATITUDE_VALIDATORS.size() >= i + 1){
                    LATITUDE_VALIDATORS.get(i-1).replaceNext(LATITUDE_VALIDATORS.get(i).getNext());
                    LATITUDE_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case LONGITUDE:
                if(LONGITUDE_VALIDATORS.size() >= i + 1){
                    LONGITUDE_VALIDATORS.get(i-1).replaceNext(LONGITUDE_VALIDATORS.get(i).getNext());
                    LONGITUDE_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            default:
                throw new ValidatorNotListedException();                    
        }               
    }     
    
    @Override
    public Pair<Boolean,ResponseEntity> validate() {  
        
        Pair<Boolean,ResponseEntity> currentTest = LONGITUDE_VALIDATORS.get(0).validate(lng);
        if(!currentTest.getLeft()){
            return currentTest;
        }
        
        currentTest = LATITUDE_VALIDATORS.get(0).validate(lat);
            return currentTest;
  
    }
    
    private static void registerFloatValidator(IFloatValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException{   
        
        switch(scope)
        {
            case LATITUDE:
                LATITUDE_VALIDATORS.add(strVal);
                LATITUDE_VALIDATORS.get(0).setNext(strVal);
                break;
            case LONGITUDE:
                LONGITUDE_VALIDATORS.add(strVal);
                LONGITUDE_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();
        }
        
}
    
    
    /* Start of DTO */
    
    private float lng;
    private float lat;
    
    public ChatroomLocationDTO(){

        
        
    }
    
    @Override
    public String getDTOName() {
        return "ChatroomNewMemberDTO";
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }   
}
