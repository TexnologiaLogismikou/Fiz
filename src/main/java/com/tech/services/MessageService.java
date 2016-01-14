package com.tech.services;

import com.tech.models.entities.Message;
import com.tech.repositories.IMessageRepository;
import com.tech.services.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService implements IMessageService 
{

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
    public void modifyMessage(Message message) 
    {
        repository.setMessageById(message.getUserid(), message.getMessage(), message.getDate(), message.getChatroom(), message.getId());
    }

    @Override
    @Transactional
    public List<Message> getAllMessages() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public List <Message> getByChatRoom(Long chatroom_id)
    {
        return repository.findByChatRoom(chatroom_id);
    }
    
    @Override
    @Transactional
    public List<Message> getBySenderId(Long userid)
    {
        return repository.findBySenderId(userid);
    }
    
    @Override
    @Transactional
    public Long getCount()
    {
        return repository.count();
    }
    
    @Override
    @Transactional
    public Long getCountBySenderId(Long userid)
    {
        //Kanoume Casting dioti h size() epistrefei int
        return Integer.toUnsignedLong(repository.findBySenderId(userid).size());
    }
    
    @Override
    @Transactional
    public Long getCountByChatroomId(Long chatroom_id)
    {
        //Kanoume Casting dioti h size() epistrefei int
        return Integer.toUnsignedLong(repository.findByChatRoom(chatroom_id).size());
    }
    
    @Override
    @Transactional
    public Long getNextId(){
        Long lastID = 0L;    
        
        for(Message vLookUp:repository.findAll()){
            System.out.println(lastID);
            if((vLookUp.getId()- lastID) == 1){
                lastID = vLookUp.getId();
            }else{
                return lastID + 1;
            }            
        }
        
        System.out.println("Finale : " + lastID);
        return lastID + 1L ;       
    }
    
    @Override
    @Transactional
    public void delete(Message message){
        repository.delete(message);
    }
}
