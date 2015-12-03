/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.services.UserInfoService;
import com.tech.services.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
@ActiveProfiles({"mixalis","mixalis"})
public class GeneralControllerTest extends AbstractControllerTest{
    String uri;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private GeneralController controller;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }    
    
    @Before
    @Override
    public void setUp(){ 
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/generalController";
    }
    
    @After
    public void tearDown() {   
        uri = null;
    }
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testValidateUsername() throws Exception {
        List<String> listOfUsernames = new ArrayList();
        listOfUsernames.add("milena");
        listOfUsernames.add("mixalis");
        listOfUsernames.add("iwanna");
        listOfUsernames.add("andreas");
        listOfUsernames.add("giorgos");
        listOfUsernames.add("basilis");
        listOfUsernames.add("thanasis");
        listOfUsernames.add("nikiforos");
        
        when(userService.checkUsername("milena")).thenReturn(true);
        when(userService.checkUsername("mixalis")).thenReturn(true);
        when(userService.checkUsername("iwanna")).thenReturn(true);
        when(userService.checkUsername("andreas")).thenReturn(false);
        when(userService.checkUsername("giorgos")).thenReturn(false);
        when(userService.checkUsername("basilis")).thenReturn(false);
        when(userService.checkUsername("thanasis")).thenReturn(false);
        when(userService.checkUsername("nikiforos")).thenReturn(false);
        
        for (String vLookUp : listOfUsernames){            
            MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/checkUsername")
                    .param("username",vLookUp))
                    .andReturn();
            
            String content = result.getResponse().getContentAsString();
            int status = result.getResponse().getStatus();

            verify(userService,times(1)).checkUsername(vLookUp);
            if (vLookUp.equals("milena") || vLookUp.equals("mixalis") || vLookUp.equals("iwanna")) {
                Assert.assertEquals("failure - expected HTTP status 302", 302, status);
                Assert.assertTrue("failure - expected HTTP response body to be 'available'",
                    content.equals("Not available"));                  
            } else {           
                Assert.assertEquals("failure - expected HTTP status 200", 200, status);
                Assert.assertTrue("failure - expected HTTP response body to be 'available'",
                    content.equals("available"));         
            }
        }
    }  
    
}
