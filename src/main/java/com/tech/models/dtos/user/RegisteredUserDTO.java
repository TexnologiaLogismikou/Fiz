/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.user;

import com.tech.models.dtos.superclass.BaseDTO;

/**
 *
 * @author KuroiTenshi
 */
public class RegisteredUserDTO extends BaseDTO {
    private String username;
    private String password;
    private String email;
    private String last_name;
    private String firstname;
    private String birthday;
      
    @Override
    public String getDTOName() {
        return "RegisteredUserDTO";
    }
    
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
    
    public String getFirstName(){
        return firstname;
    }
}
