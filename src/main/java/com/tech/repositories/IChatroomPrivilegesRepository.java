/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.ChatroomPrivileges;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
    @Modifying
    @Query("update ChatroomPrivileges u set u.room_privileges = ?1, u.room_password_protected = ?2,"
            + " u.room_password = ?3, u.room_access_method = ?4 where u.room_id = ?5")
    void setChatroomEntity(String room_privileges,Boolean room_password_protected,String room_password,String room_access_method ,Long room_id);   
}
