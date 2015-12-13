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
public class ChatroomDeleteDTO extends BaseDTO{
    private String creator_name;
    private String room_name;
    private String room_password;
    
    @Override
    public String getDTOName() {
        return "ChatroomDeleteDTO";
    }

    public String getCreator_name() {
        return creator_name;
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getRoom_password() {
        return room_password;
    }
}
