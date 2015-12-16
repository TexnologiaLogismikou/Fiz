/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.stringvalidators;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import com.tech.configurations.tools.customvalidators.superclass.StringValidator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class IncludeInListValidator extends StringValidator implements IStringValidator{
    private final List<String> LIST;
    public IncludeInListValidator(List<String> list) {
        super(new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE),"IncludeInListValidator");
        this.LIST = list;
    }

    @Override
    public Pair<Boolean, ResponseEntity> validate(String str) {
        if(str.isEmpty() || !LIST.contains(str)){
            return Pair.of(Boolean.FALSE,getErrorResponse());            
        }
        if (next != null){
            return next.validate(str);
        } else {
            return Pair.of(Boolean.TRUE, getSuccessResponse());
        }
    }  
    
}
