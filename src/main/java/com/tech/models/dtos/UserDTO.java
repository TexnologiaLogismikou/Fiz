/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

/**
 *
 * @author bill5_000
 */
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    
    public Long getid(){
        return id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public boolean getEnabled(){
        return enabled;
    }
            
}
