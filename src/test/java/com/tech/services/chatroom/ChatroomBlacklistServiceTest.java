/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.chatroom;

import com.tech.AbstractTest;
import com.tech.models.entities.chatroom.ChatroomBlacklist;
import com.tech.repositories.ICRBlacklist;
import com.tech.services.interfaces.IChatroomBlacklistService;
import java.sql.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
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
public class ChatroomBlacklistServiceTest extends AbstractTest{
    
    @Autowired
    private IChatroomBlacklistService service;
    
    private ChatroomBlacklist blacklistExist ;
    private ChatroomBlacklist blacklistNotExist;
            
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        blacklistExist= new ChatroomBlacklist(2L, 3L, Date.valueOf("2015-12-28"));
        blacklistNotExist= new ChatroomBlacklist(3L, 2L, Date.valueOf("2016-11-30"));
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddUserToBlacklist() {
        service.add(blacklistNotExist);
        
        Assert.assertEquals("Failure - fail add user to blacklist",
                blacklistNotExist.getRoom_id(),
                service.findByRoomID(blacklistNotExist.getRoom_id()).get(0).getRoom_id());
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteUserFromBlacklist() {
       Boolean bool = service.delete(blacklistExist);
       
       Assert.assertTrue("Fail delete User From Blacklist",bool);
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomID() {
       Assert.assertEquals("Fail to find User by id",blacklistExist.getRoom_id(),
               service.findByRoomID(2L).get(0).getRoom_id());
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomMember() {
        Assert.assertEquals("Fail to find User by room Member",blacklistExist.getRoom_member(),
            service.findByRoomMember(3L).get(0).getRoom_member());
    }

    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindByRoomIDAndRoomMember() {
        ChatroomBlacklist cr = service.findByRoomIDAndRoomMember(2L,3L);
        Assert.assertEquals("Fail find by romm ID and room member",blacklistExist.getRoom_expiration_time(),cr.getRoom_expiration_time());
        Assert.assertEquals("Fail find by romm ID and room member",blacklistExist.getRoom_id(),cr.getRoom_id());
        Assert.assertEquals("Fail find by romm ID and room member",blacklistExist.getRoom_member(),cr.getRoom_member());
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
        Long cr = service.countRecordsOfRoom(2L);
        Long i = 1L;
        Assert.assertEquals("Fail count record of room",i,cr);
    }

   
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCountRecordsOfMember() {
        Long cr = service.countRecordsOfMember(3L);
        Long i = 1L;
        Assert.assertEquals("Fail count record of room",i,cr);
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void setNewTime(){
//        ChatroomBlacklist cr = service.findByRoomIDAndRoomMember(2L,3L);
//        Assert.assertEquals("Fail find by romm ID and room member",blacklistExist.getRoom_expiration_time(),cr.getRoom_expiration_time());
//        Assert.assertEquals("Fail find by romm ID and room member",blacklistExist.getRoom_id(),cr.getRoom_id());
//        Assert.assertEquals("Fail find by romm ID and room member",blacklistExist.getRoom_member(),cr.getRoom_member());
//       
        service.setNewTime(blacklistExist.getRoom_id(),blacklistExist.getRoom_member(),Date.valueOf("2015-12-30"));
        
        Assert.assertNotEquals("Fail to set newTime",blacklistExist.getRoom_expiration_time(),
                service.findByRoomIDAndRoomMember(blacklistExist.getRoom_id(),blacklistExist.getRoom_member()));
    }
    
}
