/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.responses;

/**
 *
 * @author milena
 */
public class UserResponseDTO 
{
    private String username;
    private String firstname;
    private String last_name;
    private String birthday;
    private String hometown;
    private String response;
    private String status;
    private String profile_photo;
    private String email;

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
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

    public String getResponse() {
        return response;
    }

    public String getStatus() {
        return status;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public String getEmail() {
        return email;
    }
}
