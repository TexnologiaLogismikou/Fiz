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
import com.tech.models.dtos.chatroom.ChatroomCreationDTO;
import com.tech.models.dtos.superclass.BaseDTO;
import org.springframework.http.HttpStatus;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomCreationDTOValidator extends BaseValidator implements CustomValidator {
    
    public ChatroomCreationDTOValidator() {
        super("ChatroomCreationDTO");
    }

    @Override
    public boolean validate(BaseDTO DTO) {
        if (!(DTO instanceof ChatroomCreationDTO)) {
            setResponseEntity(Responses.ERROR,HttpStatus.BAD_REQUEST);
            return false;
        }
        ChatroomCreationDTO CCDTO = (ChatroomCreationDTO)DTO;
        
        return validateCCDTO(CCDTO);
    }   
    
    private boolean validateCCDTO(ChatroomCreationDTO CCDTO){
        if(!Validator.accessMethodValidator(CCDTO.getAccess_method())){
            setResponseEntity(Responses.ACCESS_METHOD_NOT_FOUND,HttpStatus.NOT_ACCEPTABLE);
            return false;
        }
        if(!Validator.passwordValidator(CCDTO.getPassword())){
            setResponseEntity(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;
        }
        if(!Validator.nameValidation(CCDTO.getRoom_name())){
            setResponseEntity(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;
        }
        if(!Validator.idValidator(CCDTO.getUserid())){
            setResponseEntity(Responses.ID_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;
        }
        if(!Validator.roomPrivilegeValidator(CCDTO.getRoom_privilege())){
            setResponseEntity(Responses.ID_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
            return false;
        }
        setResponseEntity(Responses.SUCCESS, HttpStatus.OK);
        return true;
    }  
}
