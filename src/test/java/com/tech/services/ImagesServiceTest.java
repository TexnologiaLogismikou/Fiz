package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.ImagesMod;
import com.tech.services.interfaces.IImagesService;
import com.tech.services.interfaces.IUserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 *
 * @author iwann
 */
@Transactional
public class ImagesServiceTest extends AbstractTest{
    
    @Autowired
    private IImagesService service;
    
    private ImagesMod images;
    private ImagesMod images2;
        
    public ImagesServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException{
        ClassLoader cl = getClass().getClassLoader(); //pairnw to path tiw eikonas 
                
        File file = new File(cl.getResource("images/testImg.jpg").getFile());
        File file2 = new File(cl.getResource("images/testImg2.jpg").getFile());  //thelw na parei tin ekona pou exw swsmeni
        
        Long userid = 1L; //poios xristis tha anebasei tn eikona
        Long userid2 = 2L;
        
        byte[] bytes = Files.readAllBytes(file.toPath()); //metatrepw to path se enan pinaka apo byte[]
        byte[] bytes2 = Files.readAllBytes(file2.toPath());
            
            ImagesMod img = new ImagesMod(userid);
            images = img;
            ImagesMod img2 = new ImagesMod(userid2);
            images2 = img2;
            
            
            File newFile = new File(img.getImagePath());
            if (!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs(); //               
            }
            File newFile2 = new File(img2.getImagePath());
            if (!newFile2.getParentFile().exists()){
                newFile2.getParentFile().mkdirs(); //              
            }
            
            Files.write(newFile.toPath(), bytes, StandardOpenOption.CREATE);//dimiourgite to arxeio
            Files.write(newFile2.toPath(), bytes2, StandardOpenOption.CREATE);//dimiourgite to arxeio           
  
            service.addImage(img);  //apothikeuete /kataxwrite i eikona meta sto table images
            service.addImage(img2);
    }
   
    @After
    public void tearDown() throws IOException {
       Files.delete(new File(images.getImagePath()).toPath());
       Files.delete(new File(images2.getImagePath()).toPath());
       //Files.delete(new File("C:\\FizData\\Images\\3").toPath());
       images = null;
       images2 = null;
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
       Assert.assertNotNull("expected image",service.getImageByHashtag(images.getHashtag()));
    }

    
    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testAddImage() throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        
        File file3 = new File(cl.getResource("images/testImg3.jpg").getFile());
        Long userid3 = 3L;
        
        byte[] bytes3 = Files.readAllBytes(file3.toPath());
        
        ImagesMod img3 = new ImagesMod(userid3);
        
        File newFile3 = new File(img3.getImagePath());
        if (!newFile3.getParentFile().exists()){
            newFile3.getParentFile().mkdirs(); //   
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
            if (vLookUp.getHashtag() == images.getHashtag()) {
                tmp = vLookUp;
            }
        }
        Assert.assertNotNull("Fail Get all Images",tmp);
    }

    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testDeleteImage() {
        service.deleteImage(images);
        Assert.assertFalse("Fail Delete Image",service.checkImagesByHashtag(images.getHashtag()));       
    }

    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testCheckImagesByHashtag() {
        Assert.assertEquals("Fail check images by hashtag",images.getHashtag(),service.getImageByHashtag(images.getHashtag()).getHashtag());
    }

    @Test
    @Sql(scripts = "classpath:clearImages.sql")
    public void testGetCount() {
        System.out.println(service.getCount());
        Assert.assertEquals("Fail get Count",2L,service.getCount());
    }
    
}
