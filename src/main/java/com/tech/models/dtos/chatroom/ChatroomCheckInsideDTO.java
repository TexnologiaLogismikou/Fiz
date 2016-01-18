/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyFloatValidator;
import com.tech.configurations.tools.customvalidators.elements.EmptyStringValidator;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IFloatValidator;
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
public class ChatroomCheckInsideDTO extends BaseDTO
{ 
    private static final List<IFloatValidator> LONGITUDE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyFloatValidator()));
    private static final List<IFloatValidator> LATITUDE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyFloatValidator()));
    private static final List<IStringValidator> ROOM_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> USER_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    
    public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) 
            throws InappropriateValidatorException, ValidatorNotListedException
    {
        if(newValidator instanceof IFloatValidator)
        {
            registerFloatValidator((IFloatValidator)newValidator, scope);
        } 
        else if (newValidator instanceof IStringValidator)
        {
            registerStringValidator((IStringValidator)newValidator, scope);
        }
        else 
        {
            throw new InappropriateValidatorException();
        }
    }    
    
    public static void cleanValidator()
    {
        LONGITUDE_VALIDATORS.clear();
        LATITUDE_VALIDATORS.clear();
        ROOM_NAME_VALIDATORS.clear();
        USER_NAME_VALIDATORS.clear();
        
        LONGITUDE_VALIDATORS.add(new EmptyFloatValidator());
        LATITUDE_VALIDATORS.add(new EmptyFloatValidator());
        ROOM_NAME_VALIDATORS.add(new EmptyStringValidator());
        USER_NAME_VALIDATORS.add(new EmptyStringValidator());    
    }
    
    public static List<String> getValidatorList(ValidationScopes scope) 
            throws ValidatorNotListedException
    {
       List<String> list = new ArrayList<>();
        int i = 0;
        switch(scope)
        {
            case LONGITUDE:
                for(ICustomValidator vLookUp:LONGITUDE_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case LATITUDE:
                for(ICustomValidator vLookUp:LATITUDE_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case ROOM_NAME:
                for(ICustomValidator vLookUp:ROOM_NAME_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case USER_NAME:
                for(ICustomValidator vLookUp:USER_NAME_VALIDATORS){
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
            throws ValidatorNotListedException, InappropriateValidatorException
    {
        if(i==0)
        {
            return false;
        }
        switch(scope)
        {
            case LONGITUDE:
                if(LONGITUDE_VALIDATORS.size() >= i + 1)
                {
                    LONGITUDE_VALIDATORS.get(i-1).replaceNext(LONGITUDE_VALIDATORS.get(i).getNext());
                    LONGITUDE_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case LATITUDE:
                if(LATITUDE_VALIDATORS.size() >= i + 1)
                {
                    LATITUDE_VALIDATORS.get(i-1).replaceNext(LATITUDE_VALIDATORS.get(i).getNext());
                    LATITUDE_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case ROOM_NAME:
                if(ROOM_NAME_VALIDATORS.size() >= i + 1)
                {
                    ROOM_NAME_VALIDATORS.get(i-1).replaceNext(ROOM_NAME_VALIDATORS.get(i).getNext());
                    ROOM_NAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case USER_NAME:
                if(USER_NAME_VALIDATORS.size() >= i + 1)
                {
                    USER_NAME_VALIDATORS.get(i-1).replaceNext(USER_NAME_VALIDATORS.get(i).getNext());
                    USER_NAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            default:
                throw new ValidatorNotListedException();                    
        }         
    }     
    
    @Override
    public Pair<Boolean,ResponseEntity> validate() 
    {  
        Pair<Boolean,ResponseEntity> currentTest = LONGITUDE_VALIDATORS.get(0).validate(lng);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
        currentTest = LATITUDE_VALIDATORS.get(0).validate(lat);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
        currentTest = ROOM_NAME_VALIDATORS.get(0).validate(room_name);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
       
        currentTest = USER_NAME_VALIDATORS.get(0).validate(user_name);
        return currentTest;
    }
    
    private static void registerStringValidator(IStringValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException
    {  
       switch(scope)
       {
            case ROOM_NAME:
                ROOM_NAME_VALIDATORS.add(strVal);
                ROOM_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
            case USER_NAME:
                USER_NAME_VALIDATORS.add(strVal);
                USER_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();                    
        }                           
    }
    
    private static void registerFloatValidator(IFloatValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException
    {   
        switch(scope)
       {
            case LONGITUDE:
                LONGITUDE_VALIDATORS.add(strVal);
                LONGITUDE_VALIDATORS.get(0).setNext(strVal);
                break;
            case LATITUDE:
                LATITUDE_VALIDATORS.add(strVal);
                LATITUDE_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();                    
        }                           
    }
    
    /* Start of DTO */
    
    private float lng;
    private float lat;
    private String room_name;
    private String user_name;
    private String user_id;
    
    public ChatroomCheckInsideDTO(){        
    }
    
    @Override
    public String getDTOName() {
        return "ChatroomCheckInsideDTO";
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

    public String getUser_name() {
        return user_name;
    }    

    public String getUser_id() {
        return user_id;
    }
}
