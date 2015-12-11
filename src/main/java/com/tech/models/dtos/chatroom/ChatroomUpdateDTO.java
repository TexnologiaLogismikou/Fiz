/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.tech.models.dtos.superclass.BaseDTO;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomUpdateDTO extends BaseDTO{
    private String room_name;//WONT CHANGE
    private String new_room_name;
    private String room_privilege;
    private String access_method;
    private boolean passwordProtection;
    private String password;

    @Override
    public String getDTOName() {
        return "ChatroomUpdateDTO";
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getNew_room_name() {
        return new_room_name;
    }

    public String getRoom_privilege() {
        return room_privilege;
    }

    public String getAccess_method() {
        return access_method;
    }

    public boolean isPasswordProtected() {
        return passwordProtection;
    }

    public String getPassword() {
        return password;
    }    
}