/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

/**
 *
 * @author milena
 */
public class UserResponseDTO 
{
    private String username;
    private String firstname;  
    private String last_name;
    private String profile_photo;
    private String email;  
    private String birthday;
    private String hometown;
    private String status;  
    private String response;

    public UserResponseDTO()
    {
                
    }
    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHometown() {
        return hometown;
    }

    public String getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }
}
