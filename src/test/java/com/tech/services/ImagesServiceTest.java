/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.ImagesMod;
import com.tech.services.interfaces.IImagesService;
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
        Assert.fail("cant upload image yet");
    }
    
    
}
