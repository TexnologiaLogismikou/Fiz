/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.dtos.chatroom.ChatroomCreationDTO;
import com.tech.models.entities.user.User;
import com.tech.services.chatroom.ChatroomBlacklistService;
import com.tech.services.chatroom.ChatroomEntitiesService;
import com.tech.services.chatroom.ChatroomLocationService;
import com.tech.services.chatroom.ChatroomMembersService;
import com.tech.services.chatroom.ChatroomPrivilegesService;
import com.tech.services.chatroom.ChatroomWhitelistService;
import com.tech.services.user.UserService;
import javax.transaction.Transactional;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
    public void testHandleNewChatroomInvalidDTO() throws Exception {
        //TODO invalid DTO after validator is done
    }

    @Test
    public void testHandleNewChatroomUsernameDoesNotExist() throws Exception {

        String uri = this.uri+"/newChatroom";
        json.put("username","user");
        json.put("room_name","room");
        json.put("room_privilege","all");
        json.put("access_method","abc");
        json.put("room_lat",200.5);
        json.put("room_lng",100.5);
        json.put("room_max_range",50);
        json.put("hasPassword",false);
        json.put("password","");
        when(userService.checkUsername("abc")).thenReturn(false);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals(Responses.NOT_AVAILABLE.getData(),content);
        Assert.assertEquals(404,status);
    }

    @Test
    public void testHandleNewChatroomRoomNameExistsExist() throws Exception {

        String uri = this.uri+"/newChatroom";
        json.put("username","user");
        json.put("room_name","room");
        json.put("room_privilege","all");
        json.put("access_method","abc");
        json.put("room_lat",200.5);
        json.put("room_lng",100.5);
        json.put("room_max_range",50);
        json.put("hasPassword",false);
        json.put("password","");

        when(userService.checkUsername((String)json.get("username"))).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance((String)json.get("room_name"))).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals(Responses.NOT_AVAILABLE.getData(),content);
        Assert.assertEquals(302,status);
    }

    @Test
    public void testHandleNewChatroomUserHasRoomAlready() throws Exception {

        String uri = this.uri+"/newChatroom";
        json.put("username","user");
        json.put("room_name","room");
        json.put("room_privilege","all");
        json.put("access_method","abc");
        json.put("room_lat",200.5);
        json.put("room_lng",100.5);
        json.put("room_max_range",50);
        json.put("hasPassword",false);
        json.put("password","");

        User user = new User(1L,(String)json.get("username"),"123",true,true);
        when(userService.checkUsername((String)json.get("username"))).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance((String)json.get("room_name"))).thenReturn(false);
        when(userService.getUserByUsername((String)json.get("username"))).thenReturn(user);
        when(userService.getUserById(1L)).thenReturn(user);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals(Responses.ALREADY_HAS_A_ROOM.getData(),content);
        Assert.assertEquals(302,status);
    }

    @Test
    public void testHandleNewChatroomSuccess() throws Exception {

        String uri = this.uri+"/newChatroom";
        json.put("username","user");
        json.put("room_name","room");
        json.put("room_privilege","all");
        json.put("access_method","abc");
        json.put("room_lat",200.5);
        json.put("room_lng",100.5);
        json.put("room_max_range",50);
        json.put("hasPassword",false);
        json.put("password","");

        User user = new User(1L,(String)json.get("username"),"123",true,false);
        when(userService.checkUsername((String)json.get("username"))).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance((String)json.get("room_name"))).thenReturn(false);
        when(userService.getUserByUsername((String)json.get("username"))).thenReturn(user);
        when(userService.getUserById(1L)).thenReturn(user);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals(Responses.SUCCESS.getData(),content);
        Assert.assertEquals(200,status);
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
    public void testAvailableChatrooms() {
    }

    @Test
    public void testUpdateLocation() {
    }

    @Test
    public void testCheckIfStillInside() {
    }
    
}
