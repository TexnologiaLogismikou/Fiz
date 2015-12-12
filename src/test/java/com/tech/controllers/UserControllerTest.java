/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Responses;
import com.tech.models.dtos.UserResponseDTO;
import com.tech.services.user.UserInfoService;
import com.tech.services.user.UserService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author milena
 */
@Transactional
@ActiveProfiles({"milena","milena"})
public class UserControllerTest  extends AbstractControllerTest
{
    String uri;
    
    @Mock
    private UserService userService;
    
    @Mock
    private UserInfoService userInfoService;
    
    @InjectMocks
    private UserController controller;
    
    public UserControllerTest() {
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
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/user";
    }
    
    @After
    public void tearDown()
    {
        super.tearDown();
        uri = null;
    }

    /**
     * @author milena
     * Test of loadUserProfile method, of class UserController.
     * @throws java.lang.Exception
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testLoadUserProfile() throws Exception
    { 
        json.put("username", "milenaAz");
        json.put("firstname","milena");
        json.put("last_name","Azwidou");
        json.put("profile_photo","C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg");
        json.put("email","milena@gmail.com");
        json.put("birthday","22/11/1994");
        json.put("hometown","kabala");
        json.put("status","status");
        
        when(userService.checkUsername("milenaAz")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/milenaAz")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        //Assert.assertEquals("Fail expected status 200 but was " + status, 200, status);
        
        UserResponseDTO URDTO = super.mapFromJson(content,UserResponseDTO.class);

        verify(userService, times(1)).checkUsername("milenaAz");
        verify(userService, times(1)).getUserByUsername("milenaAz");
        verify(userInfoService, times(1)).getUserInfoByUserId(1L);
        
        Assert.assertEquals("failure - expected HTTP response OK",
                200, status);
        Assert.assertTrue("failure - expected HTTP response 'username' to be 'milenaAz'",
                URDTO.getUsername().equals("milenaAz"));
        Assert.assertTrue("failure - expected HTTP response 'firstname' to be 'milena'",
                URDTO.getFirstname().equals("milena"));
        Assert.assertTrue("failure - expected HTTP response 'last_name' to be 'Azwidou'",
                URDTO.getLast_name().equals("Azwidou"));
        Assert.assertTrue("failure - expected HTTP response 'profile_photo' to be 'C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg'",
                URDTO.getProfile_photo().equals("C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg"));
        Assert.assertTrue("failure - expected HTTP response 'email' to be 'milena@gmail.com'",
                URDTO.getEmail().equals("milena@gmail.com"));
        Assert.assertTrue("failure - expected HTTP response 'birthday' to be '22/11/1994'",
                URDTO.getBirthday().equals("22/11/1994"));
        Assert.assertTrue("failure - expected HTTP response 'hometown' to be 'kabala'",
                URDTO.getHometown().equals("kabala"));
        Assert.assertTrue("failure - expected HTTP response 'status' to be 'status'",
                URDTO.getStatus().equals("status"));
        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.SUCCESS.getData()  + " '",
                URDTO.getResponse().equals(Responses.SUCCESS.getData() ));
    }

    /**
     * Test of deactivateUser method, of class UserController.
     */
    @Test
    public void testDeactivateUser() {
       
    }
    
}
