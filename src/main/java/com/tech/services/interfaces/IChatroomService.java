package com.tech.services.interfaces;

import com.tech.models.entities.Chatroom;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by KuroiTenshi on 12/6/2015.
 */
public interface IChatroomService {
    @Transactional
    Chatroom getChatroomByID(Long room_id);

    @Transactional
    List<Chatroom> getChatroomByCreator(Long room_creator);

    @Transactional
    List<Chatroom> getChatroomByMember(Long room_member);

    @Transactional
    List<Chatroom> getChatroomByRoomname(String room_name);

    @Transactional
    boolean removeChatroomByID(Long room_id);

    @Transactional
    boolean removeChatroomByCreator(Long room_creator);

    @Transactional
    boolean removeChatroomBymember(Long room_member);

    @Transactional
    boolean modifyRoomNameByRoomID(String room_name, Long room_id);
    
    @Transactional
    public Long getNextID();
}
