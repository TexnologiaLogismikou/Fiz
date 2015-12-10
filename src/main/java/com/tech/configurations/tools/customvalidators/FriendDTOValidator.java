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
import com.tech.models.dtos.FriendDTO;
import com.tech.models.dtos.superclass.BaseDTO;
import org.springframework.http.HttpStatus;

/**
 *
 * @author milena
 */
public class FriendDTOValidator extends BaseValidator implements CustomValidator
{
    public FriendDTOValidator()
    {
        super("FriendDTO");
    }

    @Override
    public boolean validate(BaseDTO DTO) 
    {
        if (!(DTO instanceof FriendDTO))
        {
            setResponseEntity(Responses.ERROR,HttpStatus.BAD_REQUEST);
            return false;
        }
        FriendDTO RUDTO = (FriendDTO)DTO;
        
        return validateFDTO(RUDTO);
    }
    
     private boolean validateFDTO(FriendDTO RUDTO)
     {
        if (!Validator.nameValidation(RUDTO.getUsername()))
        {
            setResponseEntity(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;
        }
        if (!Validator.nameValidation(RUDTO.getFriendname()))
        {
            setResponseEntity(Responses.FIRST_NAME_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;            
        }
        
        setResponseEntity(Responses.SUCCESS, HttpStatus.OK);
        return true;
     }
}
