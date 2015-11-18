package com.tech.services;

import com.tech.models.entities.Message;
import com.tech.repositories.IMessageRepository;
import com.tech.services.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private IMessageRepository repository;

    @Override
    @Transactional
    public Message getMessageById(Long id) {
        return repository.getOne(id);
    }

    @Override
    @Transactional
    public void addMessage(Message message) {
        repository.save(message);
    }

    @Override
    @Transactional
    public void addMessages(List<Message> messages) {
        repository.save(messages);
    }

    @Override
    @Transactional
    public void deleteMessage(Message message) {
        repository.delete(message);
    }

    @Override
    @Transactional
    public List<Message> getAllMessages() {
        return repository.findAll();
    }

}
