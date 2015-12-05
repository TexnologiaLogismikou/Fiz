/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.configurations.tools.Attr;
import com.tech.models.entities.ImagesMod;
import com.tech.repositories.IImagesRepository;
import com.tech.repositories.IUserRepository;
import com.tech.services.interfaces.IImagesService;
import com.tech.services.interfaces.IUserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author iwann
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class ImagesServiceTest extends AbstractTest{
    
    @Autowired
    private IImagesService service;
    
    private ImagesMod imagesExist = null;
    private ImagesMod images2Exist = null;
    
    public ImagesServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
                
        File file = new File(Attr.TESTING_IMAGES.getData() + "\\testImg.jpg");
        File file2 = new File(Attr.TESTING_IMAGES.getData() + "\\testImg2.jpg");  //thelw na parei tin ekona pou exw swsmeni
        
        byte[] bytes = Files.readAllBytes(file.toPath()); //metatrepw to path se enan pinaka apo byte[]
        byte[] bytes2 = Files.readAllBytes(file2.toPath());
        
        imagesExist = new ImagesMod(1L);
        images2Exist = new ImagesMod(2L);
        
        File newFile = new File(imagesExist.getImagePath());
        if (!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs(); //               
        }
        File newFile2 = new File(images2Exist.getImagePath());
        if (!newFile2.getParentFile().exists()){
            newFile2.getParentFile().mkdirs(); //              
        }
        
        Files.write(newFile.toPath(), bytes, StandardOpenOption.CREATE);//dimiourgite to arxeio
        Files.write(newFile2.toPath(), bytes2, StandardOpenOption.CREATE);//dimiourgite to arxeio
        
        service.addImage(imagesExist);
        service.addImage(images2Exist);
    }
    
    @After
    public void tearDown() {
        imagesExist = null;
        images2Exist = null;
    }

   
    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testGetImageByUserID() {
        List<ImagesMod> list = service.getImageByUserID(1L);
        int i = 0;
        for(ImagesMod vLookUp:list){
            Assert.assertEquals("Found wrong id on the " + i + " record",1L,vLookUp.getUserID());
            i++;
        }
        
    }

   
    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testGetImageByHashtag() {
        Assert.assertNotNull("expected image",service.getImageByHashtag(imagesExist.getHashtag()));
    }

    
    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testAddImage() throws IOException {
        File file3 = new File(Attr.TESTING_IMAGES.getData() + "\\testImg3.jpg");
        Long userid3 = 3L;
        
        byte[] bytes3 = Files.readAllBytes(file3.toPath());
        
        ImagesMod img3 = new ImagesMod(userid3);
        
        File newFile3 = new File(img3.getImagePath());
        if (!newFile3.getParentFile().exists()){
            newFile3.getParentFile().mkdirs();    
            }
        Files.write(newFile3.toPath(), bytes3, StandardOpenOption.CREATE);//dimiourgite to arxeio
        service.addImage(img3); 
       
        Assert.assertEquals("Faileresesrreer",img3.getHashtag(),service.getImageByHashtag(img3.getHashtag()).getHashtag());             
        
    }

   @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testGetAllImages() {
        ImagesMod tmp = null ;
        for(ImagesMod vLookUp:service.getImageByUserID(1L)){
            if (vLookUp.getHashtag() == imagesExist.getHashtag()) {
                tmp = vLookUp;
            }
        }
        Assert.assertNotNull("Fail Get all Images",tmp);
    }

    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testDeleteImage() {
        service.deleteImage(imagesExist);
        Assert.assertFalse("Fail Delete Image",service.checkImagesByHashtag(imagesExist.getHashtag()));       
    }

    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testCheckImagesByHashtag() {
        Assert.assertEquals("Fail check images by hashtag",imagesExist.getHashtag(),service.getImageByHashtag(imagesExist.getHashtag()).getHashtag());
    }

    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testGetCount() {
        System.out.println(service.getCount());
        Assert.assertEquals("Fail get Count",2L,service.getCount());
    }
}
