package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.ImagesMod;
import com.tech.models.entities.User;
import com.tech.services.interfaces.IImagesService;
import com.tech.services.interfaces.IUserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author iwann
 */
@Transactional
public class ImagesServiceTest extends AbstractTest{
    
    @Autowired
    private IImagesService service;
    private List<ImagesMod> list = null;    
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
        Date tm = new Date();
        list = new ArrayList();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetImageByUserID() {
        
    }

   
    @Test
    public void testGetImageByHashtag() {
        
    }

    
    @Test
    public void testAddImage() {
       
    }

    
    @Test
    public void testGetNextID() {
    }

    
    @Test
    public void testGetAllImages() {
    }

    @Test
    public void testDeleteImage() {
    }

    @Test
    public void testCheckImagesByHashtag() {
    }

    @Test
    public void testGetCount() {
        
    }
    
}
