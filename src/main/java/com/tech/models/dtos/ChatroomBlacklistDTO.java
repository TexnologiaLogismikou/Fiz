/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

import com.tech.models.dtos.superclass.BaseDTO;
import java.util.Date;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomBlacklistDTO extends BaseDTO{
    private Long room_id;
    private Long member_id;
    private Date expiration_date;
    
    @Override
    public String getDTOName() {
        return "ChatroomBlacklistDTO";
    }

    public Long getRoom_id() {
        return room_id;
    }

    public Long getMember_id() {
        return member_id;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }
}
