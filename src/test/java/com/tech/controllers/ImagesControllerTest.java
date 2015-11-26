/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.tools.FileTools;
import com.tech.models.entities.ImagesMod;
import com.tech.services.interfaces.IImagesService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author iwann
 */
@Transactional
public class ImagesControllerTest extends AbstractControllerTest{
    private String url;
    private ClassLoader cl = getClass().getClassLoader(); 
    
    @Autowired
    private IImagesService service;
    
    private ImagesMod images;
    
    public ImagesControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        url = "/images";
        
        
        File file = new File(cl.getResource("images/testImg.jpg").getFile());
        Long userid = 1L;
        try {
        
            byte[] bytes = Files.readAllBytes(file.toPath());
        
            ImagesMod img = new ImagesMod(userid);
            images = img;
        
            File newFile = new File(img.getImagePath());
                if (!newFile.getParentFile().exists()){
                    newFile.getParentFile().mkdirs();                
                }
            
       
            Files.write(newFile.toPath(), bytes, StandardOpenOption.CREATE);
        
            service.addImage(img);
            
        } catch (IOException ex) {
            Logger.getLogger(ImagesControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @After
    public void tearDown() throws IOException {
        FileTools.deleteDirectory(new File(images.getImagePath()).getParentFile().getParentFile());
        images = null;
    }

    /**
     * Test of loadImages method, of class ImagesController.
     */
    @Test
    public void testLoadImages() throws Exception {
        byte[] f = Files.readAllBytes(new File(cl.getResource("images/testImg.jpg").getFile()).toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);        
                
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(url + "/upload")
                .file(MF)
                .param("userid", "1"))
                .andReturn();

        int status = result.getResponse().getStatus();
        String str = result.getResponse().getContentAsString();
        
        Assert.assertEquals("Expected 200\n" + str +"\n",200,status);
        Assert.assertEquals("expected g00d","G00D",str);
    }
    
    @Test
    public void testLoadImagesNoUser() throws Exception {
        byte[] f = Files.readAllBytes(new File(cl.getResource("images/testImg.jpg").getFile()).toPath());
        MockMultipartFile MF = new MockMultipartFile("file","psaraki.jpg", "multipart/form-data", f);        
                
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(url + "/upload")
                .file(MF)
                .param("userid", "911"))
                .andReturn();

        int status = result.getResponse().getStatus();
        String str = result.getResponse().getContentAsString();
        
        Assert.assertEquals("Expected 404\n" + str +"\n",404,status);
        Assert.assertEquals("expected User doesnt exist","User doesnt exist",str);
    }

    @Test
    public void testLoadImagesEmpty() throws Exception {
        byte[] f = null;
        MockMultipartFile MF = new MockMultipartFile("file","1001002586287.jpg", "multipart/form-data", f);        
                
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .fileUpload(url + "/upload")
                .file(MF)
                .param("userid", "1"))
                .andReturn();

        int status = result.getResponse().getStatus();
        String str = result.getResponse().getContentAsString();
        
        Assert.assertEquals("Expected 404\n" + str +"\n",404,status);
        Assert.assertEquals("expected empty","empty",str);
    }

    /**
     * Test of handle method, of class ImagesController.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testHandle() throws Exception {
         MvcResult result = mvc.perform(MockMvcRequestBuilders 
                 .post(url + "/get/1001002586287.jpg")) //me ti methodo post ektelei to url kai pernei tin eikona pou tou balame
                 .andReturn(); //kai edw gurnaei afwse? sw auto pou girnaei dn einai aplai eikona.. einai ena HttpResponse
          //pou perixe
        Assert.assertTrue("Image didnt found", result.getResponse().getContentAsString().trim().length() > 0);
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testHandleFail() throws Exception {
         MvcResult result = mvc.perform(MockMvcRequestBuilders 
                 .post(url + "/get/1001002586281237.jpg")) //me ti methodo post ektelei to url kai pernei tin eikona pou tou balame
                 .andReturn(); //kai edw gurnaei afwse? sw auto pou girnaei dn einai aplai eikona.. einai ena HttpResponse
          //pou perixe
        Assert.assertTrue("Image didnt found", result.getResponse().getContentAsString().trim().length() > 0);
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testHandleFailv2() throws Exception {
         MvcResult result = mvc.perform(MockMvcRequestBuilders 
                 .post(url + "/get/1001002586287.hah")) //me ti methodo post ektelei to url kai pernei tin eikona pou tou balame
                 .andReturn(); //kai edw gurnaei afwse? sw auto pou girnaei dn einai aplai eikona.. einai ena HttpResponse
          //pou perixe
        Assert.assertTrue("Image was found", result.getResponse().getContentAsString().trim().length() == 0);
    }
}
