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
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    public void testQuitChatroom() {
    }

    @Test
    public void testAvailableChatrooms() {
    }

    @Test
    public void testUpdateLocation() {
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfStillInside() throws Exception {
        json.put("lng" , 23.7629689);
        json.put("lat" , 37.9837928);
        json.put("room_name" , "testRoom");
        json.put("user_name" , "testUser");

        when(userService.checkUsername("testUser")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("testRoom")).thenReturn(true);
        when(userService.getUserByUsername("testUser")).thenReturn(new User(1L, "testUser", "123", true, false));
        when(chatroomEntitesService.getRoomByName("testRoom")).thenReturn(new ChatroomEntities(1L, 2L, "testRoom"));
        when(chatroomLocationService.checkIfStillInside(1L,23.7629689F,37.9837928F)).thenReturn(true);
        when(chatroomMembersService.checkIfMemberExistsInChatroom(1L, 1L)).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkIfStillInside")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        Assert.assertEquals(Responses.SUCCESS.getData(),content);
        Assert.assertEquals(200,status);
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfStillInsideNotConnected() throws Exception {
        json.put("lng" , 23.7629689);
        json.put("lat" , 37.9837928);
        json.put("room_name" , "testRoom");
        json.put("user_name" , "testUser");

        when(userService.checkUsername("testUser")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("testRoom")).thenReturn(true);
        when(userService.getUserByUsername("testUser")).thenReturn(new User(1L, "testUser", "123", true, false));
        when(chatroomEntitesService.getRoomByName("testRoom")).thenReturn(new ChatroomEntities(1L, 2L, "testRoom"));
        when(chatroomLocationService.checkIfStillInside(1L,23.7629689F,37.9837928F)).thenReturn(true);
        when(chatroomMembersService.checkIfMemberExistsInChatroom(1L, 1L)).thenReturn(false);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkIfStillInside")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        Assert.assertEquals(Responses.NOT_CONNECTED_TO_THE_ROOM.getData(),content);
        Assert.assertEquals(401,status);
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfStillInsideOutside() throws Exception {
        json.put("lng" , 23.7629689);
        json.put("lat" , 37.9837928);
        json.put("room_name" , "testRoom");
        json.put("user_name" , "testUser");

        when(userService.checkUsername("testUser")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("testRoom")).thenReturn(true);
        when(userService.getUserByUsername("testUser")).thenReturn(new User(1L, "testUser", "123", true, false));
        when(chatroomEntitesService.getRoomByName("testRoom")).thenReturn(new ChatroomEntities(1L, 2L, "testRoom"));
        when(chatroomLocationService.checkIfStillInside(1L,23.7629689F,37.9837928F)).thenReturn(false);
        when(chatroomMembersService.checkIfMemberExistsInChatroom(1L, 1L)).thenReturn(false);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkIfStillInside")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        Assert.assertEquals(Responses.OUTSIDE_RANGE.getData(),content);
        Assert.assertEquals(410,status);
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfStillInsideOutside2() throws Exception {
        json.put("lng" , 23.7629689);
        json.put("lat" , 37.9837928);
        json.put("room_name" , "testRoom");
        json.put("user_name" , "testUser");

        when(userService.checkUsername("testUser")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("testRoom")).thenReturn(true);
        when(userService.getUserByUsername("testUser")).thenReturn(new User(1L, "testUser", "123", true, false));
        when(chatroomEntitesService.getRoomByName("testRoom")).thenReturn(new ChatroomEntities(1L, 2L, "testRoom"));
        when(chatroomLocationService.checkIfStillInside(1L,23.7629689F,37.9837928F)).thenReturn(false);
        when(chatroomMembersService.checkIfMemberExistsInChatroom(1L, 1L)).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkIfStillInside")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        verify(chatroomMembersService, times(1)).delete(any(ChatroomMembers.class));

        Assert.assertEquals(Responses.OUTSIDE_RANGE.getData(),content);
        Assert.assertEquals(410,status);
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfStillInsideNoRoom() throws Exception {
        json.put("lng" , 23.7629689);
        json.put("lat" , 37.9837928);
        json.put("room_name" , "testRoom");
        json.put("user_name" , "testUser");

        when(userService.checkUsername("testUser")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("testRoom")).thenReturn(false);


        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkIfStillInside")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);


        Assert.assertEquals(Responses.NOT_AVAILABLE.getData(),content);
        Assert.assertEquals(404,status);
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfStillInsideNoName() throws Exception {
        json.put("lng" , 23.7629689);
        json.put("lat" , 37.9837928);
        json.put("room_name" , "testRoom");
        json.put("user_name" , "testUser");

        when(userService.checkUsername("testUser")).thenReturn(false);


        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkIfStillInside")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);


        Assert.assertEquals(Responses.NOT_AVAILABLE.getData(),content);
        Assert.assertEquals(404,status);
    }
}
