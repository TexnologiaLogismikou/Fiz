/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.superclass;

import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public abstract class StringValidator extends  BaseValidator implements ICustomValidator,IStringValidator{
    protected IStringValidator next;

    public StringValidator(ResponseEntity RESPONSE_ERROR, String name) {
        super(RESPONSE_ERROR,name);
    }
    
    @Override
    public void setNext (ICustomValidator next) throws InappropriateValidatorException{
        if(!(next instanceof IStringValidator)) {     
            throw new InappropriateValidatorException();
        } 
        if(this.next != null){
            this.next.setNext(next);
        } else {
            this.next = (IStringValidator)next;         
        }
    }
     
    @Override
    public ICustomValidator getNext(){
        return next;
    }
    
    @Override
    public void replaceNext(ICustomValidator next) throws InappropriateValidatorException{
        if(!(next instanceof IStringValidator)){
            throw new InappropriateValidatorException();
        }
        this.next = (IStringValidator)next;
    }
}
