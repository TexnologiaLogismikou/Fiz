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
        messageList.add(new Message(4L,1L,"Hello, how are you"));
        messageList.add(new Message(5L,2L,"I'm fine thank you"));
        message = new Message(6L,1L,"Hi");
    }

    @After
    public void tearDown() {
        messageList = null;
        message = null;
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetMessageById() {
        Long userId = messageService.getMessageById(1L).getUserid();
        Long expectedId = 1L;
        Assert.assertEquals("Failure - message wasn't returned", expectedId, userId);
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddMessage() {
        messageService.addMessage(message);
        Assert.assertEquals("Failure - message wasn't added", message.getId(), messageService.getMessageById(6L).getId());
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddMessages() {
        messageService.addMessages(messageList);
        Assert.assertEquals("Failure - first message wasn't added", messageList.get(0).getId(), messageService.getMessageById(4L).getId());
        Assert.assertEquals("Failure - second message wasn't added", messageList.get(1).getId(), messageService.getMessageById(5L).getId());
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteMessage() {
        messageService.deleteMessage(repository.getOne(1L));
        Assert.assertNull("Failure - message wasn't deleted", repository.findOne(1L));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetAllMessages() {
        Assert.assertEquals("Failure - didn't get messages", 3, messageService.getAllMessages().size());
    }
}