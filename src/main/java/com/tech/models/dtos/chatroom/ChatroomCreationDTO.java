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
public class ChatroomCreationDTO extends BaseDTO{
    private String username;
    private String room_name;
    private String room_privilege;
    private String access_method;
    private float room_lat;
    private float room_lng;
    private int room_max_range;
    private boolean hasPassword;
    private String password;

    @Override
    public String getDTOName() {
        return "ChatroomCreationDTO";
    }

    public String getUsername() {
        return username;
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

    public float getRoom_lat() {
        return room_lat;
    }

    public float getRoom_lng() {
        return room_lng;
    }

    public int getRoom_max_range() {
        return room_max_range;
    }    
}
