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
    //IUserService userService = null;
    
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
    public void setUp(){
       /* ClassLoader cl = getClass().getClassLoader(); //pairnw to path tiw eikonas 
        String fixedData = "C:\\vol\\images";   //edw tha apothikeutei i eikona
        
        File file = new File(cl.getResource("testImg.jpg").getFile());
        File file2 = new File(cl.getResource("testImg2.jpg").getFile());  //thelw na parei tin ekona pou exw swsmeni
        
        Long userid = 1L; //poios xristis tha anebasei tn eikona
        Long userid2 = 2L;
        
        Date tm = new Date();  // pairnw tin imerominia
        
        Long imgPath = tm.hashCode() + userid.hashCode() + 0L; //dimiourgw to imgPath
        Long imgPath2 = tm.hashCode() + userid2.hashCode() + 0L;
        
        
            byte[] bytes = file.getPath().getBytes();   //metatrepw to path se enan pinaka apo byte[]
            byte[] bytes2 = file2.getPath().getBytes(); 
            
            Path pt = FileSystems.getDefault().getPath(fixedData + "\\" + imgPath + ".jpg"); //kanei ena path gia t pc
        try {
            Files.write(pt,bytes ,StandardOpenOption.CREATE);//dimiourgite to arxeio
        } catch (IOException ex) {
            Logger.getLogger(ImagesServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            Path pt2 = FileSystems.getDefault().getPath(fixedData + "\\" + imgPath2 + ".jpg"); 
        try {
            Files.write(pt2,bytes2 ,StandardOpenOption.CREATE);
        } catch (IOException ex) {
            Logger.getLogger(ImagesServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            ImagesMod img = new ImagesMod(userid,tm );
            images = img;
            ImagesMod img2 = new ImagesMod(userid2,tm );
            
            service.addImage(img);  //apothikeuete /kataxwrite i eikona meta sto table images
            service.addImage(img2);*/
          
    }
    @After
    public void tearDown() {
    }

    @Test
    public void testGetImageByUserID() {
        
    }

   
    @Test
    public void testGetImageByHashtag() {
       // Assert.assertNotNull("expected image",service.getImageByHashtag(images.getHashtag()));
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
