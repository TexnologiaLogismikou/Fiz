/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.interfaces;

import com.tech.exceptions.customexceptions.InappropriateValidatorException;

/**
 *
 * @author KuroiTenshi
 */
public interface ICustomValidator {    
    void setNext(ICustomValidator next) throws InappropriateValidatorException;    
}
