package com.tech.services.interfaces;

import com.tech.models.entities.Message;

import javax.transaction.Transactional;
import java.util.List;

public interface IMessageService 
{
    @Transactional
    Message getMessageById(Long id);

    @Transactional
    void addMessage(Message message);

    @Transactional
    void modifyMessage(Message message);

    @Transactional
    List<Message> getAllMessages();
    
    @Transactional
    List <Message> getByChatRoom(Long chatroom_id);
    
    @Transactional
    List<Message> getBySenderId(Long userid);
    
    @Transactional
    Long getCount();
    
    @Transactional
    Long getCountBySenderId(Long userid);
    
    @Transactional
    Long getCountByChatroomId(Long chatroom_id);
    
    @Transactional
    Long getNextId();
}
