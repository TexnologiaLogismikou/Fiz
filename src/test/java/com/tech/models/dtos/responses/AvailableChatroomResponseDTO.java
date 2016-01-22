package com.tech.models.dtos.responses;

/**
 * Created by Andreas on 16/12/2015.
 */
public class AvailableChatroomResponseDTO {
    private String error;
    private String size;
    private String chatroom_1;
    private String chatroom_2;

    public String getError() {
        return error;
    }

    public String getSize() {
        return size;
    }

    public String getChatroom_1() {
        return chatroom_1;
    }

    public String getChatroom_2() {
        return chatroom_2;
    }
}
