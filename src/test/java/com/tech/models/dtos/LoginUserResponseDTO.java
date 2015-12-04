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
public class LoginUserResponseDTO {
    private String username;
    private String role;  
    private String error;

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getError() {
        return error;
    }
}
