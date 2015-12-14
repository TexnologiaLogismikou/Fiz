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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class MatchValidator extends StringValidator implements ICustomValidator,IStringValidator{
    private final Pattern P;
    private Matcher m;
    
    public MatchValidator(String p) {
        super(new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE));
        this.P = Pattern.compile(p);
    }
    
    /**
     * 
     * @param str
     * @return if the regex returns true means that the validation wasn't met and the validate will return false
     */
    @Override
    public Pair<Boolean, ResponseEntity> validate(String str) {
        m = P.matcher(str);
        
        //String contains special characters
        if (m.find()) {
            return Pair.of(Boolean.FALSE,getErrorResponse());
        }  
        if (next != null){
            return next.validate(str);
        } else {
            return Pair.of(Boolean.TRUE, getSuccessResponse());
        }
    }    
}
