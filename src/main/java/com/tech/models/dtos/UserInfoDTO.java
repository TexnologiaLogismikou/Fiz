/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

/**
 *
 * @author iwann
 */
public class UserInfoDTO {
    private String email;
    private String profile_photo;
    private String status;
    private String last_name;
    private String birthday;
    private String hometown;
    
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
    
    
    
}