/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.Attr;
import com.tech.configurations.tools.FileTools;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.ImagesMod;
import com.tech.models.entities.User;
import com.tech.services.ImagesService;
import com.tech.services.UserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
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
    private ImagesController controller;
    
    @BeforeClass
    public static void setUpClass(){
    }
    
    @AfterClass
    public static void tearDownClass() {
    }    
    
    @Before
    @Override
    public void setUp() throws Exception{ 
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/images";
    }
    
    @After
    @Override
    public void tearDown() {   
        super.tearDown();
        uri = null;
        
        try {
            FileTools.deleteTestFiles(new File(Attr.IMAGES_OUTPUT_FOLDER.getData()));
        } catch (IOException ex) {
            Logger.getLogger(ImagesControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testLoadImages() throws Exception{
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("iwanna")).thenReturn(true);
        when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
        doNothing().when(imagesService).addImage(any(ImagesMod.class));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "iwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("iwanna");
        verify(imagesService,times(1)).addImage(any(ImagesMod.class));
        verify(userService,times(1)).getUserByUsername("iwanna");
        
        Assert.assertEquals("Fail expected status 200 but was " + status, 200, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.SUCCESS.getData() + "'",
                content.equals(Responses.SUCCESS.getData()));        
    }   
    
    @Test
    public void testLoadImagesNull() throws Exception{
        byte[] f = null;
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("iwanna")).thenReturn(true);
        when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
        doNothing().when(imagesService).addImage(any(ImagesMod.class));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "iwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("iwanna");
        verify(imagesService,times(0)).addImage(any(ImagesMod.class));
        verify(userService,times(1)).getUserByUsername("iwanna");
        
        Assert.assertEquals("Fail expected status 204 but was " + status, 204, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.FILE_WAS_EMPTY.getData() + "'",
                content.equals(Responses.FILE_WAS_EMPTY.getData()));        
    }    
    
    @Test
    public void testLoadImagesWrongUsername() throws Exception{
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("notIwanna")).thenReturn(false);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "notIwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("notIwanna");
        verify(imagesService,times(0)).addImage(any(ImagesMod.class));
        verify(userService,times(0)).getUserByUsername(anyString());
        
        Assert.assertEquals("Fail expected status 404 but was " + status, 404, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.NOT_AVAILABLE.getData() + "'",
                content.equals(Responses.NOT_AVAILABLE.getData()));        
    }

    @Test
    public void testLoadImagesUsernameWrongFormat() throws Exception {
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "5Iwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(0)).checkUsername(anyString());
        verify(imagesService,times(0)).addImage(any(ImagesMod.class));
        verify(userService,times(0)).getUserByUsername(anyString());
        
        Assert.assertEquals("Fail expected status 406 but was " + status, 406, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "'",
                content.equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));        
    }
    
    @Test
    public void testLoadImagesPathExists() throws Exception{
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("iwanna")).thenReturn(true);
        when(userService.getUserByUsername("iwanna")).thenReturn(new User(2L,"iwanna","iwanna",true));
        doNothing().when(imagesService).addImage(any(ImagesMod.class));
        
        File file = new File("C:\\FizData\\Images\\2");
        Assert.assertTrue(file.exists()); //checks if the folder exists before the controller call
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "iwanna"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("iwanna");
        verify(imagesService,times(1)).addImage(any(ImagesMod.class));
        verify(userService,times(1)).getUserByUsername("iwanna");
        
        Assert.assertTrue(file.exists()); //verifies that folder still exists after the controller call
        Assert.assertEquals("Fail expected status 200 but was " + status, 200, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.SUCCESS.getData() + "'",
                content.equals(Responses.SUCCESS.getData()));        
    } 
    
    @Test
    public void testLoadImagesPathDoesntExist() throws Exception{
        byte[] f = Files.readAllBytes(new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg").toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);     
        
        when(userService.checkUsername("someoneElse")).thenReturn(true);
        when(userService.getUserByUsername("someoneElse")).thenReturn(new User(5L,"someoneElse","someoneElse",true));
        doNothing().when(imagesService).addImage(any(ImagesMod.class));
        
        File file = new File("C:\\FizData\\Images\\5");
        Assert.assertFalse(file.exists()); //checks if the folder exists before the controller call
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(uri)
                .file(MF)
                .param("username", "someoneElse"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
               
        verify(userService,times(1)).checkUsername("someoneElse");
        verify(imagesService,times(1)).addImage(any(ImagesMod.class));
        verify(userService,times(1)).getUserByUsername("someoneElse");
        
        Assert.assertTrue(file.exists()); //verifies that folder still exists after the controller call
        Assert.assertEquals("Fail expected status 200 but was " + status, 200, status);
        Assert.assertTrue("Fail expected Response Body to be '" + Responses.SUCCESS.getData() + "'",
                content.equals(Responses.SUCCESS.getData()));        
        
        FileTools.deleteFullDirectory(file);
    }  
    
    @Test
    public void testHandleCorrectImage() throws IOException, Exception{
        /* 
            Google suggestion.. DONT mock a file.. just create it.
        */
        File file = new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg");
        ImagesMod Im = new ImagesMod(2L);
        Im.setImagePath(file.getPath());
        
        when(imagesService.checkImagesByHashtag(12345678L)).thenReturn(true);
        when(imagesService.getImageByHashtag(12345678L)).thenReturn(Im);             

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/get/12345678.jpg"))
                .andReturn();
        
        verify(imagesService,times(1)).checkImagesByHashtag(12345678L);
        verify(imagesService,times(1)).getImageByHashtag(12345678L);
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertFalse("Failure - expected response to have a body",content.isEmpty());
        Assert.assertTrue("Failure - expected response status to be '200'",status == 200);
    }
    
    @Test
    public void testHandleWrongImage() throws Exception {        
        when(imagesService.checkImagesByHashtag(12345678L)).thenReturn(false);     

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/get/12345678.jpg"))
                .andReturn();
        
        verify(imagesService,times(1)).checkImagesByHashtag(12345678L);
        verify(imagesService,times(0)).getImageByHashtag(anyLong());
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        Assert.assertFalse("Failure - expected response to have a body",content.isEmpty());
        Assert.assertTrue("Failure - expected response status to be '200'",status == 200);      
    }
    
    @Test
    public void testHandleWrongExtension() throws Exception {        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri + "/get/123124.png")).andReturn();        
        
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        
        verify(imagesService,times(0)).checkImagesByHashtag(anyLong());
        verify(imagesService,times(0)).getImageByHashtag(anyLong());
        
        Assert.assertTrue("Failure - expected response body to be null",content.isEmpty());
        Assert.assertTrue("Failure - expected response status to be '200'",status == 200);      
    }
}
