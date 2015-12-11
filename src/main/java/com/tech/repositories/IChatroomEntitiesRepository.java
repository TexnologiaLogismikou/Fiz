/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.chatroom.ChatroomEntities;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KuroiTenshi
 */
@Repository
public interface IChatroomEntitiesRepository extends JpaRepository<ChatroomEntities,Long> {
    ChatroomEntities findByRoomID(Long room_id);
    ChatroomEntities findByRoomName(String room_name);
    
    @Modifying
    @Query("update ChatroomEntities u set u.room_name = ?1 where u.room_id = ?2")
    void setChatroomEntity(String room_name, Long room_id);   
    
    List<ChatroomEntities> findByRoomCreator(Long room_creator); 
    
    @Modifying
    @Query("UPDATE ChatroomEntities c SET c.room_last_activity = ?1 WHERE c.room_id = ?2")
    void setRoomLastActivityByRoomID(Date room_last_activity, Long room_id);
}
