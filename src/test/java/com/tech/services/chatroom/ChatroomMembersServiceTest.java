/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.chatroom;

import com.tech.AbstractTest;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.chatroom.ChatroomMembers;
import com.tech.services.interfaces.IChatroomBlacklistService;
import com.tech.services.interfaces.IChatroomMembersService;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author Aenaos
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna", "iwanna"})
public class ChatroomMembersServiceTest extends AbstractTest {

    @Autowired
    private IChatroomMembersService service;

    private ArrayList<ChatroomMembers> chatroomMembersList = new ArrayList<>();

    public ChatroomMembersServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        chatroomMembersList.add(new ChatroomMembers(1L, 1L));
        chatroomMembersList.add(new ChatroomMembers(2L, 2L));
        chatroomMembersList.add(new ChatroomMembers(3L, 3L));
        chatroomMembersList.add(new ChatroomMembers(4L, 1L));
        chatroomMembersList.add(new ChatroomMembers(5L, 2L));
        chatroomMembersList.add(new ChatroomMembers(6L, 3L));
    }


    @After
    public void tearDown() {
        chatroomMembersList = null;
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddChatroomMember() {
        service.add(new ChatroomMembers(3L, 1L));
        Assert.assertTrue("Could not add member", service.checkIfMemberExistsInChatroom(1L, 3L));
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatMember() {
        service.delete(service.findByRoomIdAndMember(1L, 1L));
        Assert.assertFalse("Could not delete member", service.checkIfMemberExistsInChatroom(1L, 1L));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomIdAndMember() {
        Assert.assertTrue("Failed to find member in room",service.findByRoomIdAndMember(1L,1L).getRoom_id().equals(1L));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomId() {
        Assert.assertTrue("Failed to find members by room id",service.findByRoomId(1L).size()==2);
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomMember() {
        Assert.assertTrue("Failed to find members by room id",service.findByRoomMember(2L).size()==3);

    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecords() {
        Assert.assertTrue("Failed to count records",service.countRecords()==5);
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecordsOfRoom() {
        Assert.assertTrue("Failed to count rooms",service.countRecordsOfRoom(1L)==2);
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecordsOfMember() {
        Assert.assertTrue("Failed to count members",service.countRecordsOfMember(1L)==1);
        Assert.assertTrue("Failed to count members",service.countRecordsOfMember(2L)==3);

    }

}
