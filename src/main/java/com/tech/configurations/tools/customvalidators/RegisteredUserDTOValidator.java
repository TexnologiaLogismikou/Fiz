/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators;

import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.Validator;
import com.tech.configurations.tools.customvalidators.interfaces.CustomValidator;
import com.tech.configurations.tools.customvalidators.superclass.BaseValidator;
import com.tech.models.dtos.RegisteredUserDTO;
import com.tech.models.dtos.superclass.BaseDTO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;

/**
 *
 * @author KuroiTenshi
 */
public class RegisteredUserDTOValidator extends BaseValidator implements CustomValidator {
    
    public RegisteredUserDTOValidator(){
        super("RegisteredUserDTO");
    }
    
    @Override
    public boolean validate(BaseDTO DTO) {
        if (!(DTO instanceof RegisteredUserDTO)) {
            setResponseEntity(Responses.ERROR,HttpStatus.BAD_REQUEST);
            return false;
        }
        RegisteredUserDTO RUDTO = (RegisteredUserDTO)DTO;
        
        return validateRUDTO(RUDTO);
    }
    
    private boolean validateRUDTO(RegisteredUserDTO RUDTO){
        if (!Validator.nameValidation(RUDTO.getUsername())) {
            setResponseEntity(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;
        }
        if (!Validator.nameValidation(RUDTO.getFirstName())){
            setResponseEntity(Responses.FIRST_NAME_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;            
        }
        if (!Validator.nameValidation(RUDTO.getLast_name())){
            setResponseEntity(Responses.LAST_NAME_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;            
        }
        if(!Validator.passwordValidator(RUDTO.getPassword())){
            setResponseEntity(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;
        }
        if(!validateEmail(RUDTO.getEmail())){
            setResponseEntity(Responses.EMAIL_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;            
        }
        setResponseEntity(Responses.SUCCESS, HttpStatus.OK);
        return true;
    }
    
    private boolean validateEmail(String email){
        if(email.isEmpty()){
            return false;
        }
        
        if(email.trim().length() < email.length()){
            return false;
        }
        
        if(!email.contains("@")){
            return false;
        } 
        
        Pattern p = Pattern.compile("^[A-Za-z]");
        Matcher m = p.matcher(email);
        
        if (!m.find()){
            return false;
        }
        
        return true;
    }
}
