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
        messageToModify = new Message()
    }
    
    @After
    public void tearDown() {
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
        
    }

    /**
     * Test of modifyMessage method, of class MessageService.
     */
    @Test
    public void testModifyMessage() 
    {
        
    }

    /**
     * Test of getAllMessages method, of class MessageService.
     */
    @Test
    public void testGetAllMessages() {
        
    }

    /**
     * Test of getByChatRoom method, of class MessageService.
     */
    @Test
    public void testGetByChatRoom() {
        
    }

    /**
     * Test of getBySenderId method, of class MessageService.
     */
    @Test
    public void testGetBySenderId() {
        
    }

    /**
     * Test of getCount method, of class MessageService.
     */
    @Test
    public void testGetCount() {
        
    }

    /**
     * Test of getCountBySenderId method, of class MessageService.
     */
    @Test
    public void testGetCountBySenderId() {
        
    }

    /**
     * Test of getCountByChatroomId method, of class MessageService.
     */
    @Test
    public void testGetCountByChatroomId() {
        
    }

    /**
     * Test of getNextId method, of class MessageService.
     */
    @Test
    public void testGetNextId() {
        
    }
    
}
