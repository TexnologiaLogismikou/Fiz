/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.user;

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
 *
 * @author KuroiTenshi
 */
public class LoginUserDTO extends BaseDTO 
{
    private static final List<IStringValidator> USER_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> PASSWORD_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    
    public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) 
            throws InappropriateValidatorException, ValidatorNotListedException
    {
        if(newValidator instanceof IStringValidator)
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
        USER_NAME_VALIDATORS.clear();
        PASSWORD_VALIDATORS.clear();
        
        USER_NAME_VALIDATORS.add(new EmptyStringValidator());
        PASSWORD_VALIDATORS.add(new EmptyStringValidator());       
    }
    
    public static List<String> getValidatorList(ValidationScopes scope) 
            throws ValidatorNotListedException
    {
        List<String> list = new ArrayList<>();
        int i = 0;
        switch(scope)
        {
            case USER_NAME:
                for(ICustomValidator vLookUp:USER_NAME_VALIDATORS){
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
            case USER_NAME:
                if(USER_NAME_VALIDATORS.get(i) != null)
                {
                    USER_NAME_VALIDATORS.get(i-1).replaceNext(USER_NAME_VALIDATORS.get(i).getNext());
                    USER_NAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case PASSWORD:
                if(PASSWORD_VALIDATORS.get(i) != null)
                {
                    PASSWORD_VALIDATORS.get(i-1).replaceNext(PASSWORD_VALIDATORS.get(i).getNext());
                    PASSWORD_VALIDATORS.remove(i);
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
        Pair<Boolean,ResponseEntity> currentTest = USER_NAME_VALIDATORS.get(0).validate(username);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        currentTest = PASSWORD_VALIDATORS.get(0).validate(password);
        return currentTest;
    }
    
    private static void registerStringValidator(IStringValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException
    {  
         switch(scope){
            case USER_NAME:
                USER_NAME_VALIDATORS.add(strVal);
                USER_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
            case PASSWORD:
                PASSWORD_VALIDATORS.add(strVal);
                PASSWORD_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();                    
        }                  
    }
    
    /* Start of DTO */
    
    private String username;
    private String password;

    @Override
    public String getDTOName() 
    {
        return "LoginUserDTO";
    }
    
    public String getUsername() 
    {
        return username;
    }

    public String getPassword() 
    {
        return password;
    }
    
}
