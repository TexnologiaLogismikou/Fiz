/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.superclass;

import com.tech.configurations.tools.Pair;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public abstract class BaseDTO {
    public abstract String getDTOName();
    public abstract Pair<Boolean,ResponseEntity> validate() throws InappropriateValidatorException;
}
