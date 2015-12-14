/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.chatroom.ChatroomPrivileges;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author iwann
 */
public interface IChatroomPrivilegesService {

    @Transactional
    void add(ChatroomPrivileges newRecord);

    @Transactional
    boolean delete(ChatroomPrivileges deleteRecord);

    @Transactional
    List<ChatroomPrivileges> findByPrivileges(String room_privileges);

    @Transactional
    ChatroomPrivileges findByRoomId(Long room_id);

    @Transactional
    ChatroomPrivileges validateAccess(Long room_id, String room_password);
    
    @Transactional
    Long countRecords();
    
    @Transactional
    Long countRecordsOfPrivileges(String room_privileges);
    
    @Transactional
    void setChatroomPrivileges(String room_privileges,Boolean room_password_protected,String room_password,String room_access_method, Long room_id);
}
