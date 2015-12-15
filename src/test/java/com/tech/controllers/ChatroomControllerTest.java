/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.services.chatroom.*;
import com.tech.services.user.UserService;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

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
    @Sql(scripts = "classpath:populateDB.sql")
    public void updateChatroomRoomNotExist() throws Exception
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
    @Sql(scripts = "classpath:populateDB.sql")
    public void updateChatroom() throws Exception
    {
        json.put("room_name", "first testing room");
        json.put("new_room_name", "hi");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "whitelist");
        json.put("passwordProtection",true);
        json.put("password", "123");
        json.put("max_range", "5");

        when(chatroomEntitesService.validateRoomnameExistance("first testing room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
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

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first testing room");
        verify(chatroomEntitesService, times(1)).getRoomByName("first testing room");
        verify(chatroomEntitesService, times(1)).setChatroomEntities("hi",1L);
        verify(chatroomPrivilegesService, times(1)).setChatroomPrivileges("PUBLIC",true,"123","whitelist",1L); //why not true?
        verify(chatroomLocationService, times(1)).setNewMaxRange(5,1L);

        Assert.assertEquals("failure - expected HTTP response OK",
                200, status);

        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
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
    @Sql(scripts = "classpath:populateDB.sql")
    public void testUpdateLocation() throws Exception
    {
        json.put("lng", "6.7"); // not sure about this
        json.put("lat", "5.6");
        json.put("room_name", "first testing room");

        when(chatroomEntitesService.validateRoomnameExistance("first testing room")).thenReturn(true);
        when(chatroomEntitesService.getRoomByName("first testing room")).thenReturn(new ChatroomEntities(1L,1L,"first testing room"));
        doNothing().when(chatroomLocationService).setNewLngLat(67,56,1L);


        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/updateChatroomLocation")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);

        verify(chatroomEntitesService, times(1)).validateRoomnameExistance("first testing room");
        verify(chatroomEntitesService, times(1)).getRoomByName("first testing room");
        verify(chatroomLocationService, times(1)).setNewLngLat(6.7f,5.6f,1L);

        Assert.assertEquals("failure - expected HTTP response OK",
                200, status);

        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));


    }

    @Test
    public void testCheckIfStillInside() {
    }

}
