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
    private List<ImagesMod> list = null;    
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
    @Sql(scripts = "classpath:clearImages.sql")
    public void setUp() throws IOException{
        ClassLoader cl = getClass().getClassLoader(); //pairnw to path tiw eikonas 
                
        File file = new File(cl.getResource("testImg.jpg").getFile());
        File file2 = new File(cl.getResource("testImg2.jpg").getFile());  //thelw na parei tin ekona pou exw swsmeni
        
        Long userid = 1L; //poios xristis tha anebasei tn eikona
        Long userid2 = 2L;
        
        byte[] bytes = file.getPath().getBytes();   //metatrepw to path se enan pinaka apo byte[]
        byte[] bytes2 = file2.getPath().getBytes(); 
            
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
    }

    @Test
    public void testGetImageByUserID() {
        
    }

   
    @Test
    public void testGetImageByHashtag() {
       Assert.assertNotNull("expected image",service.getImageByHashtag(images.getHashtag()));
    }

    
    @Test
    public void testAddImage() {
        /*ClassLoader cl = getClass().getClassLoader();
        
        File file3 = new File(cl.getResource("testImg3.jpg").getFile());
          Long userid = 3L;
          byte[] bytes3 = file3.getPath().getBytes();
          ImagesMod img3 = new ImagesMod(userid);
            images = img;
            File newFile = new File(img.getImagePath());
            if (!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs(); //   
                try {
            Files.write(newFile.toPath(), bytes, StandardOpenOption.CREATE);//dimiourgite to arxeio
        } catch (IOException ex) {
            Logger.getLogger(ImagesServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
             service.addImage(img); */
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
