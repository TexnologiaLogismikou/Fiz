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
public class ChatroomDeleteDTO extends BaseDTO{
    private Long creator_id;
    private String room_name;
    
    @Override
    public String getDTOName() {
        return "ChatroomDeleteDTO";
    }

    public Long getCreator_id() {
        return creator_id;
    }

    public String getRoom_name() {
        return room_name;
    }
}
