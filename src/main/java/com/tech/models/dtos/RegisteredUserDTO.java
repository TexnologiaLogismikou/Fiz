/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

/**
 *
 * @author KuroiTenshi
 */
public class RegisteredUserDTO {
    private String username;
    private String password;
    private String email;
    private String last_name;
    private String birthday;
    
    public String getEmail(){
        return email;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getBirthday() {
        return birthday;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
}
