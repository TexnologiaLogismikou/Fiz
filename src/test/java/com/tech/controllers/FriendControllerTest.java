/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.models.entities.Friend;
import com.tech.models.entities.User;
import com.tech.services.FriendService;
import com.tech.services.UserService;
import javax.transaction.Transactional;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author arxa
 */

@Transactional
@ActiveProfiles({"mixalis","mixalis"})
public class FriendControllerTest extends AbstractControllerTest
{
    String uri;
    
    @Mock
    private FriendService friendService;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private FriendController controller;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }    
    
    @Before
    @Override
    public void setUp()
    { 
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/friendservice";
    }
    
    @After
    public void tearDown() 
    {   
        uri = null;
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddFriend() throws Exception 
    {
        JSONObject json = new JSONObject();
        json.put("username","mixalis");
        json.put("friendname","milena");
        
        doNothing().when(friendService).addFriend(any(Friend.class));
        
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","milena",true));
        when(userService.getUserByUsername("mixalis")).thenReturn(new User(3L,"mixalis","mixalis",true));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/addfriend")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(2)).getUserByUsername(anyString());
        verify(friendService,times(1)).addFriend(any(Friend.class));
        
        Assert.assertEquals("failure not OK", 200, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be 'complete'",
                    content.equals("complete"));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteFriend() throws Exception 
    {
        JSONObject json = new JSONObject();
        json.put("username","milena");
        json.put("friendname","mixalis");
        
        doNothing().when(friendService).deleteFriend(any(Friend.class));
        
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","milena",true));
        when(userService.getUserByUsername("mixalis")).thenReturn(new User(3L,"mixalis","mixalis",true));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deletefriend")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(2)).getUserByUsername(anyString());
        verify(friendService,times(1)).deleteFriend(any(Friend.class));
        
        Assert.assertEquals("failure - expected HTTP status to be '200'", 200, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be 'complete'",
                    content.equals("complete"));
    }  
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckFriendExists() throws Exception 
    {
        JSONObject json = new JSONObject();
        json.put("username","milena");
        json.put("friendname","iwanna");
        
        
        when(friendService.checkFriendIfExists(1L,2L)).thenReturn(true);
        
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","milena",true));
        when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkfriend")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(2)).getUserByUsername(anyString());
        verify(friendService,times(1)).checkFriendIfExists(1L,2L);
        
        Assert.assertEquals("failure - expected HTTP status to be '302'", 302, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be 'Friend already exists'",
                    content.equals("Friend already exists"));
    }  
    
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckFriendNotExists() throws Exception 
    {
        JSONObject json = new JSONObject();
        json.put("username","iwanna");
        json.put("friendname","milena");
        
        
        when(friendService.checkFriendIfExists(2L,1L)).thenReturn(false);
        
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","milena",true));
        when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkfriend")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(2)).getUserByUsername(anyString());
        verify(friendService,times(1)).checkFriendIfExists(2L,1L);
        
        Assert.assertEquals("failure - expected HTTP status to be '200'", 200, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be 'available'",
                    content.equals("available"));
    }  
    
}



