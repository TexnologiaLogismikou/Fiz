/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities;

import com.tech.models.dtos.ChatroomWhitelistDTO;
import com.tech.models.entities.embeddedIds.CRWhitelistComposite;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author KuroiTenshi
 */
@Entity
@IdClass(CRWhitelistComposite.class)
@NamedQueries({
    @NamedQuery(name = "ChatroomWhitelist.findByRoomID", query = "SELECT p FROM ChatroomWhitelist p WHERE p.room_id = ?1"),
    @NamedQuery(name = "ChatroomWhitelist.findByRoomMember",query = "SELECT p FROM ChatroomWhitelist p WHERE p.room_member = ?1"),
    @NamedQuery(name = "ChatroomWhitelist.findByRoomIDAndRoomName",query = "SELECT p FROM ChatroomWhitelist p WHERE p.room_id = ?1 AND p.room_member = ?2")
})
@Table(name = "chatroom_whitelist")
public class ChatroomWhitelist implements Serializable {
    @Id
    private Long room_id;
    
    @Id
    private Long room_member;
    
    @Column(name = "room_invitation_time")
    private Date room_invitation_time;

    public ChatroomWhitelist() {
    }

    public ChatroomWhitelist(ChatroomWhitelistDTO DTO){
        this(DTO.getRoom_id(),DTO.getMember_id());
    }
    
    public ChatroomWhitelist(Long room_id, Long room_member) {
        this.room_id = room_id;
        this.room_member = room_member;
        this.room_invitation_time = new Date();
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public Long getRoom_member() {
        return room_member;
    }

    public void setRoom_member(Long room_member) {
        this.room_member = room_member;
    }

    public Date getRoom_invitation_time() {
        return room_invitation_time;
    }

    public void setRoom_invitation_time(Date room_invitation_time) {
        this.room_invitation_time = room_invitation_time;
    }
    
}

    /*
    
    "room_id" bigint NOT NULL,
    "room_member" bigint NOT NULL,
    "room_invitation_time" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    */