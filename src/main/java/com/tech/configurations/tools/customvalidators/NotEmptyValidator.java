/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.interfaces.CustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.NameValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class NotEmptyValidator implements CustomValidator,NameValidator{
    private final ResponseEntity response = new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE);
    
    @Override
    public Pair<Boolean, ResponseEntity> validate(String str) {
        if(str.isEmpty()){
            return Pair.of(Boolean.FALSE,response);
        }
        return Pair.of(Boolean.TRUE,response);
    }

    @Override
    public ValidationScopes scope() {
        return ValidationScopes.NAME;
    }    
}
