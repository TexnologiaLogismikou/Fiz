/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.ChatroomEntities;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KuroiTenshi
 */
@Repository
public interface IChatroomEntitiesRepository extends JpaRepository<ChatroomEntities,Long> {
    ChatroomEntities findByRoomID(Long room_id);
    ChatroomEntities findByRoomName(String room_name);
    List<ChatroomEntities> findByRoomCreator(Long room_creator);    
}
