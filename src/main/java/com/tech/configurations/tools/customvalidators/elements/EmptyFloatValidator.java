/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IFloatValidator;
import com.tech.configurations.tools.customvalidators.superclass.FloatValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class EmptyFloatValidator extends FloatValidator implements IFloatValidator{
    public EmptyFloatValidator() {
        super( new ResponseEntity<>(Responses.BAD_COORDINATES.getData(),HttpStatus.UNPROCESSABLE_ENTITY),"Empty");
    }    
    
    @Override
    public Pair<Boolean, ResponseEntity> validate(float n) {
        if (next != null){
            return next.validate(n);
        } else {
            return Pair.of(Boolean.TRUE, getSuccessResponse());
        }
    }  
    
}
