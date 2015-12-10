/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.ChatroomEntities;

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
    List<ChatroomEntities> findByRoomCreator(Long room_creator);

    @Transactional
    ChatroomEntities findByRoomID(Long room_id);

    @Transactional
    public Long countRecords();

    @Transactional
    public Long countRecordsOfMember(Long member_id);

    @Transactional
    public boolean checkIfChatroomEntityExists(Long room_id);

    @Transactional
    public Long getNextID();
    
    @Transactional
    public boolean validateRoomnameExistance(String room_name);
    
    @Transactional
    public ChatroomEntities getRoomByName(String room_name);
}
