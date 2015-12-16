/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import com.tech.configurations.tools.customvalidators.superclass.StringValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class EmptyStringValidator extends StringValidator implements ICustomValidator,IStringValidator {
    public EmptyStringValidator() {
        super(new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE),"Empty");
    }

    @Override
    public Pair<Boolean, ResponseEntity> validate(String str) {
        // Ego o Arxa ebala ton epipleon elenxo: str.isEmpty(), dioti den pernoyse to test moy otan toy edina ena string = ""
        if (str == null || str.isEmpty()){            
            return Pair.of(Boolean.FALSE, getErrorResponse());
        }
        if (next != null){
            return next.validate(str);
        } else {
            return Pair.of(Boolean.TRUE, getSuccessResponse());
        }
    }    
    
}
