/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tech.AbstractControllerTest;
import com.tech.models.entities.User;
import com.tech.models.entities.UserInfo;
import com.tech.services.UserInfoService;
import com.tech.services.UserService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import net.minidev.json.JSONObject;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sun.print.resources.serviceui;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
//@ActiveProfiles({"mixalis","mixalis"})
public class RegistrationControllerTestV2 extends AbstractControllerTest{
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
        
//        uri = "http://localhost:8080/Fiz/register";
        uri = "/register";
    }
    
    @After
    public void tearDown() {   
        uri = null;
    }
    
    @Test
    public void testRegisterV2() throws UnsupportedEncodingException, Exception {
        JSONObject json = new JSONObject();
        json.put("username","milena4");
        json.put("password","milena12312314");
        json.put("last_name","iwanna");
        json.put("email","douleuei@teicm.gr");
        json.put("birtday","23-11-94");
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertEquals("Failure - expected HTTP status 200",200, status);
        Assert.assertTrue("Failure - expected HTTP response body to have a value",content.trim().length() > 0);        
    }
}
