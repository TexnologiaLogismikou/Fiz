/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.ImagesMod;
import com.tech.models.entities.User;
import com.tech.services.interfaces.IImagesService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ΙΩΑΝΝΑ
 */
@Transactional
public class ImagesServiceTest extends AbstractTest{
    @Autowired
    private IImagesService service;
    
    ImagesMod images = null;
    
    public ImagesServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Sql(scripts = "classpath:clearImgDB.sql")
    public void setUp() {
        ClassLoader cl = getClass().getClassLoader();
        
        File fi = new File(cl.getResource("images/testImg.jpg").getFile());
        byte[] fileContent = null;
        System.out.println(fi.toPath());
        try {
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (IOException ex) {
           Assert.fail("file not found" + ex.getMessage());
        }
        
        ImagesMod images2 = new ImagesMod(service.getNextID(),"psari",fileContent);  //etoimazei mia eikona
        service.addImage(images2); //pairnei tin kainourgia egkrafi
    }
    
    @After
    public void tearDown() {
         images = null;
    }

    @Test
    public void testGetImageByID() {
        long id = 1L;
        images = service.getImageByID(id);
        Assert.assertTrue("Failure - expected image to have size",images.getImages().length >0);
        Assert.assertEquals("Failure - expected image has wrong id",images.getID(),id);
        Assert.assertEquals("Failure - expected image has wrong name","psari",images.getName());
        // TODO review the generated test code and remove the default call to fail.
    }    
    
    @Test
    public void testGetImageByName() {
        String name = "psari";
        long id=1L;
        images = service.getImageByName(name);
        Assert.assertEquals("Failure - expected images has wrong name","psari",images.getName());
        Assert.assertEquals("Failure - expected images has wrong name",id,images.getID());
        Assert.assertTrue("Failure - expected image to have size",images.getImages().length >0);
    }
    
    @Test
    public void testAddImage(){
        
        ClassLoader cl = getClass().getClassLoader();
        
        File fi = new File(cl.getResource("images/testImg2.jpg").getFile());
        byte[] fileContent = null;
        System.out.println(fi.toPath());
        try {
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (IOException ex) {
           Assert.fail("file not found" + ex.getMessage());
        }
        
        images = new ImagesMod(service.getNextID(),"Fiz",fileContent);  //etoimazei mia eikona
        service.addImage(images); //pairnei tin kainourgia egkrafi
        Assert.assertEquals("Fail add images",images.getID(),service.getImageByID(images.getID()).getID());
    }
    
      
}
