/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.chatroom.ChatroomLocation;
import java.util.List;

/**
 *
 * @author iwann
 */
public interface IchatroomLocationService {

    List<ChatroomLocation> findByMaxRange(Integer room_range);

    List<ChatroomLocation> findByRoomID(Long room_id);

    List<ChatroomLocation> findIfNear(float lng, float lat);

    void save(ChatroomLocation saveRecord);
    
}
