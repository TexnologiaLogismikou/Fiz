/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.InitializeValidators;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"mixalis","mixalis"})
public class PingControllerTest extends AbstractControllerTest{
    String uri;
    
    @InjectMocks
    private PingController controller;
    
    public PingControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass() {
        InitializeValidators.InitializeCustomValidators();
    }
    
    @Override
    @Before
    public void setUp() throws Exception{ 
        
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/ping";
    }
    
    @After
    public void tearDown() {   
        super.tearDown();
        uri = null;
    }    
    
    
    @Test
    public void testSuccessPing() throws Exception{
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        
        Assert.assertEquals("failure not expected", "All good. You don't need to be authenticated to call this"
                , content); 
    }
}
