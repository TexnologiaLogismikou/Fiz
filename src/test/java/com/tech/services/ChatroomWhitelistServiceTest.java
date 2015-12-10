/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.ChatroomWhitelist;
import com.tech.services.interfaces.IChatroomWhitelistService;
import java.sql.Date;
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
public class ChatroomWhitelistServiceTest extends AbstractTest{
    
     @Autowired
    private IChatroomWhitelistService service;
    
    private ChatroomWhitelist whitelistExist ;
    //private ChatroomWhitelist whitelistNotExist;
    
    public ChatroomWhitelistServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        whitelistExist = new ChatroomWhitelist(1L, 1L, Date.valueOf("2014-1-1"));
        //whitelistNotExist = new ChatroomWhitelist(1L, 1L, Date.valueOf("1-1-2014"));
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddUserToWhitelist() {
        service.deleteUserFromWhitelist(whitelistExist);
        Assert.assertNull(service.findByRoomIDAndRoomMember(1L, 1L));
        service.addUserToWhitelist(whitelistExist);
        Assert.assertNotNull(service.findByRoomIDAndRoomMember(1L, 1L));
       
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteUserFromBlacklist() {
        Assert.assertNotNull(service.findByRoomIDAndRoomMember(1L, 1L));
        service.deleteUserFromWhitelist(whitelistExist);
        Assert.assertNull(service.findByRoomIDAndRoomMember(1L, 1L));
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomID() {
        Assert.assertEquals("Fail to find User by id",whitelistExist.getRoom_id(),
               service.findByRoomID(1L).get(0).getRoom_id());
    }

   
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomMember() {
       junit.framework.Assert.assertEquals("Fail to find User by room Member",whitelistExist.getRoom_member(),
                service.findByRoomIDAndRoomMember(1L, 1L).getRoom_member());
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomIDAndRoomMember() {
        ChatroomWhitelist cr = service.findByRoomIDAndRoomMember(1L,1L);
        Assert.assertEquals("Fail find by romm ID and room member",whitelistExist.getRoom_invitation_time(),cr.getRoom_invitation_time());
        Assert.assertEquals("Fail find by romm ID and room member",whitelistExist.getRoom_member(),cr.getRoom_member());
       
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecords() {
        Long i = service.countRecords();
        Long ii = 2L;
        Assert.assertEquals("Counting was wrong",ii,i); 
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecordsOfRoom() {
        Long cr = service.countRecordsOfRoom(1L);
        Long i = 2L;
        Assert.assertEquals("Fail count record of room",i,cr);
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecordsOfMember() {
        Long cr = service.countRecordsOfMember(1L);
        Long i = 1L;
        Assert.assertEquals("Fail count record of room",i,cr);
    }
    
}
