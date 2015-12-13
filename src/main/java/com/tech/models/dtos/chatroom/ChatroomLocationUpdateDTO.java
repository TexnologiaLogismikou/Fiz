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
public class ChatroomLocationUpdateDTO extends BaseDTO {
    private float lng;
    private float lat;
    private String room_name;
    
    public ChatroomLocationUpdateDTO(){        
    }
    
    @Override
    public String getDTOName() {
        return "ChatroomLocationUpdateDTO";
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }   

    public String getRoom_name() {
        return room_name;
    }
}
