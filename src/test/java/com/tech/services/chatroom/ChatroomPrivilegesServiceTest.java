/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.chatroom;

import com.tech.AbstractTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.chatroom.ChatroomPrivileges;
import com.tech.services.interfaces.IChatroomPrivilegesService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
@ActiveProfiles({"iwanna","iwanna"})
public class ChatroomPrivilegesServiceTest extends AbstractTest
{
    @Autowired
    private IChatroomPrivilegesService service;
    
    private static ChatroomPrivileges existingRecord1, existingRecord2, nonExistingRecord;
    
    public ChatroomPrivilegesServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        existingRecord1 = new ChatroomPrivileges(1L,"PUBLIC",false,null,"whitelist");
        existingRecord2 = new ChatroomPrivileges(3L,"PUBLIC",true,"password","blacklist");
        nonExistingRecord = new ChatroomPrivileges(1L,"PRIVATE",true,"1234","blacklist");
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        existingRecord1 = null;
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
        service.add(nonExistingRecord);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByPrivileges("PRIVATE").size()==1);
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDelete() 
    {
        service.delete(existingRecord1);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByPrivileges("PUBLIC").size()==2);
        service.delete(existingRecord2);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByPrivileges("PUBLIC").size()==1);
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomId() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomId(1L).getRoom_id().compareTo(1L)==0);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomId(2L).getRoom_id().compareTo(2L)==0);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomId(3L).getRoom_id().compareTo(3L)==0);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomId(4L)==null);
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByPrivileges() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.findByPrivileges("PUBLIC").size()==3);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByPrivileges("PRIVATE").isEmpty());
        service.add(nonExistingRecord);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByPrivileges("PRIVATE").size()==1);
    }

   
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testValidateAccess() 
    {
       Assert.assertTrue(Responses.ERROR.getData(),service.validateAccess(3L,"password").getRoom_id().compareTo(3L)==0);
       Assert.assertTrue(Responses.ERROR.getData(),service.validateAccess(1L,"null")==null);
       Assert.assertTrue(Responses.ERROR.getData(),service.validateAccess(2L,"something")==null);
    }

   
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecords() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.countRecords()==3);
        service.delete(existingRecord1);
        Assert.assertTrue(Responses.ERROR.getData(),service.countRecords()==2);
    }

   
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecordsOfPrivileges() 
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.countRecordsOfPrivileges("PUBLIC")==3);
        Assert.assertTrue(Responses.ERROR.getData(),service.countRecordsOfPrivileges("PRIVATE")==0);
        service.delete(existingRecord1);
        Assert.assertTrue(Responses.ERROR.getData(),service.countRecordsOfPrivileges("PUBLIC")==2);
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testSetChatroomPrivileges() 
    {
        service.setChatroomPrivileges("PUBLIC", false, "milena", "blacklist", 1L);
        Assert.assertTrue(Responses.ERROR.getData(),service.findByRoomId(1L).getRoom_privileges().equals("PUBLIC"));
    }
    
}
