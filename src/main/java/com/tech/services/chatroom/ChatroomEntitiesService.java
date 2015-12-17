/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.chatroom;

import com.tech.services.interfaces.IChatroomEntitiesService;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.repositories.IChatroomEntitiesRepository;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author iwann
 */
@Service
public class ChatroomEntitiesService implements IChatroomEntitiesService {
    @Autowired
    private IChatroomEntitiesRepository repository;

    @Transactional
    @Override
    public void add(ChatroomEntities newRecord) {
        repository.save(newRecord);
    }

    @Transactional
    @Override
    public boolean delete(ChatroomEntities deleteRecord) {
        repository.delete(deleteRecord);
        return true;
    }

    @Transactional
    @Override
    public ChatroomEntities findByRoomID(Long room_id) {
        return repository.findByRoomID(room_id);
    }

    @Transactional
    @Override
    public ChatroomEntities findByRoomCreator(Long room_creator) {
        return repository.findByRoomCreator(room_creator);
    }

    @Transactional
    @Override
    public Long countRecords() {
        return repository.count();
    }

//    @Transactional
//    @Override
//    public Long countRecordsOfMember(Long member_id) {
//        long i = 0;
//        for (ChatroomEntities vLookUp : repository.findByRoomCreator(member_id)) {
//            i++;
//        }
//        return i;
//    }
    
    @Transactional
    @Override
    public boolean checkIfChatroomEntityExists(Long room_id)
    {
        return repository.findByRoomID(room_id) != null;
    }
    
    /**
     * TODO reCheck this code
     * More Complex test for ValueInTheMiddle 1-2-4-5 will it return 3? 
     * @return 
     */
    @Transactional
    @Override
    public Long getNextID(){
        Long lastID = 0L;    
        
        for(ChatroomEntities vLookUp:repository.findAll()){
            if((vLookUp.getRoom_id() - lastID) == 1){
                lastID = vLookUp.getRoom_id();
            }else{
                return lastID + 1;
            }            
        }
        return lastID + 1L ;       
    }
    
    @Transactional
    @Override
    public boolean validateRoomnameExistance(String room_name){
        return repository.findByRoomName(room_name) != null;
    }

    @Transactional
    @Override
    public ChatroomEntities getRoomByName(String room_name) {
        return repository.findByRoomName(room_name);
    }    
    
    @Transactional
    @Override
    public void setChatroomEntities(String room_name,Long room_id){
        repository.setChatroomEntity(room_name, room_id);
    }
    
    @Transactional
    @Override
    public void updateLastActivity(Date last_activity,Long room_id){
        repository.setRoomLastActivityByRoomID(last_activity, room_id);
    }
}
