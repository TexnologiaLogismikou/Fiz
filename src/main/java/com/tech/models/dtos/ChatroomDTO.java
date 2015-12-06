/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomDTO {
    private Long room_id;
    private Long room_creator;
    private Long room_member;
    private String room_name;

    public Long getRoom_id() {
        return room_id;
    }

    public Long getRoom_creator() {
        return room_creator;
    }

    public Long getRoom_member() {
        return room_member;
    }

    public String getRoom_name() {
        return room_name;
    }
    
}
