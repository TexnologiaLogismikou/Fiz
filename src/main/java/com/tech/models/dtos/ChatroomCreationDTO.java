/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

import com.tech.models.dtos.superclass.BaseDTO;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomCreationDTO extends BaseDTO{
    private Long userid;
    private String room_name;
    private String room_privilege;
    private String access_method;
    private boolean hasPassword;
    private String password;

    @Override
    public String getDTOName() {
        return "ChatroomCreationDTO";
    }

    public Long getUserid() {
        return userid;
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getAccess_method() {
        return access_method;
    }

    public boolean hasPassword() {
        return hasPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getRoom_privilege() {
        return room_privilege;
    }
    
}
