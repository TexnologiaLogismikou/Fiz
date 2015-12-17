/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyNumberValidator;
import com.tech.configurations.tools.customvalidators.elements.EmptyStringValidator;
import com.tech.models.dtos.superclass.BaseDTO;
import java.util.ArrayList;
import java.util.List;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import com.tech.exceptions.customexceptions.NoValidatorsAssignedException;
import java.util.Arrays;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomUpdateDTO extends BaseDTO{
    private static final List<IStringValidator> ROOM_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> ROOM_ACCESS_METHOD_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> ROOM_PRIVILEGE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> PASSWORD_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<INumberValidator> RANGE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyNumberValidator()));
    
    public static void cleanValidator(){
        ROOM_ACCESS_METHOD_VALIDATORS.clear();
        ROOM_NAME_VALIDATORS.clear();
        ROOM_PRIVILEGE_VALIDATORS.clear();
        PASSWORD_VALIDATORS.clear();
        RANGE_VALIDATORS.clear();
        
        ROOM_ACCESS_METHOD_VALIDATORS.add(new EmptyStringValidator());
        ROOM_NAME_VALIDATORS.add(new EmptyStringValidator());
        ROOM_PRIVILEGE_VALIDATORS.add(new EmptyStringValidator());
        PASSWORD_VALIDATORS.add(new EmptyStringValidator());
        RANGE_VALIDATORS.add(new EmptyNumberValidator());        
    }
    
    private static void registerStringValidator(IStringValidator strVal, ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException{
        
        switch(scope){
            case ROOM_NAME:
                ROOM_NAME_VALIDATORS.add(strVal);
                ROOM_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
            case ROOM_ACCESS_METHOD:
                ROOM_ACCESS_METHOD_VALIDATORS.add(strVal);
                ROOM_ACCESS_METHOD_VALIDATORS.get(0).setNext(strVal);
                break;
            case ROOM_PRIVILEGE:
                ROOM_PRIVILEGE_VALIDATORS.add(strVal);
                ROOM_PRIVILEGE_VALIDATORS.get(0).setNext(strVal);
                break;
            case PASSWORD:   
                PASSWORD_VALIDATORS.add(strVal);             
                PASSWORD_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();                    
        }        
    }
    
    private static void registerNumberValidator(INumberValidator numVal, ValidationScopes scope) 
            throws ValidatorNotListedException, InappropriateValidatorException{
        
        switch(scope){
            case RANGE:
                RANGE_VALIDATORS.add(numVal);    
                RANGE_VALIDATORS.get(0).setNext(numVal);
                break;
            default:
                throw new ValidatorNotListedException();        
        }
    }
    
    public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) throws InappropriateValidatorException, ValidatorNotListedException{

        if(newValidator instanceof IStringValidator){
            registerStringValidator((IStringValidator)newValidator, scope);
        } 
        else if (newValidator instanceof INumberValidator){
            registerNumberValidator((INumberValidator)newValidator, scope);
        } else {
            throw new InappropriateValidatorException();
        }
    }
    
    public static List<String> getValidatorList(ValidationScopes scope) throws ValidatorNotListedException{
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
            case PASSWORD:
                for(ICustomValidator vLookUp:PASSWORD_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case ROOM_PRIVILEGE:
                for(ICustomValidator vLookUp:ROOM_PRIVILEGE_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case ROOM_ACCESS_METHOD: 
                for(ICustomValidator vLookUp:ROOM_ACCESS_METHOD_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case RANGE:
                for(ICustomValidator vLookUp:RANGE_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            default:
                throw new ValidatorNotListedException();                    
        }
    }
    
    public static boolean removeValidator(ValidationScopes scope, int i) throws ValidatorNotListedException, InappropriateValidatorException{
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
            case PASSWORD:
                if(PASSWORD_VALIDATORS.get(i) != null){
                    PASSWORD_VALIDATORS.get(i-1).replaceNext(PASSWORD_VALIDATORS.get(i).getNext());
                    PASSWORD_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case ROOM_PRIVILEGE:
                if(ROOM_PRIVILEGE_VALIDATORS.get(i) != null){
                    ROOM_PRIVILEGE_VALIDATORS.get(i-1).replaceNext(ROOM_PRIVILEGE_VALIDATORS.get(i).getNext());
                    ROOM_PRIVILEGE_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case ROOM_ACCESS_METHOD: 
                if(ROOM_ACCESS_METHOD_VALIDATORS.get(i) != null){
                    ROOM_ACCESS_METHOD_VALIDATORS.get(i-1).replaceNext(ROOM_ACCESS_METHOD_VALIDATORS.get(i).getNext());
                    ROOM_ACCESS_METHOD_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case RANGE:
                if(RANGE_VALIDATORS.get(i) != null){
                    RANGE_VALIDATORS.get(i-1).replaceNext(RANGE_VALIDATORS.get(i).getNext());
                    RANGE_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            default:
                throw new ValidatorNotListedException();                    
        }
    }
    
    @Override
    public Pair<Boolean,ResponseEntity> validate(){
        Pair<Boolean,ResponseEntity> currentTest = RANGE_VALIDATORS.get(0).validate((long)max_range);
        if(!currentTest.getLeft()){
            return currentTest;
        }
        
        currentTest = ROOM_NAME_VALIDATORS.get(0).validate(room_name);
        if(!currentTest.getLeft()){
            return currentTest;
        }
        
        currentTest = ROOM_NAME_VALIDATORS.get(0).validate(new_room_name);
        if(!currentTest.getLeft()){
            return currentTest;
        }
        
        currentTest = ROOM_PRIVILEGE_VALIDATORS.get(0).validate(room_privilege);
        if(!currentTest.getLeft()){
            return currentTest;
        }
        
        currentTest = ROOM_ACCESS_METHOD_VALIDATORS.get(0).validate(access_method);
        if(!currentTest.getLeft()){
            return currentTest;
        }
        
        currentTest = PASSWORD_VALIDATORS.get(0).validate(password);
        return currentTest;
    }
    
    /* Start of DTO */
    
    private String room_name;
    private String new_room_name;
    private String room_privilege;
    private String access_method;
    private boolean passwordProtection;
    private String password;
    private Integer max_range;

    @Override
    public String getDTOName() {
        return "ChatroomUpdateDTO";
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getNew_room_name() {
        return new_room_name;
    }

    public String getRoom_privilege() {
        return room_privilege;
    }

    public String getAccess_method() {
        return access_method;
    }
    
    public String getPassword() {
        return password;
    }    

    public Integer getMax_range() {
        return max_range;
    }

    public boolean isPasswordProtection() {
        return passwordProtection;
    }
}
