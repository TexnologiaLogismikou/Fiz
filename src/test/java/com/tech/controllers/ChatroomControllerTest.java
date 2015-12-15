/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.dtos.chatroom.ChatroomBlacklistDTO;
import com.tech.models.dtos.chatroom.ChatroomCreationDTO;
import com.tech.models.entities.Friend;
import com.tech.models.entities.chatroom.ChatroomBlacklist;
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

import org.hibernate.annotations.SourceType;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Date;

import static org.mockito.Matchers.any;
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
    public void testHandleBansUserDoesNotExist() throws Exception {
        String uri = this.uri+"/banFromChatroom";
        Date date = new Date();
        date.setTime(date.getTime()+3600000); //1 hour
        json.put("room_name","room");
        json.put("member_name","user");
        json.put("expiration_date",date.getTime()); //string constructor for Java.util.Date is deprecated
        when(userService.checkUsername("user")).thenReturn(false);

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
    public void testHandleBansRoomDoesNotExist() throws Exception {
        String uri = this.uri+"/banFromChatroom";
        Date date = new Date();
        date.setTime(date.getTime()+3600000);
        json.put("room_name","room");
        json.put("member_name","user");
        json.put("expiration_date",date.getTime());
        when(userService.checkUsername("user")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("room")).thenReturn(false);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals(Responses.ROOM_NOT_FOUND.getData(),content);
        Assert.assertEquals(404,status);
    }

    @Test
    public void testHandleBansBanExpired() throws Exception {
        String uri = this.uri+"/banFromChatroom";
        Date date = new Date();
        date.setTime(date.getTime()-3600000);
        json.put("room_name","room");
        json.put("member_name","user");
        json.put("expiration_date",date.getTime());
        when(userService.checkUsername("user")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("room")).thenReturn(new ChatroomEntities(10L,1L,"room"));
        when(userService.getUserByUsername("user")).thenReturn(new User(1L,"user","123",true,true));
        when(chatroomBlacklistService.findByRoomIDAndRoomMember(10L,1L)).thenReturn(new ChatroomBlacklist(10L,1L,date)); //expired date

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
    public void testHandleBansNewBanDate() throws Exception {
        String uri = this.uri+"/banFromChatroom";
        Date date = new Date(); //new ban date
        date.setTime(date.getTime()+3600000);
        json.put("room_name","room");
        json.put("member_name","user");
        json.put("expiration_date",date.getTime());
        when(userService.checkUsername("user")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("room")).thenReturn(new ChatroomEntities(10L,1L,"room"));
        when(userService.getUserByUsername("user")).thenReturn(new User(1L,"user","123",true,true));
        when(chatroomBlacklistService.findByRoomIDAndRoomMember(10L,1L)).thenReturn(new ChatroomBlacklist(10L,1L,new Date())); //current ban's date

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
    public void testHandleBansBanUserSuccess() throws Exception {
        String uri = this.uri+"/banFromChatroom";
        Date date = new Date(); //new ban date
        date.setTime(date.getTime()+3600000);
        json.put("room_name","room");
        json.put("member_name","user");
        json.put("expiration_date",date.getTime());
        when(userService.checkUsername("user")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("room")).thenReturn(new ChatroomEntities(10L,1L,"room"));
        when(userService.getUserByUsername("user")).thenReturn(new User(1L,"user","123",true,true));
        when(chatroomBlacklistService.findByRoomIDAndRoomMember(10L,1L)).thenReturn(null); //current ban's date
        doNothing().when(chatroomBlacklistService).add(any(ChatroomBlacklist.class));

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
