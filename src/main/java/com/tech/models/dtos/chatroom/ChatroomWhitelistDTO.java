/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyStringValidator;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IFloatValidator;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.NoValidatorsAssignedException;
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
public class ChatroomWhitelistDTO extends BaseDTO{
    private static final List<IStringValidator> ROOM_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> MEMBER_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> ROOM_MODE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    
    public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) 
            throws InappropriateValidatorException, ValidatorNotListedException{

        if(newValidator instanceof IStringValidator){
            registerStringValidator((IStringValidator)newValidator, scope);
        } else {
            throw new InappropriateValidatorException();
        }
    }    
    
    public static void cleanValidator(){
        ROOM_NAME_VALIDATORS.clear();
        MEMBER_NAME_VALIDATORS.clear();
        ROOM_MODE_VALIDATORS.clear();
        
        MEMBER_NAME_VALIDATORS.add(new EmptyStringValidator());
        ROOM_MODE_VALIDATORS.add(new EmptyStringValidator());
        ROOM_NAME_VALIDATORS.add(new EmptyStringValidator());
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
            case MODE:
                for(ICustomValidator vLookUp:ROOM_MODE_VALIDATORS){
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
            case MODE:
                if(ROOM_MODE_VALIDATORS.get(i) != null){
                    ROOM_MODE_VALIDATORS.get(i-1).replaceNext(ROOM_MODE_VALIDATORS.get(i).getNext());
                    ROOM_MODE_VALIDATORS.remove(i);
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
            default:
                throw new ValidatorNotListedException();                    
        }             
    }     
    
    @Override
    public Pair<Boolean,ResponseEntity> validate() 
            throws InappropriateValidatorException, NoValidatorsAssignedException{  
        
        Pair<Boolean,ResponseEntity> currentTest = MEMBER_NAME_VALIDATORS.get(0).validate(member_name);
        if(!currentTest.getLeft()){
            return currentTest;
        }
        
        currentTest = ROOM_MODE_VALIDATORS.get(0).validate(mode);
        if(!currentTest.getLeft()){
            return currentTest;
        }
        
        currentTest = ROOM_NAME_VALIDATORS.get(0).validate(room_name);
        return currentTest;     
    }
    
    private static void registerStringValidator(IStringValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException{  
        switch(scope){
            case ROOM_NAME:
                ROOM_NAME_VALIDATORS.add(strVal);
                ROOM_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
            case MODE:
                ROOM_MODE_VALIDATORS.add(strVal);
                ROOM_MODE_VALIDATORS.get(0).setNext(strVal);
                break;
            case USER_NAME:
                MEMBER_NAME_VALIDATORS.add(strVal);
                MEMBER_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();                    
        }                    
    }
    
    /* Start of DTO */
    
    private String room_name;
    private String member_name;
    private String mode;//ADD or DELETE
    
    @Override
    public String getDTOName() {
        return "ChatroomWhitelistDTO";
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getMember_name() {
        return member_name;
    }
    
    public String getMode(){
        return mode;
    }
}
