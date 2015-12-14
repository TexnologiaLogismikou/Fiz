/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.superclass;

import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public abstract class NumberValidator extends BaseValidator implements ICustomValidator{
    protected INumberValidator next;

    public NumberValidator(ResponseEntity RESPONSE_ERROR) {
        super(RESPONSE_ERROR);
    }
    
    @Override
    public void setNext (ICustomValidator next) throws InappropriateValidatorException{
        if(!(next instanceof INumberValidator)) {     
            throw new InappropriateValidatorException();
        } 
        this.next = (INumberValidator)next;         
    }   
}
