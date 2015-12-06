/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.services.interfaces.IChatroomService;
import com.tech.models.entities.Chatroom;
import com.tech.repositories.IChatroomRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author KuroiTenshi
 */
@Service
public class ChatroomService implements IChatroomService {
    @Autowired
    private IChatroomRepository repository;

    @Transactional
    @Override
    public Chatroom getChatroomByID(Long room_id) {
        return repository.findByRoomId(room_id);
    }

    @Transactional
    @Override
    public List<Chatroom> getChatroomByCreator(Long room_creator) {
        return repository.findByRoomCreator(room_creator);
    }

    @Transactional
    @Override
    public List<Chatroom> getChatroomByMember(Long room_member) {
        return repository.findByRoomMember(room_member);
    }

    @Transactional
    @Override
    public List<Chatroom> getChatroomByRoomname(String room_name) {
        return repository.findByRoomName(room_name);
    }

    @Transactional
    @Override
    public boolean removeChatroomByID(Long room_id){
        repository.deleteByRoomId(room_id);
        return true;
    }

    @Transactional
    @Override
    public boolean removeChatroomByCreator(Long room_creator){
        repository.deleteByCreator(room_creator);
        return true;
    }

    @Transactional
    @Override
    public boolean removeChatroomBymember(Long room_member){
        repository.deleteByMember(room_member);
        return true;
    }

    @Transactional
    @Override
    public boolean modifyRoomNameByRoomID(String room_name, Long room_id){
        repository.setRoomNameByRoomId(room_name, room_id);
        return true;
    }
    
    @Transactional
    @Override
    public Long getNextID(){
        Long i = repository.count();
        Long x = repository.getOne(i).getRoom_id();
        return x + 1L;
    }
}
