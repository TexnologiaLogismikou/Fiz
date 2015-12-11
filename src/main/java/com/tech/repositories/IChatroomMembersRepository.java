/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.ChatroomMembers;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KuroiTenshi
 */
@Repository
public interface IChatroomMembersRepository extends JpaRepository<ChatroomMembers, Long>{
    List<ChatroomMembers> findByRoomId(Long room_id);
    List<ChatroomMembers> findByRoomMember(Long room_member);
    ChatroomMembers findByRoomIdAndMember(Long room_id, Long room_member);
}
