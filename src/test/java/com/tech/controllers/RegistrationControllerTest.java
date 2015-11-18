/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
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
public class RegistrationControllerTest extends AbstractControllerTest{
    private String uri;
    public RegistrationControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        uri = "/register";
    }
    
    @After
    public void tearDown() {
    }

    /**
     * tests the the get command was called
     * @throws Exception 
     */
    @Test
    public void testLoadHtml() throws Exception {
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();        
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("failure - expected HTTP status 200",200, status);
        Assert.assertTrue("Failure - expected HTTP response body to have a value",content.trim().length() > 0);
    }   
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testSaveUserSucceed() throws Exception {
        User user = new User(4L,"andrew","andrew");
        String inputInJSON = super.mapToJson(user);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(inputInJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("Failure - expected HTTP status 200",200, status);
        Assert.assertTrue("Failure - expected HTTP response body to have a value",content.trim().length() > 0);        
    }
       
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testSaveUserFail()  throws Exception {
        User user = new User(4L,"iwanna","iwanna");
        String inputInJSON = super.mapToJson(user);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(inputInJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("failure - expected HTTP status 302",302, status);
        Assert.assertTrue("Failure - expected HTTP response body to have a value",content.trim().length() > 0);        
    }
}
