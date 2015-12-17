/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.interfaces;

import com.tech.configurations.tools.Pair;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public interface INumberValidator extends ICustomValidator{
    Pair<Boolean,ResponseEntity> validate(Long n);    
}
