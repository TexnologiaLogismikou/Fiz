///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tech.controllers;
//
//import com.tech.AbstractControllerTest;
//import com.tech.configurations.tools.AvailableRoles;
//import com.tech.configurations.tools.Responses;
//import com.tech.models.dtos.LoginUserResponseDTO;
//import com.tech.models.entities.User;
//import com.tech.models.entities.UserRole;
//import com.tech.services.UserRoleService;
//import com.tech.services.UserService;
//import javax.transaction.Transactional;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import static org.mockito.Matchers.anyString;
//import org.mockito.Mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
///**
// *
// * @author bill5_000
// */
//@Transactional
//@ActiveProfiles({"milena","milena"})
//public class LogInControllerTest extends AbstractControllerTest{
//    String uri;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private UserRoleService userRoleService;
//
//    @InjectMocks
//    private LogInController controller;
//
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    @Override
//    public void setUp(){
//        MockitoAnnotations.initMocks(this);
//        super.setUp(controller);
//        uri = "/login";
//    }
//
//    @After
//    @Override
//    public void tearDown() {
//        super.tearDown();
//        uri = null;
//    }
//
//    @Test
//    @Sql(scripts = "classpath:populateDB.sql")
//    public void testHandleLoginNotAuthorized() throws Exception{
//        json.put("username","milena4");
//        json.put("password","milena12312314");
//
//        when(userService.validateUser("milena4","milena12312314")).thenReturn(false);
//
//        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
//                .content(json.toString())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//
//        String content = result.getResponse().getContentAsString();
//        int status = result.getResponse().getStatus();
//
//        Assert.assertTrue("failure - expected HTTP response body to have lengh",content.trim().length() > 0);
//
//        LoginUserResponseDTO LURDTO = super.mapFromJson(content, LoginUserResponseDTO.class);
//
//        verify(userService,times(1)).validateUser("milena4","milena12312314");
//        verify(userService,times(0)).getUserByUsername(anyString());
//
//        Assert.assertEquals("failure - expected HTTP response OK",
//                200, status);
//        Assert.assertTrue("failure - expected HTTP response 'username' to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
//                LURDTO.getUsername().equals(Responses.NOT_AUTHORIZED.getData() ));
//        Assert.assertTrue("failure - expected HTTP response 'role' to be '" + Responses.NOT_AUTHORIZED.getData() + "'",
//                LURDTO.getRole().equals(Responses.NOT_AUTHORIZED.getData() ));
//        Assert.assertTrue("failure - expected HTTP response 'error' to be '" + Responses.ERROR.getData() + "'",
//                LURDTO.getError().equals(Responses.ERROR.getData()));
//    }
//
//    @Test
//    @Sql(scripts = "classpath:populateDB.sql")
//    public void testHandleLoginAuthorized() throws Exception{
//        json.put("username","milenaAz");
//        json.put("password","milena");
//
//        when(userService.validateUser("milenaAz","milena")).thenReturn(true);
//        when(userService.getUserByUsername("milenaAz")).thenReturn(new User(1L,"milenaAz","milena",true));
//        when(userRoleService.getRoleByUserID(1L)).thenReturn(AvailableRoles.ROLE_USER.getData());
//
//        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
//                .content(json.toString())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        int status = result.getResponse().getStatus();
//
//        LoginUserResponseDTO LURDTO = super.mapFromJson(content, LoginUserResponseDTO.class);
//
//        verify(userService,times(1)).validateUser("milenaAz","milena");
//        verify(userService,times(1)).getUserByUsername("milenaAz");
//
//        Assert.assertEquals("failure - expected HTTP response OK",
//                200, status);
//        Assert.assertTrue("failure - expected HTTP response 'username' to be 'milenaAz'",
//                LURDTO.getUsername().equals("milenaAz"));
//        Assert.assertTrue("failure - expected HTTP response 'role' to be '" + AvailableRoles.ROLE_USER.getData() + "'",
//                LURDTO.getRole().equals(AvailableRoles.ROLE_USER.getData()));
//        Assert.assertTrue("failure - expected HTTP response 'error' to be '" + Responses.SUCCESS.getData()  + " '",
//                LURDTO.getError().equals(Responses.SUCCESS.getData() ));
//    }
//}
