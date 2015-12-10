/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.services.interfaces.IChatroomEntitiesService;
import com.tech.models.entities.ChatroomEntities;
import com.tech.repositories.IChatroomEntitiesRepository;

import java.util.List;
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
    public void addChatroomEntity(ChatroomEntities newRecord) {
        repository.save(newRecord);
    }

    @Transactional
    @Override
    public boolean deleteChatroomEntity(ChatroomEntities deleteRecord) {
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
    public List<ChatroomEntities> findByRoomCreator(Long room_creator) {
        return repository.findByRoomCreator(room_creator);
    }

    @Transactional
    @Override
    public Long countRecords() {
        return repository.count();
    }

    @Transactional
    @Override
    public Long countRecordsOfMember(Long member_id) {
        long i = 0;
        for (ChatroomEntities vLookUp : repository.findByRoomCreator(member_id)) {
            i++;
        }
        return i;
    }
    @Transactional
    @Override
    public boolean checkIfChatroomExists(ChatroomEntities chatroomEntities)
    {
        return repository.findByRoomID(chatroomEntities.getRoom_id()) != null;
    }
}
