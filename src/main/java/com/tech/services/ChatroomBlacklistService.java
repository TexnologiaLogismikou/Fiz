/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.models.entities.ChatroomBlacklist;
import com.tech.repositories.ICRBlacklist;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tech.services.interfaces.IChatroomBlacklistService;

/**
 *
 * @author iwann
 */
@Service
public class ChatroomBlacklistService implements IChatroomBlacklistService{
    @Autowired
    private ICRBlacklist repository;
    
    @Transactional
    @Override
    public void addUserToBlacklist(ChatroomBlacklist newRecord){
        repository.save(newRecord);
    }  
    
    @Transactional
    @Override
    public boolean deleteUserFromBlacklist(ChatroomBlacklist deleteRecord){
        repository.delete(deleteRecord);
        return true;
    }
    
    @Transactional
    @Override
    public List<ChatroomBlacklist> findByRoomID(Long room_id){
        return repository.findByRoomID(room_id);
    }
    
    @Transactional
    @Override
    public List<ChatroomBlacklist> findByRoomMember(Long room_member){
        return repository.findByRoomMember(room_member);
    }
    
    @Transactional
    @Override
    public ChatroomBlacklist findByRoomIDAndRoomMember(Long room_id,Long room_member){
        return repository.findByRoomIDAndRoomMember(room_id, room_member);
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
        for (ChatroomBlacklist vLookUp:repository.findByRoomID(room_id)){
            i++;
        }
        return i;
    }
    
    @Transactional
    @Override
    public Long countRecordsOfMember(Long member_id) {
        long i = 0;
        for(ChatroomBlacklist vLookUp:repository.findByRoomMember(member_id)){
            i++;
        }
        return i;
    }
}
