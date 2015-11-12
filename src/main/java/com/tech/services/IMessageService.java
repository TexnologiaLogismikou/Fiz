package com.tech.services;

import com.tech.Models.Message;

import javax.transaction.Transactional;
import java.util.List;

public interface IMessageService {
    @Transactional
    Message getMessageById(Long id);

    @Transactional
    void addMessage(Message message);

    @Transactional
    void addMessages(List<Message> messages);

    @Transactional
    void deleteMessage(Message message);

    @Transactional
    List<Message> getAllMessages();
}
