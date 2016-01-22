/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.chatroom;

import com.tech.AbstractTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.chatroom.ChatroomLocation;
import com.tech.services.interfaces.ICRLocationService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
@ActiveProfiles({"iwanna", "iwanna"})
public class ChatroomLocationServiceTest extends AbstractTest
{
    @Autowired
    private ICRLocationService service;
    
    private static ChatroomLocation existingRecord, nonExistingRecord;
    
    public ChatroomLocationServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        existingRecord = new ChatroomLocation(1L,1,1,10000);
        nonExistingRecord = new ChatroomLocation(2L,5,5,50000);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        existingRecord = null;
        nonExistingRecord = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAdd() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomID(2L).isEmpty());
       service.add(nonExistingRecord);
       Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomID(2L).size()==1);
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomID() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomID(1L).size()==1);
//        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomID(2L).size()==1);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomID(3L).size()==1);
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByMaxRange() 
    {
        Assert.assertTrue(Responses.ERROR.getData(), service.findByMaxRange(10000).size()==2);
        Assert.assertTrue(Responses.ERROR.getData(), service.findByMaxRange(50000).isEmpty());
        service.add(nonExistingRecord);
        Assert.assertTrue(Responses.ERROR.getData(), service.findByMaxRange(50000).size()==1);
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindIfNear() 
    {
        Assert.assertTrue(Responses.ERROR.getData(), service.findIfNear(1,1).size()==1);
        Assert.assertTrue(Responses.ERROR.getData(), service.findIfNear(6,6).isEmpty());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testSetNewMaxRange() 
    {
        service.setNewMaxRange(10, 1L);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomID(1L).get(0).getRoom_max_range() == 10);

    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testSetNewLngLat() 
    {
        service.setNewLngLat(100, 100,1L);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomID(1L).size()== 1);      
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckIfStillInside() 
    {
       Assert.assertTrue(Responses.ERROR.getData(),service.checkIfStillInside(1L, 1, 1));       
    }
    
    
}
