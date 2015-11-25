/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.models.entities.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
public class LogInAndOutControllerTest extends AbstractControllerTest{
    private String url;
    
    public LogInAndOutControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Override
    @Sql(scripts = "classpath:populateDB.sql")
    public void setUp() {
        super.setUp();
        url = "/log-in";
    }
    
    @After
    public void tearDown() {
    }
   
    @Test
    public void testLoadUser() throws Exception{
        User user = new User(2L,"iwanna","iwanna",true);
        String inputInJSON = super.mapToJson(user);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url)
                .content(inputInJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("Failure - expected HTTP status 200",200,status);
        Assert.assertFalse("Failure - expected HTTP response body to be null",content.trim().length() > 0);
    }
    
    @Test
    public void testLoadUserFailBoth() throws Exception {
        User user = new User(2L,"giorgos","giorgos",true);
        String inputInJSON = super.mapToJson(user);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url)
                .content(inputInJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("Failure - expected HTTP status 404",404,status);
        Assert.assertTrue("Failure - expected HTTP response body not to have a value",content.trim().length() > 0);
    }
    
    @Test
    public void testLoadUserFailUsername() throws Exception {
        User user = new User(2L,"giorgos","iwanna",true);
        String inputInJSON = super.mapToJson(user);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url)
                .content(inputInJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("Failure - expected HTTP status 404",404,status);
        Assert.assertTrue("Failure - expected HTTP response body not to have a value",content.trim().length() > 0);
    }
    
     @Test
    public void testLoadUserFailPassword() throws Exception {
        User user = new User(2L,"iwanna","giorgos",true);
        String inputInJSON = super.mapToJson(user);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url)
                .content(inputInJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("Failure - expected HTTP status 404",404,status);
        Assert.assertTrue("Failure - expected HTTP response body not to have a value",content.trim().length() > 0);
    }
    
    @Test
    public void testLoadHtml() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("Failure - expected HTTP status 200",200, status);
        Assert.assertTrue("Failure - expected HTTP response body to have a value",content.trim().length() > 0);
    }
    
}
