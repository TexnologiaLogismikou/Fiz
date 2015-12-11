/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.ChatroomBlacklist;
import java.util.Date;
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
public interface ICRBlacklist extends JpaRepository<ChatroomBlacklist, Long>{
    List<ChatroomBlacklist> findByRoomID(Long room_id);
    List<ChatroomBlacklist> findByRoomMember(Long room_member);
    ChatroomBlacklist findByRoomIDAndRoomMember(Long room_id,Long room_member);
    @Modifying
    @Query("update ChatroomBlacklist u set u.room_expiration_time = ?1 where u.room_id = ?2 AND u.room_member = ?3")
    void setChatroomBlacklist(Date room_expiration_time,Long room_id, Long room_member);
}
