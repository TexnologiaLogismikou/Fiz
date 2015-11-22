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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author milena
 */
@Transactional
public class FriendServiceTest extends AbstractTest
{
    @Autowired
    IFriendService service;
    private ArrayList<Friend> addedFriends = new ArrayList<>();
    private ArrayList<Friend> deletedFriends = new ArrayList<>();;
    
    
    
    public FriendServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    { 
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp()
    {
       addedFriends.add(new Friend(1L,2L));
       addedFriends.add(new Friend(1L,3L));
       deletedFriends.add(new Friend(1L,4L));
    }
    
    @After
    public void tearDown() 
    {
        addedFriends = null;
        deletedFriends = null;
    }
    
    /**
     * Test of getFriendByUser method, of class FriendService.
     */
    @Test
    @Sql(scripts = "classpath:friendTesting.sql")
    public void testGetFriendsByUser()
    {
        List<Friend> friendList = service.getFriendsByUser(1L);    
        Assert.assertTrue("wrong match",friendList.get(0).getFriendid().equals(2L));
        Assert.assertTrue("wrong match",friendList.get(1).getFriendid().equals(3L));
    }
   
    
    @Test
    @Sql(scripts = "classpath:friendTesting.sql")
    public void testFriendDeleted()
    {
        List<Friend> friendList = service.getFriendsByUser(1L);    
        // Deleting the friend 4 that user 1 has.
        service.deleteFriend(deletedFriends.get(0));
        //Here we are testing if the user 2 was deleted from the friendList of user 1
        Assert.assertFalse("error in deleting fiend",service.checkExistence(deletedFriends.get(0)));
    }
}

