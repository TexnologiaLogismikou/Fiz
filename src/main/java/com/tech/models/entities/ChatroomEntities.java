/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities;

import com.tech.models.entities.embeddedIds.ChatroomEntitiesComposite;
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

    @Column(name = "room_name")
    private String room_name;
    
    @Column(name = "room_creation_date")
    private Date room_creation_date;
    
    @Column(name = "room_last_activity")
    private Date room_last_activity;
    
    public ChatroomEntities() {
    }

    public ChatroomEntities(Long room_id, Long room_creator, String room_name, Date room_creation_date, Date room_last_activity) {
        this.room_id = room_id;
        this.room_creator = room_creator;
        this.room_name = room_name;
        this.room_creation_date = room_creation_date;
        this.room_last_activity = room_last_activity;
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

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public Date getRoom_creation_date() {
        return room_creation_date;
    }

    public void setRoom_creation_date(Date room_creation_date) {
        this.room_creation_date = room_creation_date;
    }

    public Date getRoom_last_activity() {
        return room_last_activity;
    }

    public void setRoom_last_activity(Date room_last_activity) {
        this.room_last_activity = room_last_activity;
    }   
}
