/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.models.dtos.LoginUserResponseDTO;
import javax.transaction.Transactional;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author bill5_000
 */
@Transactional
@ActiveProfiles({"milena","milena"})
public class LogInControllerTest extends AbstractControllerTest{
    String uri;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }    
    
    @Before
    @Override
    public void setUp(){ 
        
        super.setUp();
        
        uri = "/login";
    }
    
    @After
    public void tearDown() {   
        uri = null;
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testHandleLoginNotAuthorized() throws Exception{
        /*JSONObject json = new JSONObject();
        json.put("username","milena4");
        json.put("password","milena12312314");
        
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
            
        LoginUserResponseDTO LURDTO = super.mapFromJson(content, LoginUserResponseDTO.class);
        
        Assert.assertEquals("failure - expected HTTP response OK",
                200, status); 
        Assert.assertTrue("failure - expected HTTP response 'username' to be 'NOT_AUTHORIZED'",
                LURDTO.getUsername().equals("NOT_AUTHORIZED"));
        Assert.assertTrue("failure - expected HTTP response 'role' to be 'NOT_AUTHORIZED'",
                LURDTO.getRole().equals("NOT_AUTHORIZED"));
        Assert.assertTrue("failure - expected HTTP response 'error' to be 'error'",
                LURDTO.getError().equals("error"));*/
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testHandleLoginAuthorized() throws Exception{
        /*JSONObject json = new JSONObject();
        json.put("username","milena");
        json.put("password","milena");
        
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        
        Assert.assertEquals("failure - expected HTTP response OK",
                200, status); 
        Assert.assertTrue("failure - expected HTTP response body to not be empty",
                content.trim().length() > 0);*/
    }
}
