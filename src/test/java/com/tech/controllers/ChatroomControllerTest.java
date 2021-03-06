/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LatitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotMatchValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.responses.AvailableChatroomResponseDTO;
import com.tech.models.dtos.chatroom.ChatroomBlacklistDTO;
import com.tech.models.dtos.chatroom.ChatroomCheckInsideDTO;
import com.tech.models.dtos.chatroom.ChatroomCreationDTO;
import com.tech.models.dtos.chatroom.ChatroomDeleteDTO;
import com.tech.models.dtos.chatroom.ChatroomLocationDTO;
import com.tech.models.dtos.chatroom.ChatroomLocationUpdateDTO;
import com.tech.models.dtos.chatroom.ChatroomMemberDTO;
import com.tech.models.dtos.chatroom.ChatroomQuitMemberDTO;
import com.tech.models.dtos.chatroom.ChatroomUpdateDTO;
import com.tech.models.dtos.chatroom.ChatroomWhitelistDTO;
import com.tech.models.entities.chatroom.*;
import com.tech.models.entities.user.User;
import com.tech.services.chatroom.*;
import com.tech.services.user.UserService;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

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
    public static void setUpClass()
    {
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass()
    {     
        InitializeValidators.InitializeCustomValidators();
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
    public void testHandleNewChatroomUsernameDoesNotExist() throws Exception {

        String uri = this.uri+"/newChatroom";
        
        json.put("username","user");
        json.put("room_name","room");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat",80.5);
        json.put("room_lng",60.5);
        json.put("room_max_range",50);
        json.put("hasPassword",false);
        json.put("password","nopass");
        
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
    public void testHandleNewChatroomRoomNameExistsExist() throws Exception {

        String uri = this.uri+"/newChatroom";
        
        json.put("username","user");
        json.put("room_name","room");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat",80.5);
        json.put("room_lng",60.5);
        json.put("room_max_range",50);
        json.put("hasPassword",false);
        json.put("password","nopass");

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
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat",80.5);
        json.put("room_lng",60.5);
        json.put("room_max_range",50);
        json.put("hasPassword",false);
        json.put("password","nopass");
        
        User user = new User(1L,(String)json.get("username"),"12345",true,true);
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
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat",80.5);
        json.put("room_lng",60.5);
        json.put("room_max_range",50);
        json.put("hasPassword",false);
        json.put("password","nopass");

        User user = new User(1L,(String)json.get("username"),"12345",true,false);
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomWrongMethod() throws Exception{
        json.put("mode","DELETE");
        json.put("room_name","Aaaaaa");
        json.put("member_name","Aaaaa");
        json.put("password","Aaaaaa");
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
    ////@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomWrongMemberName() throws Exception{
        json.put("mode","ADD");
        json.put("member_name","wrongUserName");
        json.put("room_name","Aaaaa");
        json.put("password","Aaaaaa");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomWrongRoomName() throws Exception{
        json.put("mode","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","WrongRoomName");
        json.put("password","Aaaaaa");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroom() throws Exception{
        json.put("mode","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","CorrectRoomName");
        json.put("password","Aaaaaa");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomNotPasswordInWhitelist() throws Exception{
        json.put("mode","ADD");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomWrongPasswordInBlacklist() throws Exception{
        json.put("mode","ADD");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomCorrectPasswordInBlacklist() throws Exception{
        json.put("mode","ADD");
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
        when(chatroomBlacklistService.findByRoomIDAndRoomMember(4L,4L)).thenReturn(new ChatroomBlacklist(4L,4L,java.sql.Date.valueOf("2100-12-28")));


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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomDeleteFromBlacklist() throws Exception{
        json.put("mode","ADD");
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
        when(chatroomBlacklistService.findByRoomIDAndRoomMember(4L,4L)).thenReturn(new ChatroomBlacklist(4L,4L,java.sql.Date.valueOf("2000-12-28")));
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroomCBNullFromBlacklist() throws Exception{
        json.put("mode","ADD");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToCWChatroomWhitelist() throws Exception{
        json.put("mode","ADD");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToCWNullChatroomWhitelist() throws Exception{ // Changed
        json.put("mode","ADD");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToNOCP() throws Exception{
        json.put("mode","ADD");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomNotExist() throws Exception
    {
        json.put("creator_name", "milena");
        json.put("room_name", "techa");
        json.put("room_password", "12345");

        when(chatroomEntitesService.validateRoomnameExistance("techa")).thenReturn(false);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deleteChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("techa");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomMemberDoesntHaveRoom() throws Exception
    {
        json.put("creator_name", "vasilis");
        json.put("room_name", "hi");
        json.put("room_password", "nopass");

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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomWrongCreator() throws Exception
    {
        json.put("creator_name", "iwannaFot");
        json.put("room_name", "first_testing_room");
        json.put("room_password", "");

        when(chatroomEntitesService.validateRoomnameExistance("first_testing_room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
        when(userService.getUserByUsername("iwannaFot")).thenReturn(new User(2L,"iwannaFot","iwanna",true,true));
        when(userService.getUserById(2L)).thenReturn(new User(2L,"iwannaFot","iwanna",true,true));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deleteChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first_testing_room");
        verify(chatroomEntitesService, times(1)).getRoomByName("first_testing_room");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomWrongPassword() throws Exception
    {
        json.put("creator_name", "mixalisMix");
        json.put("room_name", "third_testing_room");
        json.put("room_password", "xaxa");

        when(chatroomEntitesService.validateRoomnameExistance("third_testing_room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("third_testing_room")).thenReturn(new ChatroomEntities(3L,3L,"third_testing_room"));
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

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("third_testing_room");
        verify(chatroomEntitesService, times(1)).getRoomByName("third_testing_room");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroom() throws Exception
    {
        json.put("creator_name", "mixalisMix");
        json.put("room_name", "third_testing_room");
        json.put("room_password", "password");

        ChatroomEntities CE = new ChatroomEntities(3L,3L,"third_testing_room");

        when(chatroomEntitesService.validateRoomnameExistance("third_testing_room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("third_testing_room")).thenReturn(CE);
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

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("third_testing_room");
        verify(chatroomEntitesService, times(1)).getRoomByName("third_testing_room");
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
    public void testHandleBansUserDoesNotExist() throws Exception {
        String uri = this.uri+"/banFromChatroom";
        Date date = new Date();
        date.setTime(date.getTime()+3600000); //1 hour
        json.put("room_name","rooom");
        json.put("member_name","userr");
        json.put("expiration_date",date.getTime()); //string constructor for Java.util.Date is deprecated
        when(userService.checkUsername("userr")).thenReturn(false);

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
        json.put("room_name","rooom");
        json.put("member_name","userr");
        json.put("expiration_date",date.getTime());
        when(userService.checkUsername("userr")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("rooom")).thenReturn(false);

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
        json.put("room_name","rooom");
        json.put("member_name","userr");
        json.put("expiration_date",date.getTime());
        when(userService.checkUsername("userr")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("rooom")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("rooom")).thenReturn(new ChatroomEntities(10L,1L,"rooom"));
        when(userService.getUserByUsername("userr")).thenReturn(new User(1L,"userr","12345",true,true));
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
        json.put("room_name","rooom");
        json.put("member_name","userr");
        json.put("expiration_date",date.getTime());
        when(userService.checkUsername("userr")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("rooom")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("rooom")).thenReturn(new ChatroomEntities(10L,1L,"rooom"));
        when(userService.getUserByUsername("userr")).thenReturn(new User(1L,"userr","12345",true,true));
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
        
        json.put("room_name","rooom");
        json.put("member_name","userr");
        json.put("expiration_date",date.getTime());
        
        when(userService.checkUsername("userr")).thenReturn(true);
        when(chatroomEntitesService.validateRoomnameExistance("rooom")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("rooom")).thenReturn(new ChatroomEntities(10L,1L,"rooom"));
        when(userService.getUserByUsername("userr")).thenReturn(new User(1L,"userr","12345",true,true));
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
    public void testHandleWhitelistNoName() throws Exception {
        String uri = this.uri + "/handleWhitelist";
        json.put("room_name", "Rooom");
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_Ok() throws Exception
    {
        json.put("room_name","first_testing_room");
        json.put("member_name","milenaAz");
        json.put("password","");
        json.put("method","DELETE");

        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomMemberDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }
        
        json.put("room_name","@first_testing_room");
        json.put("member_name","milenaAz");
        json.put("password","");
        json.put("method","ADD"); // 'ADD' Method is WRONG

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/removeMember")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        ChatroomMemberDTO.cleanValidator();
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_WrongMethod() throws Exception
    {
        json.put("room_name","first_testing_room");
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

        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.ACCESS_METHOD_NOT_FOUND.getData() + "'",
                    content.equals(Responses.ACCESS_METHOD_NOT_FOUND.getData()));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_UnavailableUsername() throws Exception
    {
        json.put("room_name","first_testing_room");
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

        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
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

        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_MemberDoesNotExistInChatroom() throws Exception
    {
        json.put("room_name","first_testing_room");
        json.put("member_name","milenaAz");
        json.put("password","");
        json.put("method","DELETE");

        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
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

        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_PasswordAuthorizationFail() throws Exception
    {
        json.put("room_name","first_testing_room");
        json.put("member_name","milenaAz");
        json.put("password","wrong_password");
        json.put("method","DELETE");

        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
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

        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
                    content.equals(Responses.NOT_AUTHORIZED.getData()));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testRemoveMember_PasswordAuthorizationOk() throws Exception
    {
        json.put("room_name","first_testing_room");
        json.put("member_name","milenaAz");
        json.put("password","password");
        json.put("method","DELETE");

        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testUpdateChatroomRoomNotExist() throws Exception
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testUpdateChatroom() throws Exception
    {
        json.put("room_name", "first_testing_room");
        json.put("new_room_name", "hi");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "whitelist");
        json.put("passwordProtection",true);
        json.put("password", "123");
        json.put("max_range", "5");

        when(chatroomEntitesService.validateRoomnameExistance("first_testing_room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
        doNothing().when(chatroomEntitesService).setChatroomEntities("hi",1L);
        doNothing().when(chatroomPrivilegesService).setChatroomPrivileges("PUBLIC",true,"123","whitelist",1L);
        doNothing().when(chatroomLocationService).setNewMaxRange(5,1L);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/updateChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first_testing_room");
        verify(chatroomEntitesService, times(1)).getRoomByName("first_testing_room");
        verify(chatroomEntitesService, times(1)).setChatroomEntities("hi",1L);
        verify(chatroomPrivilegesService, times(1)).setChatroomPrivileges("PUBLIC",true,"123","whitelist",1L); //why not true?
        verify(chatroomLocationService, times(1)).setNewMaxRange(5,1L);

        Assert.assertEquals("failure - expected HTTP response OK",
                200, status);

        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
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
    //@Sql(scripts = "classpath:populateDB.sql")
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
    //@Sql(scripts = "classpath:populateDB.sql")
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testQuitChatroomMembersService() throws Exception{
        json.put("room_name", "first_testing_room");
        json.put("user_name","mixalisMix");


        when(chatroomEntitesService.validateRoomnameExistance("first_testing_room")).thenReturn(true);
        when(userService.checkUsername("mixalisMix")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
        when(userService.getUserByUsername("mixalisMix")).thenReturn(new User (3L,"mixalisMix","mixalis",true,true));
        when(chatroomMembersService.checkIfMemberExistsInChatroom(3L,1L)).thenReturn(false);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/quitChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first_testing_room");
        verify(userService, times(1)).checkUsername("mixalisMix");
        verify(chatroomEntitesService, times(1)).getRoomByName("first_testing_room");
        verify(userService, times(1)).getUserByUsername("mixalisMix");
        verify(chatroomMembersService, times(1)).checkIfMemberExistsInChatroom(3L,1L);
        //verify(chatroomMembersService, times(0)).delete(CM);

        Assert.assertEquals("failure - expected HTTP response NOT_FOUND",404, status);

        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.NOT_AVAILABLE.getData()  + " '",
                content.equals(Responses.NOT_AVAILABLE.getData() ));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testQuitChatroom() throws Exception{
        json.put("room_name", "first_testing_room");
        json.put("user_name","milenaAz");


        when(chatroomEntitesService.validateRoomnameExistance("first_testing_room")).thenReturn(true);
        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
        when(userService.getUserByUsername("milenaAz")).thenReturn(new User (2L,"milenaAz","milena",true,true));
        when(chatroomMembersService.checkIfMemberExistsInChatroom(2L,1L)).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/quitChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first_testing_room");
        verify(userService, times(1)).checkUsername("milenaAz");
        verify(chatroomEntitesService, times(1)).getRoomByName("first_testing_room");
        verify(userService, times(1)).getUserByUsername("milenaAz");
        verify(chatroomMembersService, times(1)).checkIfMemberExistsInChatroom(2L,1L);
        //verify(chatroomMembersService, times(0)).delete(CM);

        Assert.assertEquals("failure - expected HTTP response OK",200, status);

        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.SUCCESS.getData()  + " '",
                content.equals(Responses.SUCCESS.getData()));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
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
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testAvailableChatroomLocation() throws Exception{

        //{"size":2,"list":[{"room_name1":"chatroom","room_name2":"chatroom2"}],"error":"no errors"};

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

        AvailableChatroomResponseDTO chatroomResponseDTO = super.mapFromJson(content,AvailableChatroomResponseDTO.class);

        verify(chatroomLocationService,times(1)).findIfNear(50,40);
        verify(chatroomEntitesService,times(1)).findByRoomID(1L);
        verify(chatroomEntitesService,times(1)).findByRoomID(2L);

        Assert.assertEquals("failure - expected HTTP response OK",200, status);

        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.NO_ERRORS.getData()  + " '",
                chatroomResponseDTO.getError().equals(Responses.NO_ERRORS.getData()));

        Assert.assertTrue("failure - expected size to be 2 but was '" + chatroomResponseDTO.getSize()  + " '",
                chatroomResponseDTO.getSize().equals("2"));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testUpdateLocation() throws Exception
    {
        json.put("lng", "6.7"); // not sure about this
        json.put("lat", "5.6");
        json.put("room_name", "first_testing_room");

        when(chatroomEntitesService.validateRoomnameExistance("first_testing_room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
        doNothing().when(chatroomLocationService).setNewLngLat(67,56,1L);


        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/updateChatroomLocation")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first_testing_room");
        verify(chatroomEntitesService, times(1)).getRoomByName("first_testing_room");
        verify(chatroomLocationService, times(1)).setNewLngLat(6.7f,5.6f,1L);

        Assert.assertEquals("failure - expected HTTP response OK",
                200, status);

        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }

    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
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
    //@Sql(scripts = "classpath:populateDB.sql")
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
    //@Sql(scripts = "classpath:populateDB.sql")
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
    //@Sql(scripts = "classpath:populateDB.sql")
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
    //@Sql(scripts = "classpath:populateDB.sql")
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
    //@Sql(scripts = "classpath:populateDB.sql")
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
    
    
    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testAvailableChatrooms_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomLocationDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomLocationDTO.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }
        
        json.put("lng", 250);
        json.put("lat", 240);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/findAvailableChatrooms")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '422'", 422, status);
        
        Assert.assertEquals("failure - expected HTTP response body to be '" + Responses.BAD_COORDINATES.getData() + "'",
                    content,Responses.BAD_COORDINATES.getData());
        ChatroomLocationDTO.cleanValidator();
    }
     
    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfStillInside_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomCheckInsideDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomCheckInsideDTO.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }
                
        json.put("lng" , 23.7629689);
        json.put("lat" , 37.9837928);
        json.put("room_name" , "@testRoom");
        json.put("user_name" , "testUser");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkIfStillInside")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertEquals("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                content,Responses.STRING_INAPPROPRIATE_FORMAT.getData());
        
        ChatroomCheckInsideDTO.cleanValidator();
    }
    
     @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testConnectToChatroom_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomMemberDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }        
        
        json.put("method","ADD");
        json.put("member_name","CorrectUserName");
        json.put("room_name","@CorrectRoomName");
        json.put("password","Aaaaa");
        json.put("lat","1");
        json.put("lng","1");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/connectChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        ChatroomMemberDTO.cleanValidator();
    }
    
     @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroom_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomDeleteDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }
                
        json.put("creator_name", "mixalisMix");
        json.put("room_name", "@third_testing_room");
        json.put("room_password", "password");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deleteChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        ChatroomDeleteDTO.cleanValidator();
    }
    
     @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testHandleBans_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomBlacklistDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomBlacklistDTO.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }
        
        Date date = new Date();
        date.setTime(date.getTime()+3600000);
        
        json.put("room_name","@room");
        json.put("member_name","userrr");
        json.put("expiration_date",date.getTime());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/banFromChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        ChatroomBlacklistDTO.cleanValidator();
    }
    
     @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testHandleNewChatroom_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomCreationDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomCreationDTO.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }
        
        json.put("username","user");
        json.put("room_name","@room");
        json.put("room_privilege","all");
        json.put("access_method","abc");
        json.put("room_lat",200.5);
        json.put("room_lng",100.5);
        json.put("room_max_range",50);
        json.put("hasPassword",false);
        json.put("password","");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/newChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        ChatroomCreationDTO.cleanValidator();
    }
    
    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testHandleWhitelist_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomWhitelistDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomWhitelistDTO.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }
                
        json.put("room_name", "@Room");
        json.put("member_name", "Member");
        json.put("mode", "ADD");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/handleWhitelist")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        ChatroomWhitelistDTO.cleanValidator();
    }
    
    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testQuitChatroom_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomQuitMemberDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomQuitMemberDTO.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }        
        
        json.put("room_name", "@first_testing_room");
        json.put("user_name", "milenaAz");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/quitChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        ChatroomQuitMemberDTO.cleanValidator();
    }
    
    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testUpdateChatroom_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomUpdateDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomUpdateDTO.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }
                
        json.put("room_name", "@first_testing_room");
        json.put("new_room_name", "hi");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "whitelist");
        json.put("passwordProtection",true);
        json.put("password", "123");
        json.put("max_range", "5");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/updateChatroom")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        ChatroomUpdateDTO.cleanValidator();
    }
    
    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testUpdateLocation_ValidationFail() throws Exception
    {     
        try 
        {
            ChatroomLocationUpdateDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomLocationUpdateDTO.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        }        
        
        json.put("lng", "6.7"); // not sure about this
        json.put("lat", "5.6");
        json.put("room_name", "@first_testing_room");

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/updateChatroomLocation")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status);
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        ChatroomLocationUpdateDTO.cleanValidator();
    }
    
    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testQuitChatroomAdminQuiting() throws Exception{
        json.put("room_name", "first_testing_room");
        json.put("user_name","milenaAz");


        when(chatroomEntitesService.validateRoomnameExistance("first_testing_room")).thenReturn(true);
        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first_testing_room")).thenReturn(new ChatroomEntities(1L,1L,"first_testing_room"));
        when(userService.getUserByUsername("milenaAz")).thenReturn(new User (1L,"milenaAz","milena",true,true));
        when(chatroomMembersService.checkIfMemberExistsInChatroom(1L,1L)).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/quitChatroom")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first_testing_room");
        verify(userService, times(1)).checkUsername("milenaAz");
        verify(chatroomEntitesService, times(1)).getRoomByName("first_testing_room");
        verify(userService, times(1)).getUserByUsername("milenaAz");
        verify(chatroomMembersService, times(1)).checkIfMemberExistsInChatroom(1L,1L);
        //verify(chatroomMembersService, times(0)).delete(CM);

        Assert.assertEquals("failure - expected HTTP response OK",200, status);

        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.SUCCESS.getData()  + " '",
                content.equals(Responses.SUCCESS.getData()));
    }
}