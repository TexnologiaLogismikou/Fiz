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
public class ChatroomWhitelistDTO extends BaseDTO{
    private Long room_id;
    private Long member_id;
    
    @Override
    public String getDTOName() {
        return "ChatroomWhitelistDTO";
    }

    public Long getRoom_id() {
        return room_id;
    }

    public Long getMember_id() {
        return member_id;
    }
    
}
