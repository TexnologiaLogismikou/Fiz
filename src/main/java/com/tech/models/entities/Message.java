package com.tech.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table (name = "messages")
public class Message {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "userid")
    private Long userid;

    @Column(name = "message")
    private String message;

    @Column(name = "dateSent")
    private Date dateSent;

    @Column(name = "TTL")
    private Date ttl;

    @Column(name = "chatroom")
    private String chatroom;
    
    protected Message() {}

    public Message(Long id, Long userid, String message, Date ttl, String chatroom) {
        this.message = message;
        this.id = id;
        this.userid = userid;
        this.dateSent = new Date();
        this.ttl = ttl;
        this.chatroom = chatroom;
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
    
    public Date getTtl() {
        return ttl;
    }

    public void setTtl(Date ttl) {
        this.ttl = ttl;
    }

    public String getChatroom() {
        return chatroom;
    }

    public void setChatroom(String chatroom) {
        this.chatroom = chatroom;
    }
    
}
