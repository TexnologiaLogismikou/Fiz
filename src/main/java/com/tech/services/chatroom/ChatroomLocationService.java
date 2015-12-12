/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.chatroom;

import com.tech.services.interfaces.IchatroomLocationService;
import com.tech.models.entities.chatroom.ChatroomLocation;
import com.tech.repositories.IChatroomLocationRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author iwann
 */
@Service
public class ChatroomLocationService implements IchatroomLocationService {
    
    @Autowired
    private IChatroomLocationRepository repository;
    
    @Transactional
    @Override
    public void save(ChatroomLocation saveRecord){
        repository.save(saveRecord);
    }
    
    @Transactional
    @Override
    public List<ChatroomLocation> findByRoomID(Long room_id){
    
        return repository.findByRoomID(room_id);
    }
    
    @Transactional 
    @Override
    public List<ChatroomLocation> findByMaxRange(Integer room_range){
        return repository.findByMaxRange(room_range);
    }
    
    @Transactional 
    @Override
    public List<ChatroomLocation> findIfNear(float lng,float lat){
        return repository.findIfNear(lng, lat);
    }
    
}
