/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
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
