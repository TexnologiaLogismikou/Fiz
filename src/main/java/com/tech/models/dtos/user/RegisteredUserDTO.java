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
import java.util.Date;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class RegisteredUserDTO extends BaseDTO 
{
    private static final List<IStringValidator> USER_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> PASSWORD_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> EMAIL_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
//    private static final List<IStringValidator> BIRTHDAY_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    
    
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
        EMAIL_VALIDATORS.clear();
//        BIRTHDAY_VALIDATORS.clear();
        
        USER_NAME_VALIDATORS.add(new EmptyStringValidator());
        PASSWORD_VALIDATORS.add(new EmptyStringValidator());
        EMAIL_VALIDATORS.add(new EmptyStringValidator());
//        BIRTHDAY_VALIDATORS.add(new EmptyStringValidator());       
    }
    
    public static List<String> getValidatorList(ValidationScopes scope) 
            throws ValidatorNotListedException
    {
        List<String> list = new ArrayList<>();
        int i = 0;
        switch(scope)
        {
            case USER_NAME:
                for(ICustomValidator vLookUp:USER_NAME_VALIDATORS)
                {
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case PASSWORD:
                for(ICustomValidator vLookUp:PASSWORD_VALIDATORS)
                {
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case EMAIL:
                for(ICustomValidator vLookUp:EMAIL_VALIDATORS)
                {
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
//            case BIRTHDAY: 
//                for(ICustomValidator vLookUp:BIRTHDAY_VALIDATORS)
//                {
//                    if(vLookUp.getName().equals("Empty")) { continue; }
//                    i++;
//                    list.add(i + ": " + vLookUp.getName());
//                }
//                return list;
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
                if(USER_NAME_VALIDATORS.size() >= i + 1)
                {
                    USER_NAME_VALIDATORS.get(i-1).replaceNext(USER_NAME_VALIDATORS.get(i).getNext());
                    USER_NAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case PASSWORD:
                if(PASSWORD_VALIDATORS.size() >= i + 1)
                {
                    PASSWORD_VALIDATORS.get(i-1).replaceNext(PASSWORD_VALIDATORS.get(i).getNext());
                    PASSWORD_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case EMAIL:
                if(EMAIL_VALIDATORS.size() >= i + 1)
                {
                    EMAIL_VALIDATORS.get(i-1).replaceNext(EMAIL_VALIDATORS.get(i).getNext());
                    EMAIL_VALIDATORS.remove(i);
                    return true;
                }
                return false;
//            case BIRTHDAY: 
//                if(BIRTHDAY_VALIDATORS.get(i) != null)
//                {
//                    BIRTHDAY_VALIDATORS.get(i-1).replaceNext(BIRTHDAY_VALIDATORS.get(i).getNext());
//                    BIRTHDAY_VALIDATORS.remove(i);
//                    return true;
//                }
//                return false;
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
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
        currentTest = EMAIL_VALIDATORS.get(0).validate(email);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
        currentTest = USER_NAME_VALIDATORS.get(0).validate(last_name);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
        currentTest = USER_NAME_VALIDATORS.get(0).validate(firstname);
//        if(!currentTest.getLeft())
//        {
//            return currentTest;
//        }
        
//        currentTest = BIRTHDAY_VALIDATORS.get(0).validate(birthday);
        return currentTest;
    }
    
    private static void registerStringValidator(IStringValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException
    {  
        switch(scope)
        {
            case USER_NAME:
                USER_NAME_VALIDATORS.add(strVal);
                USER_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
            case PASSWORD:
                PASSWORD_VALIDATORS.add(strVal);
                PASSWORD_VALIDATORS.get(0).setNext(strVal);
                break;
            case EMAIL:
                EMAIL_VALIDATORS.add(strVal);
                EMAIL_VALIDATORS.get(0).setNext(strVal);
                break;
//            case BIRTHDAY:   
//                BIRTHDAY_VALIDATORS.add(strVal);             
//                BIRTHDAY_VALIDATORS.get(0).setNext(strVal);
//                break;
            default: 
                throw new ValidatorNotListedException();                    
        }        
                 
    }
    
    /* Start of DTO */
    
    private String username;
    private String password;
    private String email;
    private String last_name;
    private String firstname;
    private Date birthday;
      
    @Override
    public String getDTOName() {
        return "RegisteredUserDTO";
    }

    public RegisteredUserDTO() {
    }
    
    public String getEmail(){
        return email;
    }

    public String getLast_name() {
        return last_name;
    }

    public Date getBirthday() {
        return birthday;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }

    public String getFirstname() {
        return firstname;
    }
}
