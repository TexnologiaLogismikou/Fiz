/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.services.interfaces.IChatroomWhitelistService;
import com.tech.models.entities.ChatroomWhitelist;
import com.tech.repositories.ICRWhitelist;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author iwann
 */
@Service
public class ChatroomWhitelistService implements IChatroomWhitelistService {

    @Autowired
    private ICRWhitelist repository;
    
    @Transactional
    @Override
    public void addUserToWhitelist(ChatroomWhitelist newRecord){
        repository.save(newRecord);
    }  
    
    @Transactional
    @Override
    public boolean deleteUserFromWhitelist(ChatroomWhitelist deleteRecord){
        repository.delete(deleteRecord);
        return true;
    }
    
    @Transactional
    @Override
    public List<ChatroomWhitelist> findByRoomID(Long room_id){
        return repository.findByRoomID(room_id);
    }
    
    @Transactional
    @Override
    public List<ChatroomWhitelist> findByRoomMember(Long room_member){
        return repository.findByRoomMember(room_member);
    }
    
    @Transactional
    @Override
    public ChatroomWhitelist findByRoomIDAndRoomMember(Long room_id,Long room_member){
        return repository.findByRoomIDAndRoomName(room_id, room_member);
    }
    
    @Transactional
    @Override
    public Long countRecords(){
        return repository.count();
    }
    
    @Transactional
    @Override
    public Long countRecordsOfRoom(Long room_id){
        long i = 0;
        for (ChatroomWhitelist vLookUp:repository.findByRoomID(room_id)){
            i++;
        }
        return i;
    }
    
    @Transactional
    @Override
    public Long countRecordsOfMember(Long member_id) {
        long i = 0;
        for(ChatroomWhitelist vLookUp:repository.findByRoomMember(member_id)){
            i++;
        }
        return i;
    }
}

    

