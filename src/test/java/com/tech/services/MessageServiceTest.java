package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.Message;
import com.tech.repositories.IMessageRepository;
import com.tech.services.interfaces.IMessageService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Andreas on 19/11/2015.
 */
@Transactional
public class MessageServiceTest extends AbstractTest {

    @Autowired
    IMessageService messageService;

    @Autowired
    IMessageRepository repository;

    private List<Message> messageList;
    private Message message;

    @Before
    public void setUp() {
        messageList = new ArrayList();
        messageList.add(new Message(1L,1L,"Hi"));
        messageList.add(new Message(2L,1L,"Hello, how are you"));
        messageList.add(new Message(3L,2L,"I'm fine thank you"));
        message = new Message(4L,3L,"Good morning");

        repository.save(messageList);
        repository.save(message);
    }

    @After
    public void tearDown() {
        messageList = null;
        message = null;
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetMessageById() {
        Message tempMessage = messageService.getMessageById(4L);
        Assert.assertEquals("Failure - wrong message returned", tempMessage.getId(), message.getId());
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddMessage() {
        Message message1 = new Message(5L, 3L, "Testing");
        messageService.addMessage(message1);
        Assert.assertEquals("Failure - message wasn't added", message1.getId(), messageService.getMessageById(5L).getId());
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddMessages() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(6L,1L,"Hello, how are you"));
        messages.add(new Message(7L,2L,"I'm fine thank you"));
        messageService.addMessages(messages);
        Assert.assertEquals("Failure - first message wasn't added", messages.get(0).getId(), messageService.getMessageById(6L).getId());
        Assert.assertEquals("Failure - second message wasn't added", messages.get(1).getId(), messageService.getMessageById(7L).getId());
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteMessage() {
        messageService.deleteMessage(message);
        Assert.assertNull("Failure - message wasn't deleted", repository.findOne(4L));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetAllMessages() {
        Assert.assertEquals("Failure - didn't get messages", messageService.getAllMessages().size(), repository.findAll().size());
    }
}