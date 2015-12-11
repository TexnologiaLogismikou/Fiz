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
public class ChatroomMemberDTO extends BaseDTO{
    private String room_name;
    private Long member_id;
    private String password;
    private String method;//should accept only "ADD" "DELETE" and maybe "UPDATE"?
    
    @Override
    public String getDTOName() {
        return "ChatroomNewMemberDTO";
    }

    public String getRoom_name() {
        return room_name;
    }

    public Long getMember_id() {
        return member_id;
    }
    
    public String getMethod(){
        return method;
    }

    public String getPassword() {
        return password;
    }
}
