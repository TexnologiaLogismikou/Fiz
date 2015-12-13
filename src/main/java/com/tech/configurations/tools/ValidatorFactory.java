/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import com.tech.configurations.tools.customvalidators.interfaces.CustomValidator;
import com.tech.models.dtos.superclass.BaseDTO;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author iwann
 */
public class ValidatorFactory {
    private static final HashMap<Integer,CustomValidator> AVAILABLE_VALIDATORS = new HashMap();    
    private static ResponseEntity response = new ResponseEntity(HttpStatus.NOT_MODIFIED);
    public static ResponseEntity getResponse() {
        return response;
    }
     
    public static Pair<Boolean,ResponseEntity> validateDTO (BaseDTO DTO) {
        int code = DTO.getDTOName().toLowerCase().hashCode();
        if (!AVAILABLE_VALIDATORS.containsKey(code)) {
            response = new ResponseEntity(Responses.VALIDATOR_NOT_FOUND.getData(),HttpStatus.NOT_FOUND);
            return new Pair<>(false, response);
        }
        boolean booleanResponse = AVAILABLE_VALIDATORS.get(code).validate(DTO);
        return new Pair<>(booleanResponse, AVAILABLE_VALIDATORS.get(code).response());
    }
    
    public static boolean registerDTOValidator(CustomValidator DTOvalidator) {
        if (AVAILABLE_VALIDATORS.containsKey(DTOvalidator.hashCode())) {
            return false;
        }
        AVAILABLE_VALIDATORS.put(DTOvalidator.hashCode(),DTOvalidator);
        return true;
    }
}
