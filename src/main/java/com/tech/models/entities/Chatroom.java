package com.tech.models.entities;

import com.tech.models.entities.embeddedIds.ChatroomComposite;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by KuroiTenshi on 12/6/2015.
 */
@Entity
@IdClass(ChatroomComposite.class)
@NamedQueries({
        @NamedQuery(name = "Chatroom.findByRoomId", query = "SELECT p FROM Chatroom p WHERE p.room_id = ?1"),
        @NamedQuery(name = "Chatroom.findByRoomCreator", query = "SELECT p FROM Chatroom p WHERE p.room_creator = ?1"),
        @NamedQuery(name = "Chatroom.findByRoomMember", query = "SELECT p FROM Chatroom p WHERE p.room_member = ?1"),
        @NamedQuery(name = "Chatroom.findByRoomName", query = "SELECT p FROM Chatroom p WHERE p.room_name = ?1"),
        @NamedQuery(name = "Chatroom.deleteByCreator", query = "DELETE FROM Chatroom p WHERE p.room_creator = ?1"),
        @NamedQuery(name = "Chatroom.deleteByRoomId", query = "DELETE FROM Chatroom p WHERE p.room_id = ?1"),
        @NamedQuery(name = "Chatroom.deleteByMember", query = "DELETE FROM Chatroom p WHERE p.room_member = ?1")
})
@Table(name = "chatrooms")
public class Chatroom implements Serializable {
    @Id
    @Column(name = "room_id")
    private Long room_id;

    @Id
    @Column(name = "room_creator")
    private Long room_creator;

    @Id
    @Column(name = "room_member")
    private Long room_member;

    @Column(name = "room_name")
    private String room_name;

    public Chatroom() {
    }

    public Chatroom(Long room_id, String room_name, Long room_creator, Long room_member) {
        this.room_id = room_id;
        this.room_name = room_name;
        this.room_creator = room_creator;
        this.room_member = room_member;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public Long getRoom_creator() {
        return room_creator;
    }

    public void setRoom_creator(Long room_creator) {
        this.room_creator = room_creator;
    }

    public Long getRoom_member() {
        return room_member;
    }

    public void setRoom_member(Long room_member) {
        this.room_member = room_member;
    }
}
