/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.ChatroomMembers;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author iwann
 */
public interface IChatrooMembersService {

    @Transactional
    void addUserToBlacklist(ChatroomMembers newRecord);

    @Transactional
    Long countRecords();

    @Transactional
    Long countRecordsOfMember(Long member_id);

    @Transactional
    Long countRecordsOfRoom(Long room_id);

    @Transactional
    boolean deleteUserFromChatEntities(ChatroomMembers deleteRecord);

    @Transactional
    List<ChatroomMembers> findByRoomId(Long room_id);

    @Transactional
    List<ChatroomMembers> findByRoomMember(Long room_member);
    
}
