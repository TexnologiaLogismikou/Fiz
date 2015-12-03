/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.Friend;
import com.tech.services.interfaces.IFriendService;
import java.util.ArrayList;
import java.util.List;
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
 * @author milena
 */

@Transactional
@WebAppConfiguration
@ActiveProfiles({"milena","milena"})
public class FriendServiceTest extends AbstractTest
{
    @Autowired
    private IFriendService service;
    
    private List<Friend> friendsToAdd = new ArrayList();
    private List<Friend> friendsToDelete = new ArrayList();
    
    public FriendServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() 
    {
        friendsToAdd.add(new Friend(1L,2L));
        friendsToAdd.add(new Friend(1L,3L));
        friendsToDelete.add(new Friend(3L,1L));
    }
    
    @After
    public void tearDown() 
    {
        friendsToAdd = null;
        friendsToDelete = null;
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
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteFriend()
    {
        service.deleteFriend(friendsToAdd.get(0));
        Assert.assertTrue("fail", !service.checkFriendIfExists(friendsToAdd.get(0)));
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
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckFriendIfExists()
    {
        Assert.assertTrue("fail",service.checkFriendIfExists(friendsToAdd.get(0)));
    }

    /**
     * Test of getAllFriends method, of class FriendService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetAllFriends() 
    {
       //Assert.assertTrue("fail",!service.getAllFriends().isEmpty());
       Assert.assertTrue("fail",service.getAllFriends().size()==2); 
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetFriendsByMonth() 
    {
        
    }
    
}
