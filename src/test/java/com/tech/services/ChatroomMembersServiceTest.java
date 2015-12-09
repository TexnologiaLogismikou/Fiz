/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author iwann
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class ChatroomMembersServiceTest extends AbstractTest{
    
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
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddUserToBlacklist() {
       
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteUserFromChatEntities() {
       
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
