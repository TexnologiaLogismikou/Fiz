/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.ImagesMod;
import com.tech.services.interfaces.IImagesService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
    ImagesMod images2 = null;
    
    public ImagesServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    @Sql(scripts = "classpath:clearImages.sql")
    public void setUp() {
        ClassLoader cl = getClass().getClassLoader();
        
        File fi = new File(cl.getResource("testImg.jpg").getFile());
        File fi2 = new File(cl.getResource("testImg2.jpg").getFile());
                
        byte[] fileContent = null;
        byte[] fileContent2 = null;
                
        try {
            fileContent = Files.readAllBytes(fi.toPath());
            fileContent2 = Files.readAllBytes(fi2.toPath());
            
        } catch (IOException ex) {
           Assert.fail("file not found - " + ex.getMessage());
        }
        
        ImagesMod imagemode = new ImagesMod(0L,"Fiz",fileContent);  //etoimazei mia eikona
        ImagesMod imagemode2 = new ImagesMod(1L,"Fiz2",fileContent2);
                
        service.addImage(imagemode); //pairnei tin kainourgia egkrafi
        service.addImage(imagemode2); //pairnei tin kainourgia egkrafi
        images = new ImagesMod (1L,"Fiz2",fileContent2);
        images2 = new ImagesMod (0L,"Fiz",fileContent);
    }
    
    @After
    public void tearDown() {
         //images = null;
    }

    @Test
    public void testGetImageByID() {
//       
        Assert.assertTrue("Failure - expected image to have size",service.getImageByID(1L).getImages().length>0 ); 
        Assert.assertEquals("Failure - expected image has wrong id",1L,service.getImageByID(1L).getID());
        Assert.assertEquals("Failure - expected image has wrong name","Fiz2",service.getImageByID(1L).getName());
        
    }    
    
    @Test
    public void testGetImageByName() {
      
        Assert.assertEquals("Failure - expected images has wrong name","Fiz",service.getImageByName("Fiz").getName());
        Assert.assertEquals("Failure - expected images has wrong name",0L,service.getImageByName("Fiz").getID());
        Assert.assertTrue("Failure - expected image to have size",service.getImageByName("Fiz").getImages().length >0);
    }
    
    @Test
    public void testAddImage(){
        
        ClassLoader cl = getClass().getClassLoader();
        
        File fi = new File(cl.getResource("testImg3.jpg").getFile());
        byte[] fileContent = null;
        System.out.println(fi.toPath());
        try {
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (IOException ex) {
           Assert.fail("file not found" + ex.getMessage());
        }
        
        ImagesMod imagemode = new ImagesMod(2L,"Fiz",fileContent);  //etoimazei mia eikona
        service.addImage(imagemode); //pairnei tin kainourgia egkrafi
        Assert.assertEquals("Failed to add the new image",imagemode.getID(),service.getImageByID(imagemode.getID()).getID());
    }
    
    @Test
    public void testGetAllImages(){
    ClassLoader cl = getClass().getClassLoader();
        
        File fi = new File(cl.getResource("testImg.jpg").getFile());
        File fi2 = new File(cl.getResource("testImg2.jpg").getFile());
        
        byte[] fileContent = null;
        byte[] fileContent2 = null;
        
        try {
            fileContent = Files.readAllBytes(fi.toPath());
            fileContent2 = Files.readAllBytes(fi2.toPath());
        } catch (IOException ex) {
           Assert.fail("file not found - " + ex.getMessage());
        }
        
        List<ImagesMod> list = new ArrayList();
    
        list.add(new ImagesMod(0L,"Fiz",fileContent));
        list.add(new ImagesMod(1L,"Fiz2",fileContent2));
        
        List<ImagesMod> list2 = service.getAllImages();
        
        Assert.assertEquals("Failed to get all the images",list.get(0).getID(),list2.get(0).getID());
    }

    @Test
     public void testDeleteImage(){
        service.deleteImage(images);
        //Assert.assertNotEquals(service.getImageByID(0L),images);
        //Assert.assertNull(service.getImageByID(1L));
        Assert.assertFalse("Image wasnt deleted", service.checkImagesByName(images.getName()));
    }
     
    
    @Test
    public void testCheckImagesTrue(){ 
        Assert.assertTrue("Image wasnt found", service.checkImagesByName(images.getName()));
    }
    
    @Test
    public void testCheckImagesFalse(){ 
         Assert.assertFalse("Image was found", service.checkImagesByName("PictureDoesntExist"));
    }
    
    @Test
    public void testGetNextID(){
        Assert.assertEquals("Wrong id - expected 2",2,service.getNextID());
    }
    
    @Test
    public void testGetCount(){
        Assert.assertEquals("Count was wrong. - expected 2",2,service.getCount());
    }
    }
     
    
    
    
