/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.chatroom.ChatroomEntities;
import java.util.Date;

import java.util.List;
import javax.transaction.Transactional;

/**
 * @author iwann
 */
public interface IChatroomEntitiesService {

    @Transactional
    void add(ChatroomEntities newRecord);

    @Transactional
    boolean delete(ChatroomEntities deleteRecord);

    @Transactional
    ChatroomEntities findByRoomCreator(Long room_creator);

    @Transactional
    ChatroomEntities findByRoomID(Long room_id);

    @Transactional
    Long countRecords();

//    @Transactional
//    Long countRecordsOfMember(Long member_id);

    @Transactional
    boolean checkIfChatroomEntityExists(Long room_id);

    @Transactional
    Long getNextID();
    
    @Transactional
    boolean validateRoomnameExistance(String room_name);
    
    @Transactional
    ChatroomEntities getRoomByName(String room_name);
    
    @Transactional
    void setChatroomEntities(String room_name,Long room_id);    
    
    @Transactional    
    void updateLastActivity(Date last_activity,Long room_id);
}
