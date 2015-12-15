/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.dtos.ChatRoomLocationResponseDTO;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.chatroom.ChatroomLocation;
import com.tech.models.entities.user.User;
import com.tech.services.chatroom.ChatroomBlacklistService;
import com.tech.services.chatroom.ChatroomEntitiesService;
import com.tech.services.chatroom.ChatroomLocationService;
import com.tech.services.chatroom.ChatroomMembersService;
import com.tech.services.chatroom.ChatroomPrivilegesService;
import com.tech.services.chatroom.ChatroomWhitelistService;
import com.tech.services.user.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.controller;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
@ActiveProfiles({"mixalis","mixalis"})
public class ChatroomControllerTest extends AbstractControllerTest{    
    String uri;
    
    @Mock
    UserService userService;
    
    @Mock 
    ChatroomEntitiesService chatroomEntitesService;
    
    @Mock 
    ChatroomBlacklistService chatroomBlacklistService; 
    
    @Mock 
    ChatroomWhitelistService chatroomWhitelistService; 
    
    @Mock 
    ChatroomPrivilegesService chatroomPrivilegesService; 
    
    @Mock 
    ChatroomMembersService chatroomMembersService;
    
    @Mock
    ChatroomLocationService chatroomLocationService;
    
    @InjectMocks
    ChatroomController controller;
    
    public ChatroomControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/chatroom";
    }
    
    @After
    @Override
    public void tearDown() {
        super.tearDown();
        
        uri = null;
    }

    @Test
    public void testHandleNewChatroom() {
    }

    @Test
    public void testConnectToChatroom() {
    }

    @Test
    public void testDeleteChatroom() {
    }

    @Test
    public void testHandleBans() {
    }

    @Test
    public void testHandleWhitelist() {
    }

    @Test
    public void testRemoveMember() {
    }

    @Test
    public void testUpdateChatroom() {
    }

    @Test
    public void testQuitChatroom() {
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAvailableChatroomsSizeOK() throws Exception{
        
        json.put("lng", 50);
        json.put("lat",40);
        
        List<ChatroomLocation> CL = new ArrayList<>();
        //CL.add(new ChatroomLocation(1L,50,40,100));
        //CL.add(new ChatroomLocation(2L,51,41,100));
        
        when(chatroomLocationService.findIfNear(50, 40)).thenReturn(CL);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/findAvailableChatrooms")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        int status = result.getResponse().getStatus();
         
        verify(chatroomLocationService, times(1)).findIfNear(50,40);
        
        Assert.assertEquals("failure - expected HTTP response OK",200, status);
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAvailableChatroomLocation() throws Exception{
        
        json.put("lng", 50);
        json.put("lat",40);
        
        ChatroomLocation c = new ChatroomLocation(1L,50,40,100);
        ChatroomLocation c2 = new ChatroomLocation(2L,51,41,100);
        
        List<ChatroomLocation> CL = new ArrayList<>();
        CL.add(c);
        CL.add(c2);
        
        when(chatroomLocationService.findIfNear(50, 40)).thenReturn(CL);
        when(chatroomEntitesService.findByRoomID(1L)).thenReturn(new ChatroomEntities(1L,1L,"chatroom"));
        when(chatroomEntitesService.findByRoomID(2L)).thenReturn(new ChatroomEntities(2L,2L,"chatroom2"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/findAvailableChatrooms")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
         
        ChatRoomLocationResponseDTO CRLRDTO = super.mapFromJson(content,ChatRoomLocationResponseDTO.class);
        
        verify(chatroomLocationService, times(1)).findIfNear(50,40);
        verify(chatroomEntitesService,times(1)).findByRoomID(1L);
        verify(chatroomEntitesService,times(1)).findByRoomID(2L);
        
        Assert.assertEquals("failure - expected HTTP response OK",200, status);
   
        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.NO_ERRORS.getData()  + " '",
                CRLRDTO.getResponse().equals(Responses.NO_ERRORS.getData()));
    }

    @Test
    public void testUpdateLocation() {
    }

    @Test
    public void testCheckIfStillInside() {
    }
    
}
