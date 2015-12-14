package com.tech.models.dtos;

import com.tech.models.dtos.superclass.BaseDTO;


public class MessageDTO extends BaseDTO {
    private String message;
    private String username;
    private String chatroom_name;
    private float lng;
    private float lat;
    private int ttl;
    
    @Override
    public String getDTOName() {
        return "MessageDTO";
    }

    public MessageDTO() {
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getChatroom_name() {
        return chatroom_name;
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }

    public int getTtl() {
        return ttl;
    }
}
