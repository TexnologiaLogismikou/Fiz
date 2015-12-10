/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities;

import com.tech.models.dtos.ChatroomCreationDTO;
import com.tech.models.dtos.ChatroomMemberDTO;
import com.tech.models.dtos.RegisteredUserDTO;
import com.tech.models.entities.embeddedIds.ChatroomMembersComposite;
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
@NamedQueries({
        @NamedQuery(name = "ChatroomMembers.findByRoomIdAndMember", query = "SELECT p FROM ChatroomMembers p WHERE p.room_id = ?1 AND p.room_member = ?2"),
        @NamedQuery(name = "ChatroomMembers.findByRoomId", query = "SELECT p FROM ChatroomMembers p WHERE p.room_id = ?1"),
        @NamedQuery(name = "ChatroomMembers.findByRoomMember",query = "SELECT p FROM ChatroomMembers p WHERE p.room_member = ?1")
})
@Entity
@IdClass(ChatroomMembersComposite.class)
@Table (name = "chatrooms_members")
public class ChatroomMembers implements Serializable {
    @Id
    private Long room_id;

    @Id
    private Long room_member;

    @Column(name = "room_joined_date")
    private Date room_joined_date;

    public ChatroomMembers() {
    }

    public ChatroomMembers(Long room_id, ChatroomMemberDTO DTO){
        this(room_id,DTO.getMember_id());
    }
    
    public ChatroomMembers(Long room_id, ChatroomCreationDTO DTO){
        this(room_id,DTO.getUserid());
    }

    public ChatroomMembers(Long room_id, Long room_member) {
        this.room_id = room_id;
        this.room_member = room_member;
        this.room_joined_date = new Date();
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

    public Date getRoom_joined_date() {
        return room_joined_date;
    }

    public void setRoom_joined_date(Date room_joined_date) {
        this.room_joined_date = room_joined_date;
    }

}
