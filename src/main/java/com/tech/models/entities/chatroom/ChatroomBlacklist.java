/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities.chatroom;

import com.tech.models.dtos.chatroom.ChatroomBlacklistDTO;
import com.tech.models.entities.embeddedIds.CRBlacklistComposite;
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
@IdClass(CRBlacklistComposite.class)
@NamedQueries({
    @NamedQuery(name = "ChatroomBlacklist.findByRoomID", query = "SELECT p FROM ChatroomBlacklist p WHERE p.room_id = ?1"),
    @NamedQuery(name = "ChatroomBlacklist.findByRoomMember",query = "SELECT p FROM ChatroomBlacklist p WHERE p.room_member = ?1"),
    @NamedQuery(name = "ChatroomBlacklist.findByRoomIDAndRoomMember",query = "SELECT p FROM ChatroomBlacklist p WHERE p.room_id = ?1 AND p.room_member = ?2")
})
@Table(name = "chatroom_blacklist")
public class ChatroomBlacklist implements Serializable {
    @Id
    private Long room_id;
    
    @Id
    private Long room_member;
    
    @Column(name = "room_ban_time")
    private Date room_ban_time;
    
    @Column(name = "room_expiration_time")
    private Date room_expiration_time;

    public ChatroomBlacklist() {
    }

    public ChatroomBlacklist(Long room_id,Long member_id,ChatroomBlacklistDTO DTO){
        this(room_id,member_id,DTO.getExpiration_date());
    }
    
    public ChatroomBlacklist(Long room_id, Long room_member, Date room_expiration_time) {
        this.room_id = room_id;
        this.room_member = room_member;
        this.room_ban_time = new Date();
        this.room_expiration_time = room_expiration_time;
    }

    public Long getRoom_id() {
        return room_id;
    }

//    public void setRoom_id(Long room_id) {
//        this.room_id = room_id;
//    }

    public Long getRoom_member() {
        return room_member;
    }

//    public void setRoom_member(Long room_member) {
//        this.room_member = room_member;
//    }

    public Date getRoom_ban_time() {
        return room_ban_time;
    }

//    public void setRoom_ban_time(Date room_ban_time) {
//        this.room_ban_time = room_ban_time;
//    }

    public Date getRoom_expiration_time() {
        return room_expiration_time;
    }

    public void setRoom_expiration_time(Date room_expiration_time) {
        this.room_expiration_time = room_expiration_time;
    }
    
}
