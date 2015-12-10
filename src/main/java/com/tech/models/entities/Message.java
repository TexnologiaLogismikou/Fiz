package com.tech.models.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Message.findByMessageId", query = "SELECT p FROM Message p WHERE p.id = ?1"),
        @NamedQuery(name = "Message.findBySenderId",query = "SELECT p FROM Message p WHERE p.userid = ?1"),
        @NamedQuery(name = "Message.findByChatRoom",query = "SELECT p FROM Message p WHERE p.chatroom_id = ?1")
//    @NamedQuery(named = "Message.FindByDateOfSend", query = "SELECT p FROM Message p WHERE p.dateSent = ?1")
})
@Table (name = "messages")
public class Message implements Serializable{
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "userid")
    private Long userid;

    @Column(name = "message")
    private String message;

    @Column(name = "datesent")
    private Date dateSent;
    //TODO : Anazitisi kai leitourgies me basi

    @Column(name = "chatroom_id")
    private Long chatroom_id;

    protected Message() {}

    public Message(Long id, Long userid, String message, Long chatroom) {
        this.message = message;
        this.id = id;
        this.userid = userid;
        this.dateSent = new Date();
        this.chatroom_id = chatroom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getDate() {
        return dateSent;
    }

    public void setDate() {
        this.dateSent = new Date();
    }

    public Long getChatroom() {
        return chatroom_id;
    }

    public void setChatroom(Long chatroom) {
        this.chatroom_id = chatroom;
    }
}
