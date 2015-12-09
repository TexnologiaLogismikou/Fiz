/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.ChatroomPrivileges;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author iwann
 */
public interface IChatroomPrivilegesService {

    @Transactional
    void addUserToBlacklist(ChatroomPrivileges newRecord);

    @Transactional
    boolean deleteUserFromBlacklist(ChatroomPrivileges deleteRecord);

    @Transactional
    List<ChatroomPrivileges> findByPrivileges(String room_privileges);

    @Transactional
    ChatroomPrivileges findByRoomId(Long room_id);

    @Transactional
    ChatroomPrivileges validateAccess(Long room_id, String room_password);
    
    @Transactional
    public Long countRecords();
    
    @Transactional
    public Long countRecordsOfPrivileges(String room_privileges);
}
