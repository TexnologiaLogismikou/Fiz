/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.ChatroomWhitelist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KuroiTenshi
 */
@Repository
public interface ICRWhitelist extends JpaRepository<ChatroomWhitelist, Long>{
    List<ChatroomWhitelist> findByRoomID(Long room_id);
    List<ChatroomWhitelist> findByRoomMember(Long room_member);
    ChatroomWhitelist findByRoomMemberAndID(Long room_id, Long room_member);  
}
