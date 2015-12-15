/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyStringValidator;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
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
 * @author milena
 */
public class FriendDTO extends BaseDTO{
    
    private static final List<IStringValidator> USERNAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    
    public static void cleanValidator()
    {
        USERNAME_VALIDATORS.clear();
              
        USERNAME_VALIDATORS.add(new EmptyStringValidator());
    }
    
    private static void registerStringValidator(IStringValidator strVal , ValidationScopes scope)  throws ValidatorNotListedException, InappropriateValidatorException
    {  
         switch(scope)
         {
            case USER_NAME:
                USERNAME_VALIDATORS.add(strVal);
                USERNAME_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException(); 
         }
    }
    
     public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) throws InappropriateValidatorException, ValidatorNotListedException{

        if(newValidator instanceof IStringValidator)
        {
            registerStringValidator((IStringValidator)newValidator, scope);
        } 
        else
        {
            throw new InappropriateValidatorException();
        }
    }    
    
    public static List<String> getValidatorList(ValidationScopes scope) throws ValidatorNotListedException
    {
       List<String> list = new ArrayList<>();
        int i = 0;
        switch(scope){
            case USER_NAME:
                for(ICustomValidator vLookUp:USERNAME_VALIDATORS)
                {
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            default:
                throw new ValidatorNotListedException();                    
        }      
    }  
    public static boolean removeValidator(ValidationScopes scope, int i) throws ValidatorNotListedException, InappropriateValidatorException
    {   
        if(i==0)
        {
            return false;
        }
        switch(scope)
        {
            case USER_NAME:
                if(USERNAME_VALIDATORS.get(i) != null)
                {
                    USERNAME_VALIDATORS.get(i-1).replaceNext(USERNAME_VALIDATORS.get(i).getNext());
                    USERNAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;

            default:
                throw new ValidatorNotListedException();                    
        }
    }     

    @Override
    public Pair<Boolean,ResponseEntity> validate() throws InappropriateValidatorException, NoValidatorsAssignedException
    {
        Pair<Boolean,ResponseEntity> currentTest;
        
        currentTest = USERNAME_VALIDATORS.get(0).validate(username);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
        currentTest = USERNAME_VALIDATORS.get(0).validate(friendname);
            return currentTest;
    }
    
    /* Start of DTO */
    
    private String username;
    private String friendname;

    @Override
    public String getDTOName() {
        return "FriendDTO";
    }
    
    public String getUsername() 
    {
        return username;
    }

    public String getFriendname()
    {
        return friendname;
    }
    
}
