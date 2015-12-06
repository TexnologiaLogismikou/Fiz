/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author KuroiTenshi
 */
@NamedQueries({
        @NamedQuery(name = "ChatroomPrivileges.findByRoomId", query = "SELECT p FROM ChatroomPrivileges p WHERE p.room_id = ?1"),
        @NamedQuery(name = "ChatroomPrivileges.findByPrivileges",query = "SELECT p FROM ChatroomPrivileges p WHERE p.room_privileges = ?1")
})
@Entity
@Table (name = "chatroom_privileges")
public class ChatroomPrivileges implements Serializable {
    @Id
    private Long room_id;
    
    @Column(name = "room_privileges")
    private String room_privileges;

    public ChatroomPrivileges() {
    }

    public ChatroomPrivileges(Long room_id, String room_privileges) {
        this.room_id = room_id;
        this.room_privileges = room_privileges;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public String getRoom_privileges() {
        return room_privileges;
    }

    public void setRoom_privileges(String room_privileges) {
        this.room_privileges = room_privileges;
    }
    
}
