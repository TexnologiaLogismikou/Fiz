/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.superclass;

import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.Validator;
import com.tech.configurations.tools.ValidatorFactory;
import com.tech.configurations.tools.customvalidators.interfaces.CustomValidator;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public abstract class BaseValidator implements CustomValidator{
    private final String myName;
    private ResponseEntity newResponse;
    
    protected BaseValidator(String myName){
        this.myName = myName;
        ValidatorFactory.registerDTOValidator(this);
    }
   
    protected void setResponseEntity(Responses response, HttpStatus status){
            newResponse = new ResponseEntity(response.getData(),status);
    }
    
    @Override
    public ResponseEntity response() {
        return newResponse;
    }
    
    @Override
    public int hashCode() {
        return myName.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseValidator other = (BaseValidator) obj;
        if (!Objects.equals(this.myName, other.myName)) {
            return false;
        }
        if (!Objects.equals(this.newResponse, other.newResponse)) {
            return false;
        }
        return true;
    }
    
}
