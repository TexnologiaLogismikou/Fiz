/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.stringvalidators;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.superclass.StringValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;

/**
 *
 * @author KuroiTenshi
 */
public class NotEmptyValidatorS extends StringValidator implements IStringValidator{
    public NotEmptyValidatorS() {
        super(new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT.getData(), HttpStatus.NOT_ACCEPTABLE),"NotEmptyValidatorS");
    }

    @Override
    public Pair<Boolean, ResponseEntity> validate(String str) {
        if(str.isEmpty()){
            return Pair.of(Boolean.FALSE,getErrorResponse());
        }
        if (next != null){
            return next.validate(str);
        } else {
            return Pair.of(Boolean.TRUE, getSuccessResponse());
        }
    }   
}
