package com.tech.repositories;

import com.tech.models.entities.Message;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    Message findByMessageId(Long id);
    List<Message> findBySenderId(Long userid);
    List<Message> findByChatRoom(Long chatroom_id);
    
    @Modifying
    @Query("update Message u set u.userid = ?1, u.message = ?2, u.dateSent = ?3, u.chatroom_id = ?4 where u.id = ?5")
    void setMessageById(Long userid,String message,Date datesent,Long chatroom_id,Long id);      
}
