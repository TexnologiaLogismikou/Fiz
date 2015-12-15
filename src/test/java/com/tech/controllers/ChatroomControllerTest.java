/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.chatroom.ChatroomEntities;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
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
    public void testUpdateChatroom() {
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testQuitChatroomUserExistance() throws Exception{
        json.put("room_name", "bill");
        json.put("user_name","vasilis");
        
        when(chatroomEntitesService.validateRoomnameExistance("bill")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/quitChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("bill");
        verify(userService, times(0)).checkUsername(anyString());
        verify(chatroomEntitesService, times(0)).getRoomByName(anyString());
        verify(userService, times(0)).getUserByUsername(anyString());
        verify(chatroomMembersService, times(0)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        //verify(chatroomMembersService, times(0)).delete(CM);
        
        Assert.assertEquals("failure - expected HTTP response NOT_FOUND",404, status);
        
        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.NOT_AVAILABLE.getData()  + " '",
                content.equals(Responses.NOT_AVAILABLE.getData() ));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testQuitChatroomCheckUsername() throws Exception{
        json.put("room_name", "bill");
        json.put("user_name","vasilis");
        
        when(chatroomEntitesService.validateRoomnameExistance("bill")).thenReturn(true);
        when(userService.checkUsername("vasilis")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/quitChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("bill");
        verify(userService, times(1)).checkUsername("vasilis");
        verify(chatroomEntitesService, times(0)).getRoomByName(anyString());
        verify(userService, times(0)).getUserByUsername(anyString());
        verify(chatroomMembersService, times(0)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        
        Assert.assertEquals("failure - expected HTTP response NOT_FOUND",404, status);
        
        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.NOT_AVAILABLE.getData()  + " '",
                content.equals(Responses.NOT_AVAILABLE.getData() ));
    }
    
    
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testQuitChatroomMembersService() throws Exception{
        json.put("room_name", "first testing room");
        json.put("user_name","mixalisMix");
        
        
        when(chatroomEntitesService.validateRoomnameExistance("first testing room")).thenReturn(true);
        when(userService.checkUsername("mixalisMix")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        when(userService.getUserByUsername("mixalisMix")).thenReturn(new User (3L,"mixalisMix","mixalis",true,true));
        when(chatroomMembersService.checkIfMemberExistsInChatroom(3L,1L)).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/quitChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first testing room");
        verify(userService, times(1)).checkUsername("mixalisMix");
        verify(chatroomEntitesService, times(1)).getRoomByName("first testing room");
        verify(userService, times(1)).getUserByUsername("mixalisMix");
        verify(chatroomMembersService, times(1)).checkIfMemberExistsInChatroom(3L,1L);
        //verify(chatroomMembersService, times(0)).delete(CM);
        
        Assert.assertEquals("failure - expected HTTP response NOT_FOUND",404, status);
        
        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.NOT_AVAILABLE.getData()  + " '",
                content.equals(Responses.NOT_AVAILABLE.getData() ));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testQuitChatroom() throws Exception{
        json.put("room_name", "first testing room");
        json.put("user_name","milenaAz");
        
        
        when(chatroomEntitesService.validateRoomnameExistance("first testing room")).thenReturn(true);
        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        when(userService.getUserByUsername("milenaAz")).thenReturn(new User (1L,"milenaAz","milena",true,true));
        when(chatroomMembersService.checkIfMemberExistsInChatroom(1L,1L)).thenReturn(true);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/quitChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first testing room");
        verify(userService, times(1)).checkUsername("milenaAz");
        verify(chatroomEntitesService, times(1)).getRoomByName("first testing room");
        verify(userService, times(1)).getUserByUsername("milenaAz");
        verify(chatroomMembersService, times(1)).checkIfMemberExistsInChatroom(1L,1L);
        //verify(chatroomMembersService, times(0)).delete(CM);
        
        Assert.assertEquals("failure - expected HTTP response OK",200, status);
        
        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.SUCCESS.getData()  + " '",
                content.equals(Responses.SUCCESS.getData()));
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
