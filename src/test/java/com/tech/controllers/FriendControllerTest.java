/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.Friend;
import com.tech.models.entities.User;
import com.tech.services.FriendService;
import com.tech.services.UserService;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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
    @Override
    public void tearDown() 
    {   
        super.tearDown();
        uri = null;
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddFriend() throws Exception 
    {
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
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteFriend() throws Exception 
    {
        json.put("username","milena");
        json.put("friendname","mixalis");
        
        doNothing().when(friendService).deleteFriend(any(Friend.class));
        
        when(userService.getUserByUsername("milena")).thenReturn(new User(1L,"milena","milena",true));
        when(userService.getUserByUsername("mixalis")).thenReturn(new User(3L,"mixalis","mixalis",true));
        when(friendService.checkFriendIfExists(1L,3L)).thenReturn(true);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/deletefriend")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(2)).getUserByUsername(anyString());
        verify(friendService,times(1)).deleteFriend(any(Friend.class));
        
        Assert.assertEquals("failure - expected HTTP status to be '200'", 200, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.SUCCESS.getData() + "'",
                    content.equals(Responses.SUCCESS.getData()));
    }  
               
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testInAppropriateFormat() throws Exception 
    {
        json.put("username","5milena");
        json.put("friendname","!mixalis");
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/addfriend")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(0)).getUserByUsername(anyString());
        verify(userService,times(0)).checkUsername(anyString());
        verify(friendService,times(0)).checkFriendIfExists(anyLong(),anyLong());
        verify(friendService,times(0)).addFriend(any(Friend.class));
        
        Assert.assertEquals("failure - expected HTTP status to be '406'", 406, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                    content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFriendNotExist() throws Exception 
    {
       json.put("username","milena");
       json.put("friendname","vasilis"); 
       
       when(userService.checkUsername("vasilis")).thenReturn(false);
       
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/addfriend")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService,times(0)).getUserByUsername(anyString());
        verify(userService,times(1)).checkUsername(anyString());
        verify(friendService,times(0)).checkFriendIfExists(anyLong(),anyLong());
        verify(friendService,times(0)).addFriend(any(Friend.class));
        
        Assert.assertEquals("failure - expected HTTP status to be '404'", 404, status); 
        
        Assert.assertTrue("failure - expected HTTP response body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                    content.equals(Responses.NOT_AVAILABLE.getData()));
        
    }
}



