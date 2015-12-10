/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.ChatroomEntities;
import com.tech.models.entities.ChatroomMembers;
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
 * @author iwann
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
        //service.add(new ChatroomMembers(1L, 1L));
       // Assert.assertTrue("Could not add member", service.checkIfChatroomEntityExists(4L));
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatMember() {

//        service.delete(service.findByRoomId(1L));
//
//        service.delete(testRoom);
//        Assert.assertFalse("Could not delete chatroom", service.checkIfChatroomEntityExists(1L);

    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomId() {

    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomMember() {

    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecords() {
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecordsOfRoom() {

    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecordsOfMember() {

    }

}
