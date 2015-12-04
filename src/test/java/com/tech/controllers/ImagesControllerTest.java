/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Attr;
import com.tech.models.entities.Friend;
import com.tech.models.entities.ImagesMod;
import com.tech.models.entities.User;
import com.tech.services.ImagesService;
import com.tech.services.UserInfoService;
import com.tech.services.UserService;
import java.io.File;
import java.nio.file.Files;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author iwann
 */
@Transactional
@ActiveProfiles({"iwanna","iwanna"})
public class ImagesControllerTest extends AbstractControllerTest{
    String uri;
    
    @Mock
    private ImagesService imagesService;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private RegistrationController controller;
    
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
        
        uri = "/images";
    }
    
    @After
    @Override
    public void tearDown() {   
        super.tearDown();
        uri = null;
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testLoadImages() throws Exception{
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
        doNothing().when(imagesService).addImage(any(ImagesMod.class));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri + "/upload")
                .file(MF)
                .param("username", "iwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        System.out.println("===============" + content + status);
               
        verify(imagesService,times(1)).addImage(any(ImagesMod.class));
        verify(userService,times(1)).getUserByUsername("iwanna");
        
        Assert.assertEquals("Fail expected status 200 but was " + status, 200, status);
        Assert.assertTrue("Fail expected Response Body to be 'Success'",content.equals("Success"));
        
     }
    
}
