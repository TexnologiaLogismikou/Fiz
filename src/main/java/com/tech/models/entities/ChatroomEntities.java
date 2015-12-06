/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities;

import com.tech.models.entities.embeddedIds.ChatroomEntitiesComposite;
import java.io.Serializable;
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
        @NamedQuery(name = "ChatroomEntities.findByRoomID", query = "SELECT p FROM ChatroomEntities p WHERE p.room_id = ?1"),
        @NamedQuery(name = "ChatroomEntities.findByRoomCreator",query = "SELECT p FROM ChatroomEntities p WHERE p.room_creator = ?1")
})
@Entity
@IdClass(ChatroomEntitiesComposite.class)
@Table (name = "chatrooms_entities")
public class ChatroomEntities implements Serializable {
    @Id
    private Long room_id; 
    
    @Id
    private Long room_creator;

    public ChatroomEntities() {
    }

    public ChatroomEntities(Long room_id, Long room_creator) {
        this.room_id = room_id;
        this.room_creator = room_creator;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public Long getRoom_creator() {
        return room_creator;
    }

    public void setRoom_creator(Long room_creator) {
        this.room_creator = room_creator;
    }
    
    
}
