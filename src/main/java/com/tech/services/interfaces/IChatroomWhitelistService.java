/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.chatroom.ChatroomBlacklist;
import com.tech.models.entities.chatroom.ChatroomWhitelist;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author iwann
 */
public interface IChatroomWhitelistService {

    @Transactional
    void add(ChatroomWhitelist newRecord);

    @Transactional
    Long countRecords();

    @Transactional
    Long countRecordsOfMember(Long member_id);

    @Transactional
    Long countRecordsOfRoom(Long room_id);

    @Transactional
    boolean delete(ChatroomWhitelist deleteRecord);

    @Transactional
    List<ChatroomWhitelist> findByRoomID(Long room_id);

    @Transactional
    ChatroomWhitelist findByRoomIDAndRoomMember(Long room_id, Long room_member);

    @Transactional
    List<ChatroomWhitelist> findByRoomMember(Long room_member);
    
}
