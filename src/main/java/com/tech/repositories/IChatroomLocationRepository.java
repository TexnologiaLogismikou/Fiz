/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.chatroom.ChatroomLocation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author iwann
 */
@Repository
public interface IChatroomLocationRepository  extends JpaRepository<ChatroomLocation, Long>{
    List<ChatroomLocation> findByRoomID(Long room_id);
    List<ChatroomLocation> findByMaxRange(Integer room_range);
    List<ChatroomLocation> findIfNear(float lng,float lat);
    
    @Modifying
    @Query("update ChatroomLocation u set u.room_max_range = ?1 where u.room_id = ?2")
    void setChatroomLocationMaxRangeById(Long id ,Integer  room_max_range); 
    
    @Query("update ChatroomLocation u set u.room_lng = ?1,u.room_lat = ?2 where u.room_id = ?3")
    void setChatroomLocationLngAndLatById(Long id ,float lng , float lat);
    
}
