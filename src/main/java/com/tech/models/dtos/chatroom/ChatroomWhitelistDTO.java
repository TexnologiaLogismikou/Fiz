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
public class ChatroomWhitelistDTO extends BaseDTO{
    private String room_name;
    private Long member_id;
    private String mode;//ADD or DELETE
    
    @Override
    public String getDTOName() {
        return "ChatroomWhitelistDTO";
    }

    public String getRoom_name() {
        return room_name;
    }

    public Long getMember_id() {
        return member_id;
    }
    
    public String getMode(){
        return mode;
    }
}
