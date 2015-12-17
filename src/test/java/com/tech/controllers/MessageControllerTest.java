/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.InitializeValidators;
import com.tech.services.MessageService;
import com.tech.services.chatroom.ChatroomEntitiesService;
import com.tech.services.chatroom.ChatroomLocationService;
import com.tech.services.chatroom.ChatroomMembersService;
import com.tech.services.user.UserService;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
@ActiveProfiles({"mixalis","mixalis"})
public class MessageControllerTest extends AbstractControllerTest {
    String uri;
    
    @Mock
    MessageService messageService;

    @Mock
    ChatroomEntitiesService  chatroomEntitieService;
    
    @Mock
    UserService userService;
    
    @Mock
    ChatroomLocationService locationService;
    
    @Mock
    ChatroomMembersService memberService;
    
    @InjectMocks
    MessageController controller;
    
    public MessageControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        InitializeValidators.InitializeCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass()
    {     
        InitializeValidators.CleanCustomValidators();
    } 
    
    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/";
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        
        uri = null;
    }

    @Test
    public void testIncomingMessage() {
    }

    @Test
    public void testMessageHistory() {
    }
    
}
