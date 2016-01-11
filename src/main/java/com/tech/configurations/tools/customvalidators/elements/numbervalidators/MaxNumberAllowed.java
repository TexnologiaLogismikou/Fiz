/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.numbervalidators;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import com.tech.configurations.tools.customvalidators.superclass.NumberValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class MaxNumberAllowed extends NumberValidator implements INumberValidator{
    private final int L;
    public MaxNumberAllowed(int L) {
        super( new ResponseEntity<>(Responses.ID_INAPPROPRIATE_FORMAT.getData(),HttpStatus.UNPROCESSABLE_ENTITY), "MaxNumberAllowed");
        this.L = L;
    }    
    
    @Override
    public Pair<Boolean, ResponseEntity> validate(Long n) {
        if(n > L){
            return Pair.of(Boolean.FALSE, getErrorResponse());
        }
        if (next != null){
            return next.validate(n);
        } else {
            return Pair.of(Boolean.TRUE, getSuccessResponse());
        }
    } 
}
