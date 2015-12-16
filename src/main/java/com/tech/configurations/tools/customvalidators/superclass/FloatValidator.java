/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.superclass;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IFloatValidator;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public abstract class FloatValidator  extends BaseValidator implements ICustomValidator{
    protected IFloatValidator next;

    public FloatValidator(ResponseEntity RESPONSE_ERROR, String name) {
        super(RESPONSE_ERROR,name);
    }
    
    @Override
    public void setNext (ICustomValidator next) throws InappropriateValidatorException{
        if(!(next instanceof IFloatValidator)) {     
            throw new InappropriateValidatorException();
        } 
        if(this.next != null){
            this.next.setNext(next);
        } else {
            this.next = (IFloatValidator)next;         
        }      
    }   
    
    @Override
    public ICustomValidator getNext(){
        return next;
    }
    
    @Override
    public void replaceNext(ICustomValidator next) throws InappropriateValidatorException{
        if(!(next instanceof IFloatValidator)){
            throw new InappropriateValidatorException();
        }
        this.next = (IFloatValidator)next;
    }
    
}
