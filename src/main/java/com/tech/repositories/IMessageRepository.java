package com.tech.repositories;

import com.tech.models.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    Message findByMessageId(Long id);
    List<Message> findBySenderId(Long userid);
    List<Message> findByChatRoom(String chatroom_id);
}
