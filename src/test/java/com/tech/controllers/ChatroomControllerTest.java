/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.models.dtos.chatroom.ChatroomWhitelistDTO;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.chatroom.ChatroomWhitelist;
import com.tech.models.entities.user.User;
import com.tech.services.chatroom.ChatroomBlacklistService;
import com.tech.services.chatroom.ChatroomEntitiesService;
import com.tech.services.chatroom.ChatroomLocationService;
import com.tech.services.chatroom.ChatroomMembersService;
import com.tech.services.chatroom.ChatroomPrivilegesService;
import com.tech.services.chatroom.ChatroomWhitelistService;
import com.tech.services.user.UserService;
import com.tech.configurations.tools.Responses;

import javax.transaction.Transactional;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;


/**
 * @author KuroiTenshi
 */
@Transactional
@ActiveProfiles({"mixalis", "mixalis"})
public class ChatroomControllerTest extends AbstractControllerTest {
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
    public void testHandleWhitelistNoName() throws Exception {
        String uri = this.uri + "/handleWhitelist";
        json.put("room_name", "Room");
        json.put("member_name", "Member");
        json.put("mode", "ADD");

        when(userService.checkUsername("Member")).thenReturn(false);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(userService, times(1)).checkUsername(anyString());


        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals(Responses.NOT_AVAILABLE.getData(), content);
        Assert.assertEquals(404, status);
    }

    @Test
    public void testHandleWhitelistNoRoom() throws Exception {

        String uri = this.uri + "/handleWhitelist";
        json.put("room_name", "Room");
        json.put("member_name", "Member");
        json.put("mode", "ADD");

        when(userService.checkUsername("Member")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("IsARoom")).thenReturn(false);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();


        System.out.println(result.getResponse().getContentAsString());

        verify(userService, times(1)).checkUsername("Member");
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance(anyString());

        System.out.println(result.getResponse().getContentAsString());

        Assert.assertEquals(Responses.ROOM_NOT_FOUND.getData(), content);
        Assert.assertEquals(404, status);

    }

    @Test
    public void testHandleWhitelistAddFailure() throws Exception {

        String uri = this.uri + "/handleWhitelist";
        json.put("room_name", "Room");
        json.put("member_name", "Member");
        json.put("mode", "ADD");

        when(userService.checkUsername("Member")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("Room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("Room")).thenReturn(new ChatroomEntities(1L, 1L, "Room"));
        when(userService.getUserByUsername("Member")).thenReturn(new User(1L,"george","george",true,false));

        when(chatroomWhitelistService.findByRoomIDAndRoomMember(1L,1L)).thenReturn(new ChatroomWhitelist(1L,1L));


        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();


        verify(userService, times(1)).checkUsername("Member");
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("Room");
        verify(chatroomEntitesService, times(1)).getRoomByName(anyString());
        verify(userService, times(1)).getUserByUsername(anyString());
        verify(chatroomWhitelistService, times(1)).findByRoomIDAndRoomMember(anyLong(), anyLong());

        Assert.assertEquals(Responses.ALREADY_EXISTS.getData(), content);
        Assert.assertEquals(302 , status);
    }

    @Test
    public void testHandleWhitelistDeleteSuccess() throws Exception {

        String uri = this.uri + "/handleWhitelist";
        json.put("room_name", "Room");
        json.put("member_name", "Member");
        json.put("mode", "DELETE");

        when(userService.checkUsername("Member")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("Room")).thenReturn(true);

        when(chatroomEntitesService.getRoomByName("Room")).thenReturn(new ChatroomEntities(1L, 1L, "Room"));
        when(userService.getUserByUsername("Member")).thenReturn(new User(1L,"george","george",true,false));

        when(chatroomWhitelistService.findByRoomIDAndRoomMember(1L,1L)).thenReturn(new ChatroomWhitelist(1L,1L));
        when(chatroomWhitelistService.delete(new ChatroomWhitelist(1L,1L))).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();


        verify(userService, times(1)).checkUsername("Member");
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("Room");
        verify(chatroomEntitesService, times(1)).getRoomByName(anyString());
        verify(userService, times(1)).getUserByUsername(anyString());
        verify(chatroomWhitelistService, times(1)).findByRoomIDAndRoomMember(anyLong(), anyLong());
        verify(chatroomWhitelistService, times(1)).delete(any(ChatroomWhitelist.class));

        Assert.assertEquals(Responses.SUCCESS.getData(), content);
        Assert.assertEquals(200 , status);
    }

    @Test
    public void testHandleWhitelistAddSuccess() throws Exception {
        String uri = this.uri + "/handleWhitelist";
        json.put("room_name", "Room");
        json.put("member_name", "Member");
        json.put("mode", "ADD");

        when(userService.checkUsername("Member")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("Room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("Room")).thenReturn(new ChatroomEntities(1L, 1L, "Room"));
        when(userService.getUserByUsername("Member")).thenReturn(new User(1L,"george","george",true,false));

        when(chatroomWhitelistService.findByRoomIDAndRoomMember(1L,1L)).thenReturn(null);


        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();


        verify(userService, times(1)).checkUsername("Member");
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("Room");
        verify(chatroomEntitesService, times(1)).getRoomByName(anyString());
        verify(userService, times(1)).getUserByUsername(anyString());
        verify(chatroomWhitelistService, times(1)).findByRoomIDAndRoomMember(anyLong(), anyLong());

        Assert.assertEquals(Responses.SUCCESS.getData(), content);
        Assert.assertEquals(200 , status);

    }


    @Test
    public void testHandleWhitelistDeleteFailure() throws Exception {
        String uri = this.uri + "/handleWhitelist";
        json.put("room_name", "Room");
        json.put("member_name", "Member");
        json.put("mode", "DELETE");

        when(userService.checkUsername("Member")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("Room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("Room")).thenReturn(new ChatroomEntities(1L, 1L, "Room"));
        when(userService.getUserByUsername("Member")).thenReturn(new User(1L,"george","george",true,false));

        when(chatroomWhitelistService.findByRoomIDAndRoomMember(1L,1L)).thenReturn(null);


        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();


        verify(userService, times(1)).checkUsername("Member");
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("Room");
        verify(chatroomEntitesService, times(1)).getRoomByName(anyString());
        verify(userService, times(1)).getUserByUsername(anyString());
        verify(chatroomWhitelistService, times(1)).findByRoomIDAndRoomMember(anyLong(), anyLong());

        Assert.assertEquals(Responses.NOT_AVAILABLE.getData(), content);
        Assert.assertEquals(404 , status);
    }

    @Test
    public void testHandleWhitelistBadMethod() throws Exception {
        String uri = this.uri + "/handleWhitelist";
        json.put("room_name", "Room");
        json.put("member_name", "Member");
        json.put("mode", "TAKIS");

        when(userService.checkUsername("Member")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("Room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("Room")).thenReturn(new ChatroomEntities(1L, 1L, "Room"));
        when(userService.getUserByUsername("Member")).thenReturn(new User(1L,"george","george",true,false));



        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();


        verify(userService, times(1)).checkUsername("Member");
        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("Room");
        verify(chatroomEntitesService, times(1)).getRoomByName(anyString());
        verify(userService, times(1)).getUserByUsername(anyString());

        Assert.assertEquals(Responses.ACCESS_METHOD_NOT_FOUND.getData(), content);
        Assert.assertEquals(400 , status);
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
