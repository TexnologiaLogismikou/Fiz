/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.chatroom;

import com.tech.AbstractTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.services.interfaces.IChatroomEntitiesService;
import java.sql.Date;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;

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

        service.add(new ChatroomEntities(4L, 1L, "TestRoom"));
        Assert.assertTrue("Could not add chatroom", service.checkIfChatroomEntityExists(4L));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteChatroomEntity() {

        ChatroomEntities testRoom = chatroomEntitiesList.get(0);
        service.delete(testRoom);
        Assert.assertFalse("Could not delete chatroom", service.checkIfChatroomEntityExists(testRoom.getRoom_id()));

    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfChatroomEntityExists() {
        Assert.assertTrue("Could not find chatroom", service.checkIfChatroomEntityExists(chatroomEntitiesList.get(0).getRoom_id()));
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
        Assert.assertNotNull("Failed to find rooms by room creator",service.findByRoomCreator(1L));
    }


    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecords() {
        Assert.assertTrue("Failed to count rooms",service.countRecords()==3);

    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetNextID() 
    {
        Long l = service.getNextID();
        Assert.assertTrue(Responses.ERROR.getData(),l==4);
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testValidateRoomnameExistance() 
    {
        Assert.assertFalse(Responses.ERROR.getData(),service.validateRoomnameExistance("playrooom"));
        Assert.assertTrue(Responses.ERROR.getData(),service.validateRoomnameExistance("first testing room"));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetRoomByName() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getRoomByName("first testing room").getRoom_id().compareTo(1L)==0);
        Assert.assertTrue(Responses.ERROR.getData(),service.getRoomByName("playroom")==null);
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testSetChatroomEntities() 
    {
        service.setChatroomEntities("ArxaRoom", 1L);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomID(1L).getRoom_name().equals("ArxaRoom"));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testUpdateLastActivity() 
    {
        Date testDate = new Date(Calendar.getInstance().getTimeInMillis()+1000);
        service.updateLastActivity(testDate,1L);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomID(1L).getRoom_last_activity().compareTo(testDate)==0);
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetNextIDMiddleNumber() 
    {
        ChatroomEntities testRoom = chatroomEntitiesList.get(1);
        service.delete(testRoom);
        Long l = service.getNextID();
        Assert.assertTrue(Responses.ERROR.getData(),l==2);
    }
    
}
