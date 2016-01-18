/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyFloatValidator;
import com.tech.configurations.tools.customvalidators.elements.EmptyNumberValidator;
import com.tech.configurations.tools.customvalidators.elements.EmptyStringValidator;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IFloatValidator;
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
 * @author Aenaos
 */
public class ChatroomCreationDTO extends BaseDTO {


    private static final List<IStringValidator> USERNAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> ROOM_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> ACCESS_METHOD_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> PRIVILEGE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> PASSWORD_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<INumberValidator> RANGE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyNumberValidator()));
    private static final List<IFloatValidator> LAT_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyFloatValidator()));
    private static final List<IFloatValidator> LNG_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyFloatValidator()));


    public static void registerValidator(ICustomValidator newValidator, ValidationScopes scope)
            throws InappropriateValidatorException, ValidatorNotListedException {

        if (newValidator instanceof IStringValidator) {
            registerStringValidator((IStringValidator) newValidator, scope);
        } else if (newValidator instanceof INumberValidator) {
            registerNumberValidator((INumberValidator) newValidator, scope);
        } else if (newValidator instanceof IFloatValidator) {
            registerFloatValidator((IFloatValidator) newValidator, scope);
        } else {
            throw new InappropriateValidatorException();
        }
    }

    public static void cleanValidator() {

        USERNAME_VALIDATORS.clear();
        ROOM_NAME_VALIDATORS.clear();
        ACCESS_METHOD_VALIDATORS.clear();
        PRIVILEGE_VALIDATORS.clear();
        PASSWORD_VALIDATORS.clear();
        RANGE_VALIDATORS.clear();
        LAT_VALIDATORS.clear();
        LNG_VALIDATORS.clear();

        USERNAME_VALIDATORS.add(new EmptyStringValidator());
        ROOM_NAME_VALIDATORS.add(new EmptyStringValidator());
        ACCESS_METHOD_VALIDATORS.add(new EmptyStringValidator());
        PRIVILEGE_VALIDATORS.add(new EmptyStringValidator());
        PASSWORD_VALIDATORS.add(new EmptyStringValidator());
        RANGE_VALIDATORS.add(new EmptyNumberValidator());
        LAT_VALIDATORS.add(new EmptyFloatValidator());
        LNG_VALIDATORS.add(new EmptyFloatValidator());
    }

    public static List<String> getValidatorList(ValidationScopes scope) throws ValidatorNotListedException {

        List<String> list = new ArrayList<>();
        int i = 0;
        switch (scope) {
            case USER_NAME:
                for (ICustomValidator vLookUp : USERNAME_VALIDATORS) {
                    if (vLookUp.getName().equals("Empty")) {
                        continue;
                    }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case ROOM_NAME:
                for (ICustomValidator vLookUp : ROOM_NAME_VALIDATORS) {
                    if (vLookUp.getName().equals("Empty")) {
                        continue;
                    }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case ROOM_PRIVILEGE:
                for (ICustomValidator vLookUp : PRIVILEGE_VALIDATORS) {
                    if (vLookUp.getName().equals("Empty")) {
                        continue;
                    }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case ROOM_ACCESS_METHOD:
                for (ICustomValidator vLookUp : ACCESS_METHOD_VALIDATORS) {
                    if (vLookUp.getName().equals("Empty")) {
                        continue;
                    }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case PASSWORD:
                for (ICustomValidator vLookUp : PASSWORD_VALIDATORS) {
                    if (vLookUp.getName().equals("Empty")) {
                        continue;
                    }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }

                return list;
            case RANGE:
                for (ICustomValidator vLookUp : RANGE_VALIDATORS) {
                    if (vLookUp.getName().equals("Empty")) {
                        continue;
                    }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case LATITUDE:
                for (ICustomValidator vLookUp : LAT_VALIDATORS) {
                    if (vLookUp.getName().equals("Empty")) {
                        continue;
                    }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case LONGITUDE:
                for (ICustomValidator vLookUp : LNG_VALIDATORS) {
                    if (vLookUp.getName().equals("Empty")) {
                        continue;
                    }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            default:
                throw new ValidatorNotListedException();
        }
    }

    public static boolean removeValidator(ValidationScopes scope, int i) throws ValidatorNotListedException, InappropriateValidatorException {

        if (i == 0) {
            return false;
        }
        switch (scope) {
            case USER_NAME:
                if (USERNAME_VALIDATORS.size() >= i + 1 ) {
                    USERNAME_VALIDATORS.get(i - 1).replaceNext(USERNAME_VALIDATORS.get(i).getNext());
                    USERNAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case ROOM_NAME:
                if (ROOM_NAME_VALIDATORS.size() >= i + 1 ) {
                    ROOM_NAME_VALIDATORS.get(i - 1).replaceNext(ROOM_NAME_VALIDATORS.get(i).getNext());
                    ROOM_NAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case PASSWORD:
                if (PASSWORD_VALIDATORS.size() >= i + 1 ) {
                    PASSWORD_VALIDATORS.get(i - 1).replaceNext(PASSWORD_VALIDATORS.get(i).getNext());
                    PASSWORD_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case ROOM_PRIVILEGE:
                if (PRIVILEGE_VALIDATORS.size() >= i + 1 ) {
                    PRIVILEGE_VALIDATORS.get(i - 1).replaceNext(PRIVILEGE_VALIDATORS.get(i).getNext());
                    PRIVILEGE_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case ROOM_ACCESS_METHOD:
                if (ACCESS_METHOD_VALIDATORS.size() >= i + 1 ) {
                    ACCESS_METHOD_VALIDATORS.get(i - 1).replaceNext(ACCESS_METHOD_VALIDATORS.get(i).getNext());
                    ACCESS_METHOD_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case RANGE:
                if (RANGE_VALIDATORS.size() >= i + 1 ) {
                    RANGE_VALIDATORS.get(i - 1).replaceNext(RANGE_VALIDATORS.get(i).getNext());
                    RANGE_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case LATITUDE:
                if (LAT_VALIDATORS.size() >= i + 1 ) {
                    LAT_VALIDATORS.get(i - 1).replaceNext(LAT_VALIDATORS.get(i).getNext());
                    LAT_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case LONGITUDE:
                if (LNG_VALIDATORS.size() >= i + 1 ) {
                    LNG_VALIDATORS.get(i - 1).replaceNext(LNG_VALIDATORS.get(i).getNext());
                    LNG_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            default:
                throw new ValidatorNotListedException();
        }
    }

    @Override
    public Pair<Boolean, ResponseEntity> validate() {

        Pair<Boolean,ResponseEntity> currentTest = RANGE_VALIDATORS.get(0).validate((long)room_max_range);
        if(!currentTest.getLeft()){
            return currentTest;
        }

        currentTest = USERNAME_VALIDATORS.get(0).validate(username);
        if(!currentTest.getLeft()){
            return currentTest;
        }

        currentTest = ROOM_NAME_VALIDATORS.get(0).validate(room_name);
        if(!currentTest.getLeft()){
            return currentTest;
        }

        currentTest = PRIVILEGE_VALIDATORS.get(0).validate(room_privilege);
        if(!currentTest.getLeft()){
            return currentTest;
        }

        currentTest = ACCESS_METHOD_VALIDATORS.get(0).validate(access_method);
        if(!currentTest.getLeft()){
            return currentTest;
        }

        currentTest = LAT_VALIDATORS.get(0).validate(room_lat);
        if(!currentTest.getLeft()){
            return currentTest;
        }

        currentTest = LNG_VALIDATORS.get(0).validate(room_lng);
        if(!currentTest.getLeft()){
            return currentTest;
        }

        currentTest = PASSWORD_VALIDATORS.get(0).validate(password);
        return currentTest;
    }

    private static void registerStringValidator(IStringValidator newValidator, ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException {

        switch (scope) {
            case USER_NAME:
                USERNAME_VALIDATORS.add(newValidator);
                USERNAME_VALIDATORS.get(0).setNext(newValidator);
                break;
            case ROOM_NAME:
                ROOM_NAME_VALIDATORS.add(newValidator);
                ROOM_NAME_VALIDATORS.get(0).setNext(newValidator);
                break;
            case ROOM_ACCESS_METHOD:
                ACCESS_METHOD_VALIDATORS.add(newValidator);
                ACCESS_METHOD_VALIDATORS.get(0).setNext(newValidator);
                break;
            case ROOM_PRIVILEGE:
                PRIVILEGE_VALIDATORS.add(newValidator);
                PRIVILEGE_VALIDATORS.get(0).setNext(newValidator);
                break;
            case PASSWORD:
                PASSWORD_VALIDATORS.add(newValidator);
                PASSWORD_VALIDATORS.get(0).setNext(newValidator);
                break;
            default:
                throw new ValidatorNotListedException();
        }
    }


    private static void registerFloatValidator(IFloatValidator newValidator, ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException {

        switch (scope) {
            case LATITUDE:
                LAT_VALIDATORS.add(newValidator);
                LAT_VALIDATORS.get(0).setNext(newValidator);
                break;
            case LONGITUDE:
                LNG_VALIDATORS.add(newValidator);
                LNG_VALIDATORS.get(0).setNext(newValidator);
                break;
            default:
                throw new ValidatorNotListedException();
        }
    }

    private static void registerNumberValidator(INumberValidator newValidator, ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException {

        switch (scope) {
            case RANGE:
                RANGE_VALIDATORS.add(newValidator);
                RANGE_VALIDATORS.get(0).setNext(newValidator);
                break;
            default:
                throw new ValidatorNotListedException();
        }
    }
    
    /* Start of DTO */

    private String username;
    private String user_id;
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
    
    public boolean isHasPassword() {
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

    public String getUser_id() {
        return user_id;
    }
}
