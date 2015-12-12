/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities.chatroom;

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
@Entity
@NamedQueries({
    @NamedQuery(name = "ChatroomLocation.findByRoomID", query = "SELECT p FROM ChatroomLocation p WHERE p.room_id = ?1"),
    @NamedQuery(name = "ChatroomLocation.findIfNear", query = "SELECT u FROM ChatroomLocation u \n" +
                                                              "WHERE u.room_id IN ( SELECT p.room_id \n" +
                                                              "FROM ChatroomLocation p WHERE earth_distance(ll_to_earth( ?1, ?2 ), \n" +
                                                              "ll_to_earth(p.room_lng, p.room_lat)) < p.room_max_range )"),
    @NamedQuery(name = "ChatroomLocation.findByMaxRange", query = "SELECT u FROM ChatroomLocation u WHERE u.room_max_range = ?1")
})
@Table(name = "chatroom_location")
public class ChatroomLocation implements Serializable {
    @Id
    @Column(name = "room_id")
    private Long room_id;
    
    @Column(name = "room_lat")
    private float room_lat;
    
    @Column(name = "room_lng")
    private float room_lng;
    
    @Column(name = "room_max_range")
    private int room_max_range;
    
    public ChatroomLocation() {
    }

    public ChatroomLocation(Long room_id, float room_lat, 
            float room_lng, int room_max_range) {
        this.room_id = room_id;
        this.room_lat = room_lat;
        this.room_lng = room_lng;
        this.room_max_range = room_max_range;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public float getRoom_lat() {
        return room_lat;
    }

    public void setRoom_lat(float room_lat) {
        this.room_lat = room_lat;
    }

    public float getRoom_lng() {
        return room_lng;
    }

    public void setRoom_lng(float room_lng) {
        this.room_lng = room_lng;
    }

    public int getRoom_max_range() {
        return room_max_range;
    }

    public void setRoom_max_range(int room_max_range) {
        this.room_max_range = room_max_range;
    }
}