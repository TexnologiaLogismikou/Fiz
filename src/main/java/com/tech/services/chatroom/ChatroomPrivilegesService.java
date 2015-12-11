/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.chatroom;

import com.tech.services.interfaces.IChatroomPrivilegesService;
import com.tech.models.entities.chatroom.ChatroomPrivileges;
import com.tech.repositories.IChatroomPrivilegesRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author iwann
 */
@Service
public class ChatroomPrivilegesService implements IChatroomPrivilegesService {
    @Autowired
    private IChatroomPrivilegesRepository repository;
    
    @Transactional
    @Override
    public void add(ChatroomPrivileges newRecord){
        repository.save(newRecord);
    }  
    
    @Transactional
    @Override
    public boolean delete(ChatroomPrivileges deleteRecord){
        repository.delete(deleteRecord);
        return true;
    }
    
    @Transactional
    @Override
    public ChatroomPrivileges findByRoomId(Long room_id){
        return repository.findByRoomId(room_id);
    }
    
    @Transactional
    @Override
    public  List<ChatroomPrivileges> findByPrivileges(String room_privileges){
        return repository.findByPrivileges(room_privileges);
    }
    
    @Transactional
    @Override
    public ChatroomPrivileges validateAccess(Long room_id,String room_password){
        return repository.validateAccess(room_id, room_password);
    }
    
    @Transactional
    @Override
    public Long countRecords(){
        return repository.count();
    }
    
    @Transactional
    @Override
    public Long countRecordsOfPrivileges(String room_privileges) {
        long i = 0;
        for(ChatroomPrivileges vLookUp:repository.findByPrivileges(room_privileges)){
            i++;
        }
        return i;
    }
    
    @Transactional
    @Override
    public void setChatroomPrivileges(String room_privileges,Boolean room_password_protected,
            String room_password,String room_access_method, Long room_id){
        
        repository.setChatroomEntity(room_privileges, room_password_protected, 
                room_password, room_access_method,room_id);
    }
}
