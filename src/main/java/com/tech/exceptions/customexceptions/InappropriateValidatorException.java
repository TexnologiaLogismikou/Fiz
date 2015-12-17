/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.exceptions.customexceptions;

/**
 *
 * @author KuroiTenshi
 */
public class InappropriateValidatorException extends Exception{
    public InappropriateValidatorException(){
        super("Validator format was inappropriate");
    }
}
