/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.chatroom.ChatroomMembers;
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
    @Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_Ok() throws Exception
    {
        json.put("room_name","first testing room");
        json.put("member_name","milenaAz");
        json.put("password","");
        json.put("method","DELETE");
        
        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        when(userService.getUserByUsername("milenaAz")).thenReturn(new User(1L,"milenaAz","milena",true,true));
        when(chatroomPrivilegesService.findByRoomId(1L)).thenReturn(new ChatroomPrivileges(1L,"PUBLIC",false,null,"whitelist")); 
        when(chatroomMembersService.checkIfMemberExistsInChatroom(1L,1L)).thenReturn(true); 
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/removeMember")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername(anyString());
        verify(chatroomEntitesService,times(2)).getRoomByName(anyString());
        verify(userService,times(1)).getUserByUsername(anyString());
        verify(chatroomPrivilegesService,times(1)).findByRoomId(anyLong());
        verify(chatroomMembersService,times(1)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        verify(chatroomMembersService,times(1)).delete(any(ChatroomMembers.class));
        
        Assert.assertEquals("failure - expected HTTP status to be '200'", 200, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_WrongMethod() throws Exception
    {
        json.put("room_name","first testing room");
        json.put("member_name","milenaAz");
        json.put("password","");
        json.put("method","ADD"); // 'ADD' Method is WRONG
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/removeMember")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("failure - expected HTTP status to be '400'", 400, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.ACCESS_METHOD_NOT_FOUND.getData()));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_UnavailableUsername() throws Exception
    {
        json.put("room_name","first testing room");
        json.put("member_name","Arxa"); // 'Arxa' does not exist
        json.put("password","");
        json.put("method","DELETE");
      
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/removeMember")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername(anyString());
      
        Assert.assertEquals("failure - expected HTTP status to be '404'", 404, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_NoRoomName() throws Exception
    {
        json.put("room_name","fourth testing room"); // 'fourth testing room' does not exist
        json.put("member_name","milenaAz");
        json.put("password","");
        json.put("method","DELETE");
        
        when(userService.checkUsername("milenaAz")).thenReturn(true);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/removeMember")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername(anyString());
        verify(chatroomEntitesService,times(1)).getRoomByName(anyString());
  
        Assert.assertEquals("failure - expected HTTP status to be '404'", 404, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_MemberDoesNotExistInChatroom() throws Exception
    {
        json.put("room_name","first testing room");
        json.put("member_name","milenaAz");
        json.put("password","");
        json.put("method","DELETE");
        
        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        when(userService.getUserByUsername("milenaAz")).thenReturn(new User(1L,"milenaAz","milena",true,true));
        when(chatroomPrivilegesService.findByRoomId(1L)).thenReturn(new ChatroomPrivileges(1L,"PUBLIC",false,null,"whitelist")); 
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/removeMember")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        chatroomMembersService.checkIfMemberExistsInChatroom(2L,2L);

        verify(userService,times(1)).checkUsername(anyString());
        verify(chatroomEntitesService,times(2)).getRoomByName(anyString());
        verify(userService,times(1)).getUserByUsername(anyString());
        verify(chatroomPrivilegesService,times(1)).findByRoomId(anyLong());
        verify(chatroomMembersService,times(2)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        
        Assert.assertEquals("failure - expected HTTP status to be '404'", 404, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_PasswordAuthorizationFail() throws Exception
    {
        json.put("room_name","first testing room");
        json.put("member_name","milenaAz");
        json.put("password","wrong_password");
        json.put("method","DELETE");
        
        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        when(userService.getUserByUsername("milenaAz")).thenReturn(new User(1L,"milenaAz","milena",true,true));
        when(chatroomPrivilegesService.findByRoomId(1L)).thenReturn(new ChatroomPrivileges(3L,"PUBLIC",true,"password","blacklist")); 
        when(chatroomMembersService.checkIfMemberExistsInChatroom(1L,1L)).thenReturn(true); 
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/removeMember")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername(anyString());
        verify(chatroomEntitesService,times(2)).getRoomByName(anyString());
        verify(userService,times(1)).getUserByUsername(anyString());
        verify(chatroomPrivilegesService,times(1)).findByRoomId(anyLong());
        verify(chatroomMembersService,times(1)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        
        Assert.assertEquals("failure - expected HTTP status to be '401'", 401, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.NOT_AUTHORIZED.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_PasswordAuthorizationOk() throws Exception
    {
        json.put("room_name","first testing room");
        json.put("member_name","milenaAz");
        json.put("password","password");
        json.put("method","DELETE");
        
        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        when(userService.getUserByUsername("milenaAz")).thenReturn(new User(1L,"milenaAz","milena",true,true));
        when(chatroomPrivilegesService.findByRoomId(1L)).thenReturn(new ChatroomPrivileges(3L,"PUBLIC",true,"password","blacklist")); 
        when(chatroomMembersService.checkIfMemberExistsInChatroom(1L,1L)).thenReturn(true); 
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/removeMember")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername(anyString());
        verify(chatroomEntitesService,times(2)).getRoomByName(anyString());
        verify(userService,times(1)).getUserByUsername(anyString());
        verify(chatroomPrivilegesService,times(1)).findByRoomId(anyLong());
        verify(chatroomMembersService,times(1)).checkIfMemberExistsInChatroom(anyLong(),anyLong());
        
        Assert.assertEquals("failure - expected HTTP status to be '200'", 200, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
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
