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
public enum AvailableRoles {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");
    
    private final String str;
    AvailableRoles(String str) {
        this.str = str;        
    }
    
    public String getData(){
        return str;
    }
    
    
    
}
