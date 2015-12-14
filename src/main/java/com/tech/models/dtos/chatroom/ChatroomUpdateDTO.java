/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.tech.configurations.tools.ValidationScopes;
import com.tech.models.dtos.superclass.BaseDTO;
import java.util.ArrayList;
import java.util.List;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import com.tech.configurations.tools.customvalidators.superclass.StringValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomUpdateDTO extends BaseDTO{
    private static final List<IStringValidator> STRING_VALIDATORS = new ArrayList<>();
    private static final List<IStringValidator> METHOD_VALIDATORS = new ArrayList<>();
    private static final List<INumberValidator> ID_VALIDATORS = new ArrayList<>();
    private static final List<INumberValidator> RANGE_VALIDATORS = new ArrayList<>();
    
    
    public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) throws InappropriateValidatorException, ValidatorNotListedException{
        switch(scope){
            case STRING:
                if(newValidator instanceof IStringValidator) {
                    STRING_VALIDATORS.add((IStringValidator)newValidator);
                    break;                    
                }
                throw new InappropriateValidatorException();
            case NUMBER:
                if(newValidator instanceof INumberValidator){
                    ID_VALIDATORS.add((INumberValidator)newValidator); 
                    break;                   
                }
                throw new InappropriateValidatorException();
            case RANGE:
                if(newValidator instanceof INumberValidator){
                    RANGE_VALIDATORS.add((INumberValidator)newValidator); 
                    break;                   
                }
                throw new InappropriateValidatorException();
            case METHOD:
                if(newValidator instanceof IStringValidator){
                    METHOD_VALIDATORS.add((IStringValidator)newValidator); 
                    break;                   
                }
                throw new InappropriateValidatorException();                
            default :
                throw new ValidatorNotListedException();
        }
    }
    
    
    public boolean validate(){
        return false;
    }
    
    private String room_name;//WONT CHANGE
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

    public boolean isPasswordProtected() {
        return passwordProtection;
    }

    public String getPassword() {
        return password;
    }    

    public Integer getMax_range() {
        return max_range;
    }
}
