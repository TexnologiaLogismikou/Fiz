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
        return repository.findByMessageId(id);
    }

    @Override
    @Transactional
    public void addMessage(Message message) {
        repository.save(message);
    }

    @Override
    @Transactional
    public void modifyMessage(Message message) {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    @Transactional
    public List<Message> getAllMessages() {
        return repository.findAll();
    }


}
