/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.floatvalidator;

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
public class LongitudeValidator extends FloatValidator implements ICustomValidator,IFloatValidator{
    public LongitudeValidator() {
        super( new ResponseEntity<>(Responses.BAD_COORDINATES,HttpStatus.UNPROCESSABLE_ENTITY), "LongitudeValidator");
    }    
    
    @Override
    public Pair<Boolean, ResponseEntity> validate(float n) {
        if(-180 > n || n > 180){
            return Pair.of(Boolean.FALSE, getErrorResponse());
        }
        if (next != null){
            return next.validate(n);
        } else {
            return Pair.of(Boolean.TRUE, getSuccessResponse());
        }
    } 
    
}
