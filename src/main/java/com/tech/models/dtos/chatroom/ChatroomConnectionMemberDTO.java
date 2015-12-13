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
public class ChatroomConnectionMemberDTO extends BaseDTO{
    private String room_name;
    private String member_name;
    private String password;
    private String method;
    private float lat;
    private float lng;
    
    @Override
    public String getDTOName() {
        return "ChatroomConnectionMemberDTO";
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getMember_id() {
        return member_name;
    }
    
    public String getMethod(){
        return method;
    }

    public String getPassword() {
        return password;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }
}
