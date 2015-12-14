/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.chatroom.ChatroomPrivileges;
import com.tech.models.entities.user.User;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
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
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomNotExist() throws Exception 
    {
        json.put("creator_name", "milena");
        json.put("room_name", "tech");
        json.put("room_password", "12345");
        
        when(chatroomEntitesService.validateRoomnameExistance("tech")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deleteChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("tech");
        verify(chatroomEntitesService, times(0)).getRoomByName(anyString());
        verify(userService, times(0)).getUserByUsername(anyString());
        verify(userService, times(0)).getUserById(anyLong());
        verify(chatroomPrivilegesService, times(0)).findByRoomId(anyLong());
        verify(chatroomEntitesService, times(0)).delete(any(ChatroomEntities.class));
        verify(userService, times(0)).updateUserRoom(anyBoolean(), anyLong());
        
        
        Assert.assertEquals("failure - expected HTTP response NOT_FOUND",
                404, status);
   
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.ROOM_NOT_FOUND.getData() + "'",
                    content.equals(Responses.ROOM_NOT_FOUND.getData()));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomMemberDoesntHaveRoom() throws Exception 
    {
        json.put("creator_name", "vasilis");
        json.put("room_name", "hi");
        json.put("room_password", "");
       
        User user = new User(4L,"vasilis","paok",true,false);
        
        when(chatroomEntitesService.validateRoomnameExistance("hi")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("hi")).thenReturn(new ChatroomEntities(4L,2L,"hi"));
        when(userService.getUserByUsername("vasilis")).thenReturn(user);
        when(userService.getUserById(4L)).thenReturn(user);
  

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deleteChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("hi");
        verify(chatroomEntitesService, times(1)).getRoomByName("hi");
        verify(userService, times(1)).getUserByUsername("vasilis");
        verify(userService, times(1)).getUserById(4L);
        verify(chatroomPrivilegesService, times(0)).findByRoomId(anyLong());
        verify(chatroomEntitesService, times(0)).delete(any(ChatroomEntities.class));
        verify(userService, times(0)).updateUserRoom(anyBoolean(), anyLong());
        
        Assert.assertEquals("failure - expected HTTP response UNAUTHORIZED",
                401, status);
   
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
                    content.equals(Responses.NOT_AUTHORIZED.getData()));
       
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomWrongCreator() throws Exception 
    {
        json.put("creator_name", "iwannaFot");
        json.put("room_name", "first testing room");
        json.put("room_password", "");
        
        when(chatroomEntitesService.validateRoomnameExistance("first testing room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        when(userService.getUserByUsername("iwannaFot")).thenReturn(new User(2L,"iwannaFot","iwanna",true,true));
        when(userService.getUserById(2L)).thenReturn(new User(2L,"iwannaFot","iwanna",true,true));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deleteChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first testing room");
        verify(chatroomEntitesService, times(1)).getRoomByName("first testing room");
        verify(userService, times(1)).getUserByUsername("iwannaFot");
        verify(userService, times(1)).getUserById(2L);
        verify(chatroomPrivilegesService, times(0)).findByRoomId(anyLong());
        verify(chatroomEntitesService, times(0)).delete(any(ChatroomEntities.class));
        verify(userService, times(0)).updateUserRoom(anyBoolean(), anyLong());
        
        Assert.assertEquals("failure - expected HTTP response UNAUTHORIZED",
                401, status);
   
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
                    content.equals(Responses.NOT_AUTHORIZED.getData()));  
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomWrongPassword() throws Exception 
    {
        json.put("creator_name", "mixalisMix");
        json.put("room_name", "third testing room");
        json.put("room_password", "xaxa");
        
        when(chatroomEntitesService.validateRoomnameExistance("third testing room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("third testing room")).thenReturn(new ChatroomEntities(3L,3L,"third testing room"));
        when(userService.getUserByUsername("mixalisMix")).thenReturn(new User(3L,"mixalisMix","mixalis",true,true));
        when(userService.getUserById(3L)).thenReturn(new User(3L,"mixalisMix","mixalis",true,true));
        when(chatroomPrivilegesService.findByRoomId(3L)).thenReturn(new ChatroomPrivileges(3L,"PUBLIC",true,"password","blacklist"));
        
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deleteChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("third testing room");
        verify(chatroomEntitesService, times(1)).getRoomByName("third testing room");
        verify(userService, times(1)).getUserByUsername("mixalisMix");
        verify(userService, times(1)).getUserById(3L);
        verify(chatroomPrivilegesService, times(1)).findByRoomId(3L);
        verify(chatroomEntitesService, times(0)).delete(any(ChatroomEntities.class));
        verify(userService, times(0)).updateUserRoom(anyBoolean(), anyLong());
        
        Assert.assertEquals("failure - expected HTTP response UNAUTHORIZED",
                401, status);
   
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
                    content.equals(Responses.NOT_AUTHORIZED.getData()));  
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroom() throws Exception 
    {
        json.put("creator_name", "mixalisMix");
        json.put("room_name", "third testing room");
        json.put("room_password", "password");
        
        ChatroomEntities CE = new ChatroomEntities(3L,3L,"third testing room");
                
        when(chatroomEntitesService.validateRoomnameExistance("third testing room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("third testing room")).thenReturn(CE);
        when(userService.getUserByUsername("mixalisMix")).thenReturn(new User(3L,"mixalisMix","mixalis",true,true));
        when(userService.getUserById(3L)).thenReturn(new User(3L,"mixalisMix","mixalis",true,true));
        when(chatroomPrivilegesService.findByRoomId(3L)).thenReturn(new ChatroomPrivileges(3L,"PUBLIC",true,"password","blacklist"));
        when(chatroomEntitesService.delete(CE)).thenReturn(true);
        doNothing().when(userService).updateUserRoom(false, 3L);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deleteChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("third testing room");
        verify(chatroomEntitesService, times(1)).getRoomByName("third testing room");
        verify(userService, times(1)).getUserByUsername("mixalisMix");
        verify(userService, times(1)).getUserById(3L);
        verify(chatroomPrivilegesService, times(1)).findByRoomId(3L);
        verify(chatroomEntitesService, times(1)).delete(CE);
        verify(userService, times(1)).updateUserRoom(false, 3L);
        
        Assert.assertEquals("failure - expected HTTP response OK",
                200, status);
   
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData())); 
        
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
    public void testAvailableChatrooms() {
    }

    @Test
    public void testUpdateLocation() {
    }

    @Test
    public void testCheckIfStillInside() {
    }
    
}
