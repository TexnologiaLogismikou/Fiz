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
public class MessageHistoryRequestDTO {
    private String room_name;
    private String member_name;
    private Long lat;
    private Long lng;

    public MessageHistoryRequestDTO() {
    }

    public String getRoom_name() {
        return room_name;
    }

    public Long getLat() {
        return lat;
    }

    public Long getLng() {
        return lng;
    }    

    public String getMember_name() {
        return member_name;
    }
}
