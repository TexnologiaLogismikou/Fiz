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
    
    public Pair<Boolean,ResponseEntity> validate() throws InappropriateValidatorException, NoValidatorsAssignedException{
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
    
    public boolean isPasswordProtection() {
        return passwordProtection;
    }

    public String getPassword() {
        return password;
    }    

    public Integer getMax_range() {
        return max_range;
    }
}
