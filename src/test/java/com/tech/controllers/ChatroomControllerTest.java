/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.services.chatroom.ChatroomBlacklistService;
import com.tech.services.chatroom.ChatroomEntitiesService;
import com.tech.services.chatroom.ChatroomLocationService;
import com.tech.services.chatroom.ChatroomMembersService;
import com.tech.services.chatroom.ChatroomPrivilegesService;
import com.tech.services.chatroom.ChatroomWhitelistService;
import com.tech.services.user.UserService;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
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
    @Sql(scripts = "classpath:populateDB.sql")
    public void updateChatroomRoomNotExist() throws Exception
    {
        json.put("room_name", "hi");
        json.put("new_room_name", "new room");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "whitelist");
        json.put("passwordProtection",false);
        json.put("password", "");
        json.put("max_range", "5");
        
        when(chatroomEntitesService.validateRoomnameExistance("hi")).thenReturn(false);
        
          MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/updateChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("hi");
        verify(chatroomEntitesService, times(0)).getRoomByName(anyString());
        verify(chatroomEntitesService, times(0)).setChatroomEntities(anyString(),anyLong());
        verify(chatroomPrivilegesService, times(0)).setChatroomPrivileges(anyString(),anyBoolean(),anyString(),anyString(),anyLong());
        verify(chatroomLocationService, times(0)).setNewMaxRange(anyInt(),anyLong());
        
        Assert.assertEquals("failure - expected HTTP response NOT_FOUND",
                404, status);
   
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));  
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void updateChatroom() throws Exception
    {
        json.put("room_name", "first testing room");
        json.put("new_room_name", "hi");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "whitelist");
        json.put("passwordProtection",false);
        json.put("password", "123");
        json.put("max_range", "5");
        
        when(chatroomEntitesService.validateRoomnameExistance("first testing room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        doNothing().when(chatroomEntitesService).setChatroomEntities("hi",1L);
        doNothing().when(chatroomPrivilegesService).setChatroomPrivileges("PUBLIC",false,"123","whitelist",1L);
        doNothing().when(chatroomLocationService).setNewMaxRange(5,1L);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/updateChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first testing room");
        verify(chatroomEntitesService, times(1)).getRoomByName("first testing room");
        verify(chatroomEntitesService, times(1)).setChatroomEntities("hi",1L);
        verify(chatroomPrivilegesService, times(1)).setChatroomPrivileges("PUBLIC",false,"123","whitelist",1L); //why not true?
        verify(chatroomLocationService, times(1)).setNewMaxRange(5,1L);
        
        Assert.assertEquals("failure - expected HTTP response OK",
                200, status);
   
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }
   
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testUpdateLocationRoomNameNotExist() throws Exception
    {
        json.put("lng", "6.7");
        json.put("lat", "5.6");
        json.put("room_name", "hi");
        
         when(chatroomEntitesService.validateRoomnameExistance("hi")).thenReturn(false);
        
          MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/updateChatroomLocation")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("hi");
        verify(chatroomEntitesService, times(0)).getRoomByName(anyString());
        verify(chatroomLocationService, times(0)).setNewLngLat(anyFloat(),anyFloat(),anyLong());
        
        Assert.assertEquals("failure - expected HTTP response NOT_FOUND",
                404, status);
   
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));  
    }
    
    @Test
    public void testQuitChatroom() {
    }

    @Test
    public void testAvailableChatrooms() {
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testUpdateLocation() throws Exception
    {
        json.put("lng", "67"); // not sure about this 
        json.put("lat", "56");
        json.put("room_name", "first testing room");
        
        when(chatroomEntitesService.validateRoomnameExistance("first testing room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        doNothing().when(chatroomLocationService).setNewLngLat(67,56,1L);
        
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/updateChatroomLocation")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first testing room");
        verify(chatroomEntitesService, times(1)).getRoomByName("first testing room");
        verify(chatroomLocationService, times(1)).setNewLngLat(67,56,1L);
        
        Assert.assertEquals("failure - expected HTTP response OK",
                200, status);
   
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
        
        
    }

    @Test
    public void testCheckIfStillInside() {
    }
    
}
