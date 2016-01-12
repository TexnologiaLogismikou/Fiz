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
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.superclass.BaseDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;

/**
 * @author KuroiTenshi
 */
public class ChatroomDeleteDTO extends BaseDTO {
    private static final List<IStringValidator> USERNAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> ROOM_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> ROOM_PASSWORD = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));

    public static void registerValidator(ICustomValidator newValidator, ValidationScopes scope) throws InappropriateValidatorException, ValidatorNotListedException {

        if (newValidator instanceof IStringValidator) {
            registerStringValidator((IStringValidator) newValidator, scope);
        } else {
            throw new InappropriateValidatorException();
        }
    }

    public static void cleanValidator() {

        USERNAME_VALIDATORS.clear();
        ROOM_NAME_VALIDATORS.clear();
        ROOM_PASSWORD.clear();

        USERNAME_VALIDATORS.add(new EmptyStringValidator());
        ROOM_NAME_VALIDATORS.add(new EmptyStringValidator());
        ROOM_PASSWORD.add(new EmptyStringValidator());
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
            case PASSWORD:
                for (ICustomValidator vLookUp : ROOM_PASSWORD) {
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
            return false; // I believe you had forgotten that - Arxa
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
                if (ROOM_PASSWORD.size() >= i + 1 ) {
                    ROOM_PASSWORD.get(i - 1).replaceNext(ROOM_PASSWORD.get(i).getNext());
                    ROOM_PASSWORD.remove(i);
                    return true;
                }
                return false;
            default:
                throw new ValidatorNotListedException();
        }
    }

    @Override
    public Pair<Boolean, ResponseEntity> validate() {


        Pair<Boolean, ResponseEntity> currentTest = USERNAME_VALIDATORS.get(0).validate(creator_name);
        if (!currentTest.getLeft()) {
            return currentTest;
        }

        currentTest = ROOM_NAME_VALIDATORS.get(0).validate(room_name);
        if (!currentTest.getLeft()) {
            return currentTest;
        }

        currentTest = ROOM_PASSWORD.get(0).validate(room_password);
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
            case PASSWORD:
                ROOM_PASSWORD.add(newValidator);
                ROOM_PASSWORD.get(0).setNext(newValidator);
                break;
            default:
                throw new ValidatorNotListedException();
        }
    }
    
    /* Start of DTO */

    private String creator_name;
    private String room_name;
    private String room_password;

    @Override
    public String getDTOName() {
        return "ChatroomDeleteDTO";
    }

    public String getCreator_name() {
        return creator_name;
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getRoom_password() {
        return room_password;
    }
}
