/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.superclass;

import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public abstract class BaseValidator implements ICustomValidator{    
    private final ResponseEntity RESPONSE_ERROR;
    private final ResponseEntity RESPONSE_DONE = new ResponseEntity<>(Responses.SUCCESS,HttpStatus.OK);
    
    protected BaseValidator(ResponseEntity RESPONSE_ERROR){
        this.RESPONSE_ERROR = RESPONSE_ERROR;
    }
    
    protected ResponseEntity getErrorResponse(){
        return RESPONSE_ERROR;
    }
    protected ResponseEntity getSuccessResponse(){
        return RESPONSE_DONE;
    }
    
}
