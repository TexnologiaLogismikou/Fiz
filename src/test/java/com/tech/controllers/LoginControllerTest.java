/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.user.User;
import com.tech.models.entities.user.UserInfo;
import com.tech.services.user.UserInfoService;
import com.tech.services.user.UserRoleService;
import com.tech.services.user.UserService;
import java.util.Date;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
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
 * @author KuroiTenshi
 */
@Transactional
@ActiveProfiles({"mixalis","mixalis"})
public class LoginControllerTest extends AbstractControllerTest
{
    
    String uri;
    
    @Mock
    private UserRoleService userRoleService;
    
    @Mock
    private UserService userService;
    
    @Mock
    private UserInfoService userInfoService;
    
    @InjectMocks
    private LoginController controller;
    
    public LoginControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        InitializeValidators.InitializeCustomValidators();
    }
    
    @Before
    public void setUp() 
    {
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/login";
    }
    
    @After
    public void tearDown() 
    {
        super.tearDown();
        uri = null;
    }

    @Test
    public void testLoginSuccess() throws Exception
    {
        json.put("username", "billaras");
        json.put("password","12345");
        
        User tempUser = new User(4L,"billaras","12345",true,true);
        UserInfo tempUserInfo = new UserInfo (4L,"bill54greek@gmail.com","nothing",
                "hi there","iwannidis",new Date(),"Giannitsa","vasilis");
        
        when(userService.validateUser("billaras","12345")).thenReturn(true);
        when(userService.getUserByUsername("billaras")).thenReturn(tempUser);
        when(userInfoService.getUserInfoByUserId(tempUser.getId())).thenReturn(tempUserInfo);
        when(userRoleService.getRoleByUserID(tempUser.getId())).thenReturn("user");
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).getUserByUsername("billaras");
        verify(userService,times(1)).validateUser("billaras","12345");
        verify(userInfoService,times(1)).getUserInfoByUserId(tempUser.getId());
        verify(userRoleService,times(1)).getRoleByUserID(tempUser.getId());
        
        Assert.assertEquals("failure not OK", 200, status); 
        
    }
    
    @Test
    public void testLoginFail() throws Exception
    {
        json.put("username", "billaras");
        json.put("password","12345");
        
        when(userService.validateUser("billaras","12345")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = result.getResponse().getStatus();
        
        verify(userService,times(0)).getUserByUsername(anyString());
        verify(userService,times(1)).validateUser("billaras","12345");
        verify(userInfoService,times(0)).getUserInfoByUserId(anyLong());
        verify(userRoleService,times(0)).getRoleByUserID(anyLong());
        
        Assert.assertEquals("failure not unauthorized", 401, status); 
        
    }
    
}
