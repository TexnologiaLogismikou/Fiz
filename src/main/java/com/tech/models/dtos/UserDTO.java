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
    private String username;
    private String password;
    private boolean enabled;
    private String email;
    private String profile_photo;
    private String status;
    private String last_name;
    private String birthday;
    private String hometown;
    private String first_name;
    
    public String getEmail(){
        return email;
    }
    
    public String getProfile_photo(){
        return profile_photo;
    }
    
    public String getStatus(){
        return status;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHometown() {
        return hometown;
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
    
    public String getFirstName(){
        return first_name;
    }
            
}
