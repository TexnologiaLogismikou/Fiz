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
public class ChatroomLocationDTO extends BaseDTO{
    private float lng;
    private float lat;
    
    public ChatroomLocationDTO(){        
    }
    
    @Override
    public String getDTOName() {
        return "ChatroomNewMemberDTO";
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }   
}
