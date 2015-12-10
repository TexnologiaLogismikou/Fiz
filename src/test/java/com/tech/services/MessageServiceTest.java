/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.Message;
import com.tech.services.interfaces.IMessageService;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author arxa
 */

@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class MessageServiceTest extends AbstractTest
{
    @Autowired
    private IMessageService service;
    
   // private final String testTextMessage = "test message";
    private final String testTextMessage = "test message";
    private final String testModifiedTextMessage = "this text message has been modified";
    private Message messageToAdd;
    private Message messageToModify;
    
    public MessageServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() 
    {
        messageToAdd = new Message(5L,3L,testTextMessage,1L);
        messageToModify = new Message(1L,1L,testModifiedTextMessage,1L);
    }
    
    @After
    public void tearDown() 
    {
        messageToAdd = null;
        messageToModify = null;
    }

    /**
     * Test of getMessageById method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetMessageById() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getMessageById(1L).getMessage().equals("initial message"));
        Assert.assertFalse(Responses.ERROR.getData(),service.getMessageById(2L).getMessage().equals("initial message"));
    }

    /**
     * Test of addMessage method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddMessage() 
    {
        service.addMessage(messageToAdd);
        Assert.assertTrue(Responses.ERROR.getData(),service.getMessageById(5L).getMessage().equals(testTextMessage));
    }

    /**
     * Test of modifyMessage method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyMessage() 
    {
        service.modifyMessage(messageToModify);
        Assert.assertTrue(Responses.ERROR.getData(),service.getMessageById(1L).getMessage().equals(testModifiedTextMessage));
    }

    /**
     * Test of getAllMessages method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetAllMessages() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getAllMessages().size()==4);
    }

    /**
     * Test of getByChatRoom method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetByChatRoom() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getByChatRoom(1L).size()==3);
        Assert.assertTrue(Responses.ERROR.getData(),service.getByChatRoom(2L).size()==1);
    }

    /**
     * Test of getBySenderId method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetBySenderId() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getBySenderId(2L).size()==2);
        Assert.assertTrue(Responses.ERROR.getData(),service.getBySenderId(1L).size()==1);
        Assert.assertTrue(Responses.ERROR.getData(),service.getBySenderId(3L).size()==1);   
    }

    /**
     * Test of getCount method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetCount() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getCount().compareTo(4L)==0);
    }

    /**
     * Test of getCountBySenderId method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetCountBySenderId() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getCountBySenderId(2L).compareTo(2L)==0);
        Assert.assertTrue(Responses.ERROR.getData(),service.getCountBySenderId(1L).compareTo(1L)==0);
        Assert.assertTrue(Responses.ERROR.getData(),service.getCountBySenderId(3L).compareTo(1L)==0);
    }

    /**
     * Test of getCountByChatroomId method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetCountByChatroomId() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getCountByChatroomId(1L).compareTo(3L)==0);
        Assert.assertTrue(Responses.ERROR.getData(),service.getCountByChatroomId(2L).compareTo(1L)==0);
    }

    /**
     * Test of getNextId method, of class MessageService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetNextId() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getNextId().compareTo(5L)==0);
    }
}
