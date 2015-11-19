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
import java.util.logging.Level;
import org.junit.After;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ΙΩΑΝΝΑ
 */
@Transactional
public class ImagesServiceTest extends AbstractTest{
    @Autowired
    private IImagesService service;
    private List<User> list = null;    
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
    public void setUp() {
    }
    
    @After
    public void tearDown() {
         //images = null;
    }

    @Test
    public void testGetImageByID() {
        long id = 0L;
        ImagesMod newImg = service.getImageByID(id);
        Assert.assertTrue("Failure - expected image to have size",newImg.getImages().length >0);
        Assert.assertEquals("Failure - expected image has wrong id",newImg.getID(),id);
        Assert.assertEquals("Failure - expected image has wrong name","iwanna",newImg.getName());
        // TODO review the generated test code and remove the default call to fail.
    }    
    
    @Test
    public void testGetImageByName() {
        String name = "iwanna";
        long id=0L;
        ImagesMod newString = service.getImageByName(name);
        Assert.assertEquals("Failure - expected images has wrong name","iwanna",newString.getName());
        Assert.assertEquals("Failure - expected images has wrong name",newString.getID(),id);
        Assert.assertTrue("Failure - expected image to have size",newString.getImages().length >0);
    }
    
    @Test
    public void testAddImage(){
        
        ClassLoader cl = getClass().getClassLoader();
        
        File fi = new File(cl.getResource("testImg.jpg").getFile());
        byte[] fileContent = null;
        System.out.println(fi.toPath());
        try {
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (IOException ex) {
           Assert.fail("file not found");
        }
        
        ImagesMod imagemode = new ImagesMod(3L,"Fiz",fileContent);  //etoimazei mia eikona
        service.addImage(imagemode); //pairnei tin kainourgia egkrafi
        Assert.assertEquals("Fail add images",imagemode.getID(),service.getImageByID(imagemode.getID()).getID());
    }
    
      
}
