package com.tech.repositories;

import com.tech.models.entities.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by KuroiTenshi on 12/6/2015.
 */
@Repository
public interface IChatroomRepository extends JpaRepository<Chatroom, Long> {
    Chatroom findByRoomId(Long room_id);
    List<Chatroom> findByRoomName(String room_name);
    List<Chatroom> findByRoomCreator(Long room_creator);
    List<Chatroom> findByRoomMember(Long room_member);
    void deleteByCreator(Long room_creator);
    void deleteByRoomId(Long room_id);
    void deleteByMember(Long room_member);
    @Modifying
    @Query("update Chatroom u set u.room_name = ?1 where u.room_id = ?2") //mipos 8elei na ta balw ola?
    void setRoomNameByRoomId(String room_name, Long room_id );
}
