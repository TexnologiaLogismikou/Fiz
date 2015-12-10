/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.ChatroomEntities;
import com.tech.services.interfaces.IChatroomEntitiesService;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * @author Aenaos
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna", "iwanna"})
public class ChatroomEntitiesServiceTest extends AbstractTest {

    @Autowired
    private IChatroomEntitiesService service;

    private ArrayList<ChatroomEntities> chatroomEntitiesList = new ArrayList<>();

    public ChatroomEntitiesServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        chatroomEntitiesList.add(new ChatroomEntities(1L, 1L, "PlayRoom"));
        chatroomEntitiesList.add(new ChatroomEntities(2L, 2L, "DarkRoom"));
        chatroomEntitiesList.add(new ChatroomEntities(3L, 3L, "SomeRoom"));
        chatroomEntitiesList.add(new ChatroomEntities(4L, 1L, "ThisRoom"));
        chatroomEntitiesList.add(new ChatroomEntities(5L, 2L, "TalkRoom"));
        chatroomEntitiesList.add(new ChatroomEntities(6L, 3L, "ChatRoom"));
    }

    @After
    public void tearDown() {
        chatroomEntitiesList = null;
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testaddChatroomEntity() {

        service.addChatroomEntity(new ChatroomEntities(4L, 1L, "TestRoom"));
        Assert.assertTrue("Could not add chatroom", service.checkIfChatroomExists(4L));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomEntity() {

        ChatroomEntities testRoom = chatroomEntitiesList.get(0);
        service.deleteChatroomEntity(testRoom);
        Assert.assertFalse("Could not delete chatroom", service.checkIfChatroomExists(testRoom.getRoom_id()));

    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfChatroomExists() {
        Assert.assertTrue("Could not find chatroom", service.checkIfChatroomExists(chatroomEntitiesList.get(0).getRoom_id()));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomID() {
        ChatroomEntities testRoom = service.findByRoomID(1L);
        Assert.assertTrue("Failed to find room by room_id",testRoom.getRoom_id().equals(1L));
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomCreator() {
        Assert.assertTrue("Failed to find rooms by room creator",service.findByRoomCreator(1L).size()==2);
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecords() {
        Assert.assertTrue("Failed to count rooms",service.countRecords()==3);

    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecordsOfMember() {
        Assert.assertTrue("Failed to count rooms created by user",service.countRecordsOfMember(1L)==2);

    }

}
