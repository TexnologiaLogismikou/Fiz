/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.InitializeValidators;
import com.tech.models.entities.user.User;
import com.tech.models.entities.user.UserInfo;
import com.tech.models.entities.user.UserRole;
import com.tech.services.user.UserInfoService;
import com.tech.services.user.UserRoleService;
import com.tech.services.user.UserService;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
@ActiveProfiles({"mixalis","mixalis"})
public class RegistrationControllerTest extends AbstractControllerTest{
    String uri;
    
    @Mock
    private UserService userService;
    
    @Mock
    private UserInfoService userInfoService;
    
    @Mock 
    private UserRoleService userRoleService; 
    
    @InjectMocks
    private RegistrationController controller;
    
    @BeforeClass
    public static void setUpClass()
    {
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass()
    {     
        InitializeValidators.InitializeCustomValidators();
    }    
    
    @Before
    @Override
    public void setUp(){ 
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        InitializeValidators.InitializeCustomValidators();
        
        uri = "/register";
    }
    
    @After
    @Override
    public void tearDown() {   
        super.tearDown();
        uri = null;
    }
    
    @Test
    //@Sql(scripts = "classpath:populateDB.sql")
    public void testRegister() throws Exception{
        json.put("username","milena4");
        json.put("password","milena12312314");
        json.put("last_name","iwanna");
        json.put("firstname","milenref34a");
        json.put("email","douleuei@teicm.gr");
        json.put("birthday","1994-11-23");
        
        when(userService.getNextID()).thenReturn(4L);
        when(userService.checkUsername("milena4")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(userService, times(1)).getNextID();
        verify(userService, times(1)).addUser(any(User.class));
        verify(userService, times(1)).checkUsername("milena4");
        verify(userInfoService, times(1)).addUserInfo(any(UserInfo.class));      
        verify(userRoleService,times(1)).addUserRole(any(UserRole.class));
        
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to not be empty",
                content.trim().length() > 0);     
    } 
}
