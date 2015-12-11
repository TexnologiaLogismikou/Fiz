package com.tech.models.dtos;

import com.tech.models.dtos.superclass.BaseDTO;


public class MessageDTO extends BaseDTO {
    private String message;
    private Long user;
    private Long chatroom_id;
    
    @Override
    public String getDTOName() {
        return "MessageDTO";
    }
    
    public String getMessage() {
        return message;
    }

    public Long getUser() {
        return user;
    }
    
    public Long getChatroom_id(){
        return chatroom_id;
    }
}
