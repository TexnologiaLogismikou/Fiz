/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

import com.tech.models.dtos.superclass.BaseDTO;

/**
 *
 * @author milena
 */
public class FriendDTO extends BaseDTO
{
    private String username;
    private String friendname;

    @Override
    public String getDTOName() {
        return "FriendDTO";
    }
    
    public String getUsername() 
    {
        return username;
    }

    public String getFriendname()
    {
        return friendname;
    }
    
}
