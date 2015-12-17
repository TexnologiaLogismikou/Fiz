/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyNumberValidator;
import com.tech.configurations.tools.customvalidators.elements.EmptyStringValidator;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
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
public class MessageHistoryRequestDTO extends BaseDTO{
    
    private static final List<IStringValidator> ROOM_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> MEMBER_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<INumberValidator> LAT_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyNumberValidator()));
    private static final List<INumberValidator> LNG_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyNumberValidator()));
   
    
    public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) 
            throws InappropriateValidatorException, ValidatorNotListedException{

         if(newValidator instanceof IStringValidator){
            registerStringValidator((IStringValidator)newValidator, scope);
        } 
        else if (newValidator instanceof INumberValidator){
            registerNumberValidator((INumberValidator)newValidator, scope);
        } else {
            throw new InappropriateValidatorException();
        }
    }    
    
    public static void cleanValidator(){    //adeiazzei ti lista kai bazei to prwto stoixeio
      {
        ROOM_NAME_VALIDATORS.clear();              
        ROOM_NAME_VALIDATORS.add(new EmptyStringValidator());
        
        MEMBER_NAME_VALIDATORS.clear();              
        MEMBER_NAME_VALIDATORS.add(new EmptyStringValidator());
        
        LAT_VALIDATORS.clear();              
        LAT_VALIDATORS.add(new EmptyNumberValidator());
        
        LNG_VALIDATORS.clear();              
        LNG_VALIDATORS.add(new EmptyNumberValidator());
    }
    
    }
    
    public static List<String> getValidatorList(ValidationScopes scope) 
            throws ValidatorNotListedException{
        
        List<String> list = new ArrayList<>();
        int i = 0;
        switch(scope){
            case ROOM_NAME:
                for(ICustomValidator vLookUp:ROOM_NAME_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case USER_NAME:
                for(ICustomValidator vLookUp:MEMBER_NAME_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case LATITUDE:
                for(ICustomValidator vLookUp:LAT_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case LONGITUDE: 
                for(ICustomValidator vLookUp:LNG_VALIDATORS){
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
            case ROOM_NAME:
                if(ROOM_NAME_VALIDATORS.get(i) != null){
                    ROOM_NAME_VALIDATORS.get(i-1).replaceNext(ROOM_NAME_VALIDATORS.get(i).getNext());
                    ROOM_NAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case USER_NAME:
                if(MEMBER_NAME_VALIDATORS.get(i) != null){
                    MEMBER_NAME_VALIDATORS.get(i-1).replaceNext(MEMBER_NAME_VALIDATORS.get(i).getNext());
                    MEMBER_NAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case LATITUDE:
                if(LAT_VALIDATORS.get(i) != null){
                    LAT_VALIDATORS.get(i-1).replaceNext(LAT_VALIDATORS.get(i).getNext());
                    LAT_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case LONGITUDE: 
                if(LNG_VALIDATORS.get(i) != null){
                    LNG_VALIDATORS.get(i-1).replaceNext(LNG_VALIDATORS.get(i).getNext());
                    LNG_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            default:
                throw new ValidatorNotListedException();                    
        }            
    }     
    
    @Override
    public Pair<Boolean,ResponseEntity> validate() {  
        
        Pair<Boolean,ResponseEntity> currentTest;
        
        currentTest = ROOM_NAME_VALIDATORS.get(0).validate(room_name);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        currentTest = MEMBER_NAME_VALIDATORS.get(0).validate(member_name);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
        currentTest = LAT_VALIDATORS.get(0).validate(lat);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
        currentTest = LNG_VALIDATORS.get(0).validate(lng);
            return currentTest;   
            
         
    }
    
    private static void registerStringValidator(IStringValidator strVal , ValidationScopes scope) throws InappropriateValidatorException, ValidatorNotListedException{
            switch(scope)
        {
            case ROOM_NAME:
                ROOM_NAME_VALIDATORS.add(strVal);
                ROOM_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
               
            case USER_NAME:
                ROOM_NAME_VALIDATORS.add(strVal);
                ROOM_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();
        }
    }
    
        private static void registerNumberValidator(INumberValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException{ 
             switch(scope)
        {
            case LATITUDE:
                LAT_VALIDATORS.add(strVal);
                LAT_VALIDATORS.get(0).setNext(strVal);
                break;
               
            case LONGITUDE:
                LNG_VALIDATORS.add(strVal);
                LNG_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();
        }
    }
    
    /* Start of DTO */
    
    private String room_name;
    private String member_name;
    private Long lat;
    private Long lng;

    @Override
    public String getDTOName() {
        return "MessageHistoryRequestDTO";
    }
    
    public MessageHistoryRequestDTO() {
    }

    public String getRoom_name() {
        return room_name;
    }

    public Long getLat() {
        return lat;
    }

    public Long getLng() {
        return lng;
    }    

    public String getMember_name() {
        return member_name;
    }
}
