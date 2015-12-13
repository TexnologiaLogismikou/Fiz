/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.chatroom.ChatroomLocation;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author KuroiTenshi
 */
public interface ICRLocationService {

    @Transactional
    boolean checkIfStillInside(Long room_id, float lng, float lat);

    @Transactional
    List<ChatroomLocation> findByMaxRange(Integer room_range);

    @Transactional
    List<ChatroomLocation> findByRoomID(Long room_id);

    @Transactional
    List<ChatroomLocation> findIfNear(float lng, float lat);

    @Transactional
    void add(ChatroomLocation saveRecord);

    @Transactional
    void setNewLngLat(float lng, float lat, Long room_id);

    @Transactional
    void setNewMaxRange(int room_max_range, Long room_id);
    
}
