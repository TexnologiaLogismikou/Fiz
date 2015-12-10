package com.tech.models.dtos;

import com.tech.models.dtos.superclass.BaseDTO;


public class MessageDTO extends BaseDTO {
    private String message;
    private String user;
    private String color;

    @Override
    public String getDTOName() {
        return "MessageDTO";
    }
    
    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }

    public String getColor() {
        return color;
    }
}
