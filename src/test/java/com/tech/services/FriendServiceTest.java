/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.Friend;
import com.tech.services.interfaces.IFriendService;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author milena
 */

@Transactional
@WebAppConfiguration
@ActiveProfiles({"milena","milena"})
public class FriendServiceTest extends AbstractTest
{
    @Autowired
    IFriendService service;
    
    public FriendServiceTest() {
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

    /**
     * Test of addFriend method, of class FriendService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddFriend()
    {
        Friend friend = new Friend(3L,1L);
        service.addFriend(friend);
        Assert.assertTrue("fail",service.checkFriendIfExists(friend));
    }

    /**
     * Test of deleteFriend method, of class FriendService.
     */
    @Test
    public void testDeleteFriend()
    {
        
    }

    /**
     * Test of getFriendsByUser method, of class FriendService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetFriendsByUser()
    {
        List<Friend> friendList = service.getFriendsByUser(1L);    
        Assert.assertTrue("wrong match",friendList.get(0).getFriendid().equals(2L));
        Assert.assertTrue("wrong match",friendList.get(1).getFriendid().equals(3L));
    }

    /**
     * Test of checkFriendIfExists method, of class FriendService.
     */
    @Test
    public void testCheckFriendIfExists()
    {
        
    }

    /**
     * Test of getAllFriends method, of class FriendService.
     */
    @Test
    public void testGetAllFriends() 
    {
       
    }
    
}
