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

import java.util.Date;

/**
 * @author iwann
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna", "iwanna"})
public class ChatroomEntitiesServiceTest extends AbstractTest

        @Autowired
        private IChatroomEntitiesService service;


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
        }

        @After
        public void tearDown() {
        }


        @Test
        @Sql(scripts = "classpath:populateDB.sql")
        public void testaddChatroomEntity() {

        }


        @Test
        @Sql(scripts = "classpath:populateDB.sql")
        public void testDeleteChatroomEntity() {

        }


        @Test
        @Sql(scripts = "classpath:populateDB.sql")
        public void testFindByRoomID() {
                ChatroomEntities friend = new ChatroomEntities();
                service.findByRoomID(room_id,  room_creator,  room_name,  room_creation_date, room_last_activity);
                Assert.assertTrue("fail",service.checkFriendIfExists(friend.getUserid(),friend.getFriendid()));

        }


        @Test
        @Sql(scripts = "classpath:populateDB.sql")
        public void testFindByRoomCreator() {

        }


        @Test
        @Sql(scripts = "classpath:populateDB.sql")
        public void testCountRecords() {

        }


        @Test
        @Sql(scripts = "classpath:populateDB.sql")
        public void testCountRecordsOfMember() {

        }

}
