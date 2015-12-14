/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.chatroom.ChatroomBlacklist;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.chatroom.ChatroomMembers;
import com.tech.models.entities.chatroom.ChatroomPrivileges;
import com.tech.models.entities.chatroom.ChatroomWhitelist;
import com.tech.models.entities.user.User;
import com.tech.services.chatroom.ChatroomBlacklistService;
import com.tech.services.chatroom.ChatroomEntitiesService;
import com.tech.services.chatroom.ChatroomLocationService;
import com.tech.services.chatroom.ChatroomMembersService;
import com.tech.services.chatroom.ChatroomPrivilegesService;
import com.tech.services.chatroom.ChatroomWhitelistService;
import com.tech.services.user.UserService;
import java.sql.Date;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
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
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomWrongMethod() throws Exception{
        json.put("method","NOT");
        json.put("room_name","A");
        json.put("member_name","A");
        json.put("password","A");
        json.put("lat","1");
        json.put("lng","1");
        
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("failure not OK", 400, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.ACCESS_METHOD_NOT_FOUND.getData() + "'",
                    content.equals(Responses.ACCESS_METHOD_NOT_FOUND.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomWrongMemberName() throws Exception{
        json.put("method","ADD");
        json.put("member_name","wrongUserName");
        json.put("room_name","A");
        json.put("password","A");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("wrongUserName")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("wrongUserName");
        
        Assert.assertEquals("failure not OK", 404, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomWrongRoomName() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","WrongRoomName");
        json.put("password","A");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("WrongRoomName")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("WrongRoomName");
        
        Assert.assertEquals("failure not OK", 404, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroom() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","A");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("CorrectRoomName")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("CorrectRoomName")).thenReturn(new ChatroomEntities(4L, 4L, "CorrectRoomName"));
        when(userService.getUserByUsername("CorrectUserName")).thenReturn(new User());
        when(chatroomLocationService.checkIfStillInside(4L,1, 1)).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("CorrectRoomName");
        verify(chatroomLocationService,times(1)).checkIfStillInside(4L,1,1);
        
        Assert.assertEquals("failure not OK", 410, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.OUTSIDE_RANGE.getData() + "'",
                    content.equals(Responses.OUTSIDE_RANGE.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomNotPasswordInWhitelist() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","WrongPassword");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("CorrectRoomName")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("CorrectRoomName")).thenReturn(new ChatroomEntities(4L, 4L, "CorrectRoomName"));
        when(userService.getUserByUsername("CorrectUserName")).thenReturn(new User(4L,"CorrectUserName","WrongPassword",true));
        when(chatroomLocationService.checkIfStillInside(4L,1, 1)).thenReturn(true);
        when(chatroomPrivilegesService.findByRoomId(4L)).thenReturn(new ChatroomPrivileges(4L,"public",false,"WrongPassword","whitelist"));
       
        
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("CorrectRoomName");
        verify(chatroomLocationService,times(1)).checkIfStillInside(4L,1,1);
        verify(chatroomPrivilegesService,times(1)).findByRoomId(4L);
        
        Assert.assertEquals("failure not OK", 401, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
                    content.equals(Responses.NOT_AUTHORIZED.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomWrongPasswordInBlacklist() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","WrongPassword");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("CorrectRoomName")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("CorrectRoomName")).thenReturn(new ChatroomEntities(4L, 4L, "CorrectRoomName"));
        when(userService.getUserByUsername("CorrectUserName")).thenReturn(new User(4L,"CorrectUserName","WrongPassword",true));
        when(chatroomLocationService.checkIfStillInside(4L,1, 1)).thenReturn(true);
        when(chatroomPrivilegesService.findByRoomId(4L)).thenReturn(new ChatroomPrivileges(4L,"public",true,"CorrectPassword","blacklist"));
       
        
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("CorrectRoomName");
        verify(chatroomLocationService,times(1)).checkIfStillInside(4L,1,1);
        verify(chatroomPrivilegesService,times(1)).findByRoomId(4L);
        
        Assert.assertEquals("failure not OK", 401, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
                    content.equals(Responses.NOT_AUTHORIZED.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomCorrectPasswordInBlacklist() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","CorrectPassword");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("CorrectRoomName")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("CorrectRoomName")).thenReturn(new ChatroomEntities(4L, 4L, "CorrectRoomName"));
        when(userService.getUserByUsername("CorrectUserName")).thenReturn(new User(4L,"CorrectUserName","WrongPassword",true));
        when(chatroomLocationService.checkIfStillInside(4L,1, 1)).thenReturn(true);
        when(chatroomPrivilegesService.findByRoomId(4L)).thenReturn(new ChatroomPrivileges(4L,"public",true,"CorrectPassword","blacklist"));
        when(chatroomBlacklistService.findByRoomIDAndRoomMember(4L,4L)).thenReturn(new ChatroomBlacklist(4L,4L,Date.valueOf("2100-12-28")));
        
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("CorrectRoomName");
        verify(chatroomLocationService,times(1)).checkIfStillInside(4L,1,1);
        verify(chatroomPrivilegesService,times(1)).findByRoomId(4L);
        verify(chatroomBlacklistService,times(1)).findByRoomIDAndRoomMember(4L,4L);
        
        Assert.assertEquals("failure not OK", 401, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
                    content.equals(Responses.NOT_AUTHORIZED.getData()));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomDeleteFromBlacklist() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","CorrectPassword");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("CorrectRoomName")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("CorrectRoomName")).thenReturn(new ChatroomEntities(4L, 4L, "CorrectRoomName"));
        when(userService.getUserByUsername("CorrectUserName")).thenReturn(new User(4L,"CorrectUserName","WrongPassword",true));
        when(chatroomLocationService.checkIfStillInside(4L,1, 1)).thenReturn(true);
        when(chatroomPrivilegesService.findByRoomId(4L)).thenReturn(new ChatroomPrivileges(4L,"public",true,"CorrectPassword","blacklist"));
        when(chatroomBlacklistService.findByRoomIDAndRoomMember(4L,4L)).thenReturn(new ChatroomBlacklist(4L,4L,Date.valueOf("2000-12-28")));
        when(chatroomBlacklistService.delete(any (ChatroomBlacklist.class))).thenReturn(true);
        
        doNothing().when(chatroomMembersService).add(any(ChatroomMembers.class));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("CorrectRoomName");
        verify(chatroomLocationService,times(1)).checkIfStillInside(4L,1,1);
        verify(chatroomPrivilegesService,times(1)).findByRoomId(4L);
        verify(chatroomBlacklistService,times(1)).findByRoomIDAndRoomMember(4L,4L);
        verify(chatroomBlacklistService,times(1)).delete(any (ChatroomBlacklist.class));
        verify(chatroomMembersService,times(1)).add(any(ChatroomMembers.class));
       
        
        Assert.assertEquals("failure not OK", 200, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomCBNullFromBlacklist() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","CorrectPassword");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("CorrectRoomName")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("CorrectRoomName")).thenReturn(new ChatroomEntities(4L, 4L, "CorrectRoomName"));
        when(userService.getUserByUsername("CorrectUserName")).thenReturn(new User(4L,"CorrectUserName","WrongPassword",true));
        when(chatroomLocationService.checkIfStillInside(4L,1, 1)).thenReturn(true);
        when(chatroomPrivilegesService.findByRoomId(4L)).thenReturn(new ChatroomPrivileges(4L,"public",true,"CorrectPassword","blacklist"));
        when(chatroomBlacklistService.findByRoomIDAndRoomMember(4L,4L)).thenReturn(null);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("CorrectRoomName");
        verify(chatroomLocationService,times(1)).checkIfStillInside(4L,1,1);
        verify(chatroomPrivilegesService,times(1)).findByRoomId(4L);
        verify(chatroomBlacklistService,times(1)).findByRoomIDAndRoomMember(4L,4L);
        
       
        
        Assert.assertEquals("failure not OK", 200, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToCWChatroomWhitelist() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","WrongPassword");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("CorrectRoomName")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("CorrectRoomName")).thenReturn(new ChatroomEntities(4L, 4L, "CorrectRoomName"));
        when(userService.getUserByUsername("CorrectUserName")).thenReturn(new User(4L,"CorrectUserName","WrongPassword",true));
        when(chatroomLocationService.checkIfStillInside(4L,1, 1)).thenReturn(true);
        when(chatroomPrivilegesService.findByRoomId(4L)).thenReturn(new ChatroomPrivileges(4L,"public",false,"CorrectPassword","whitelist"));
        when(chatroomWhitelistService.findByRoomIDAndRoomMember(4L,4L)).thenReturn(new ChatroomWhitelist(4L,4L));
        
        doNothing().when(chatroomMembersService).add(any(ChatroomMembers.class));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("CorrectRoomName");
        verify(chatroomLocationService,times(1)).checkIfStillInside(4L,1,1);
        verify(chatroomPrivilegesService,times(1)).findByRoomId(4L);
        verify(chatroomWhitelistService,times(1)).findByRoomIDAndRoomMember(4L,4L);
        verify(chatroomMembersService,times(1)).add(any(ChatroomMembers.class));
        
        Assert.assertEquals("failure not OK", 200, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToCWNullChatroomWhitelist() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","WrongPassword");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("CorrectRoomName")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("CorrectRoomName")).thenReturn(new ChatroomEntities(4L, 4L, "CorrectRoomName"));
        when(userService.getUserByUsername("CorrectUserName")).thenReturn(new User(4L,"CorrectUserName","WrongPassword",true));
        when(chatroomLocationService.checkIfStillInside(4L,1, 1)).thenReturn(true);
        when(chatroomPrivilegesService.findByRoomId(4L)).thenReturn(new ChatroomPrivileges(4L,"public",false,"CorrectPassword","whitelist"));
        when(chatroomWhitelistService.findByRoomIDAndRoomMember(4L,4L)).thenReturn(null);
        
        doNothing().when(chatroomMembersService).add(any(ChatroomMembers.class));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("CorrectRoomName");
        verify(chatroomLocationService,times(1)).checkIfStillInside(4L,1,1);
        verify(chatroomPrivilegesService,times(1)).findByRoomId(4L);
        verify(chatroomWhitelistService,times(1)).findByRoomIDAndRoomMember(4L,4L);
        verify(chatroomMembersService,times(0)).add(any(ChatroomMembers.class));
        
        Assert.assertEquals("failure not OK", 401, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
                    content.equals(Responses.NOT_AUTHORIZED.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToNOCP() throws Exception{
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","WrongPassword");
        json.put("lat","1");
        json.put("lng","1");
        
        when(userService.checkUsername("CorrectUserName")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("CorrectRoomName")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("CorrectRoomName")).thenReturn(new ChatroomEntities(4L, 4L, "CorrectRoomName"));
        when(userService.getUserByUsername("CorrectUserName")).thenReturn(new User(4L,"CorrectUserName","WrongPassword",true));
        when(chatroomLocationService.checkIfStillInside(4L,1, 1)).thenReturn(true);
        when(chatroomPrivilegesService.findByRoomId(4L)).thenReturn(new ChatroomPrivileges(4L,"public",false,"CorrectPassword","NOCP"));
             
          
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("CorrectUserName");
        verify(chatroomEntitesService,times(1)).validateRoomnameExistance("CorrectRoomName");
        verify(chatroomLocationService,times(1)).checkIfStillInside(4L,1,1);
        verify(chatroomPrivilegesService,times(1)).findByRoomId(4L);
        verify(chatroomWhitelistService,times(0)).findByRoomIDAndRoomMember(4L,4L);
        verify(chatroomMembersService,times(0)).add(any(ChatroomMembers.class));
        
        Assert.assertEquals("failure not OK", 400, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.ACCESS_METHOD_NOT_FOUND.getData() + "'",
                    content.equals(Responses.ACCESS_METHOD_NOT_FOUND.getData()));
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
    public void testAvailableChatrooms() {
    }

    @Test
    public void testUpdateLocation() {
    }

    @Test
    public void testCheckIfStillInside() {
    }
    
}
