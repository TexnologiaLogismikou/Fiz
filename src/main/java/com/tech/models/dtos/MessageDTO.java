package com.tech.models.dtos;

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
import com.tech.exceptions.customexceptions.NoValidatorsAssignedException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.superclass.BaseDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;


public class MessageDTO extends BaseDTO
{
    private static final List<IStringValidator> MESSAGE_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> USERNAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IStringValidator> CHATROOM_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    private static final List<IFloatValidator> LAT_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyFloatValidator()));
    private static final List<IFloatValidator> LNG_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyFloatValidator()));
    private static final List<INumberValidator> TTL_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyNumberValidator()));

    public static void cleanValidator()
    {
        MESSAGE_VALIDATORS.clear();
        USERNAME_VALIDATORS.clear();
        CHATROOM_NAME_VALIDATORS.clear();
        LAT_VALIDATORS.clear();
        LNG_VALIDATORS.clear();
        TTL_VALIDATORS.clear();

        MESSAGE_VALIDATORS.add(new EmptyStringValidator());
        USERNAME_VALIDATORS.add(new EmptyStringValidator());
        CHATROOM_NAME_VALIDATORS.add(new EmptyStringValidator());
        LAT_VALIDATORS.add(new EmptyFloatValidator());
        LNG_VALIDATORS.add(new EmptyFloatValidator());
        TTL_VALIDATORS.add(new EmptyNumberValidator());     
    }
    
     private static void registerStringValidator(IStringValidator strVal , ValidationScopes scope) throws ValidatorNotListedException, InappropriateValidatorException
     {  
        switch(scope)
        {
            case USER_NAME:
                USERNAME_VALIDATORS.add(strVal);
                USERNAME_VALIDATORS.get(0).setNext(strVal);
                break;
            case ROOM_NAME:
                CHATROOM_NAME_VALIDATORS.add(strVal);
                CHATROOM_NAME_VALIDATORS.get(0).setNext(strVal);
                break; 
            case STRING:   
                MESSAGE_VALIDATORS.add(strVal);             
                MESSAGE_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();                    
        }        
     }
     
    private static void registerFloatValidator(IFloatValidator strVal , ValidationScopes scope) throws ValidatorNotListedException, InappropriateValidatorException
    {   
        
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
            case TTL:
                LAT_VALIDATORS.add(strVal);    
                TTL_VALIDATORS.get(0).setNext(strVal);
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
       else if (newValidator instanceof IFloatValidator)
       {
            registerFloatValidator((IFloatValidator)newValidator, scope);
       } else
       {
            throw new InappropriateValidatorException();
       }
    }    
        
    public static List<String> getValidatorList(ValidationScopes scope) throws ValidatorNotListedException
    {
        List<String> list = new ArrayList<>();
        int i = 0;
        switch(scope)
        {
            case USER_NAME:
                for(ICustomValidator vLookUp:USERNAME_VALIDATORS)
                {
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case ROOM_NAME:
                for(ICustomValidator vLookUp:CHATROOM_NAME_VALIDATORS)
                {
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case LATITUDE:
                for(ICustomValidator vLookUp:LAT_VALIDATORS)
                {
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case LONGITUDE: 
                for(ICustomValidator vLookUp:LNG_VALIDATORS)
                {
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case TTL:
                for(ICustomValidator vLookUp:TTL_VALIDATORS)
                {
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            case STRING:
                for(ICustomValidator vLookUp:MESSAGE_VALIDATORS)
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
        if(i==0){
            return false;
        }
        switch(scope)
        {
            case ROOM_NAME:
                if(CHATROOM_NAME_VALIDATORS.get(i) != null)
                {
                    CHATROOM_NAME_VALIDATORS.get(i-1).replaceNext(CHATROOM_NAME_VALIDATORS.get(i).getNext());
                    CHATROOM_NAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case LATITUDE:
                if(LAT_VALIDATORS.get(i) != null)
                {
                    LAT_VALIDATORS.get(i-1).replaceNext(LAT_VALIDATORS.get(i).getNext());
                    LAT_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case LONGITUDE:
                if(LNG_VALIDATORS.get(i) != null)
                {
                    LNG_VALIDATORS.get(i-1).replaceNext(LNG_VALIDATORS.get(i).getNext());
                    LNG_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case STRING: 
                if(MESSAGE_VALIDATORS.get(i) != null)
                {
                    MESSAGE_VALIDATORS.get(i-1).replaceNext(MESSAGE_VALIDATORS.get(i).getNext());
                    MESSAGE_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            case TTL:
                if(TTL_VALIDATORS.get(i) != null)
                {
                    TTL_VALIDATORS.get(i-1).replaceNext(TTL_VALIDATORS.get(i).getNext());
                    TTL_VALIDATORS.remove(i);
                    return true;
                }
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
    public Pair<Boolean,ResponseEntity> validate()
    {  
        Pair<Boolean,ResponseEntity> currentTest;
        
        currentTest = CHATROOM_NAME_VALIDATORS.get(0).validate(chatroom_name);
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
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
       
        currentTest = USERNAME_VALIDATORS.get(0).validate(username);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
         
        currentTest = TTL_VALIDATORS.get(0).validate((long)ttl);
        if(!currentTest.getLeft())
        {
            return currentTest;
        }
        
        currentTest = MESSAGE_VALIDATORS.get(0).validate(message);
        return currentTest; 
    }
    
    /* Start of DTO */
    
    private String message;
    private String username;
    private String chatroom_name;
    private float lng;
    private float lat;
    private int ttl;
    
    @Override
    public String getDTOName() {
        return "MessageDTO";
    }

    public MessageDTO() {
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getChatroom_name() {
        return chatroom_name;
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }

    public int getTtl() {
        return ttl;
    }
}
