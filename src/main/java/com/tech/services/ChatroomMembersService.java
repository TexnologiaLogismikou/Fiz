/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.models.entities.ChatroomBlacklist;
import com.tech.models.entities.ChatroomEntities;
import com.tech.models.entities.ChatroomMembers;
import com.tech.repositories.IChatroomEntitiesRepository;
import com.tech.repositories.IChatroomMembersRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tech.services.interfaces.IChatroomMembersService;

/**
 *
 * @author iwann
 */@Service
public class ChatroomMembersService implements IChatroomMembersService {
    @Autowired
    private IChatroomMembersRepository repository;
    
    @Transactional
    @Override
    public void addUserToBlacklist(ChatroomMembers newRecord){
        repository.save(newRecord);
    }   
     
    @Transactional
    @Override
    public boolean deleteUserFromChatEntities(ChatroomMembers deleteRecord){
        repository.delete(deleteRecord);
        return true;
    }
    
    @Transactional
    @Override
    public List<ChatroomMembers> findByRoomId(Long room_id){
        return repository.findByRoomId(room_id);
    }
    
    @Transactional
    @Override
    public List<ChatroomMembers> findByRoomMember(Long room_member){
        return repository.findByRoomMember(room_member);
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
        for (ChatroomMembers vLookUp:repository.findByRoomId(room_id)){
            i++;
        }
        return i;
    }
    
    @Transactional
    @Override
    public Long countRecordsOfMember(Long member_id) {
        long i = 0;
        for(ChatroomMembers vLookUp:repository.findByRoomMember(member_id)){
            i++;
        }
        return i;
    }
    
}
