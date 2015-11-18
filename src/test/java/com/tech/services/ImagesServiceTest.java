package com.tech.services;

import com.tech.AbstractTest;
import com.tech.Models.ImagesMod;
import com.tech.Models.User;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author KuroiTenshi
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
        images = null;
    }

    /**
     * Test of getImageByID method, of class ImagesService.
     */
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
