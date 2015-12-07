/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.ChatroomPrivileges;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KuroiTenshi
 */
@Repository
public interface IChatroomPrivilegesRepository extends JpaRepository<ChatroomPrivileges,Long>{
    ChatroomPrivileges findByRoomId(Long room_id);
    List<ChatroomPrivileges> findByPrivileges(String room_privileges);
    ChatroomPrivileges validateAccess(Long room_id,String room_password);
}
