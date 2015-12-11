/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.chatroom.ChatroomBlacklist;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author iwann
 */
public interface IChatroomBlacklistService {

    @Transactional
    void add(ChatroomBlacklist newRecord);

    @Transactional
    Long countRecords();

    @Transactional
    Long countRecordsOfMember(Long member_id);

    @Transactional
    Long countRecordsOfRoom(Long room_id);

    @Transactional
    boolean delete(ChatroomBlacklist deleteRecord);

    @Transactional
    List<ChatroomBlacklist> findByRoomID(Long room_id);

    @Transactional
    ChatroomBlacklist findByRoomIDAndRoomMember(Long room_id, Long room_member);

    @Transactional
    List<ChatroomBlacklist> findByRoomMember(Long room_member);
    
    @Transactional
    void setNewTime(Long room_id,Long member_id,Date room_expiration_time);
}
