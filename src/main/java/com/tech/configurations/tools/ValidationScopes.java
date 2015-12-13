/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

/**
 *
 * @author KuroiTenshi
 */
public enum ValidationScopes {
    NAME("name"),
    PASSWORD("password"),
    EMAIL("email"),
    METHOD("method");
    
    private final String str;
    ValidationScopes(String str) {
        this.str = str;        
    }
    
    public String getData(){
        return str;
    }
    
}
