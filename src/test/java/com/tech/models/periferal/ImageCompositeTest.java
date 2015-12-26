/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.periferal;

import com.tech.models.entities.embeddedIds.ImageComposite;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author KuroiTenshi
 */
public class ImageCompositeTest {
    
    private Date dateNow = new Date();
    private ImageComposite IC;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {        
        IC = new ImageComposite(1L, dateNow);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEqualsSuccess(){
        ImageComposite IC2 = new ImageComposite(1L, dateNow);
        
        Assert.assertTrue("failure - expected to be the same", IC.equals(IC2));        
    }
    
    @Test
    public void testEqualFailOnNumber(){        
        ImageComposite IC2 = new ImageComposite(2L, dateNow);
        
        Assert.assertFalse("failure - expected numbers to be diff", IC.equals(IC2));       
    }
    
    @Test
    public void testEqualFailDate(){        
        ImageComposite IC2 = new ImageComposite(1L, java.sql.Date.valueOf("2015-12-18"));
        
        Assert.assertFalse("failure - expected dates to be diff", IC.equals(IC2));       
    }
    
    @Test
    public void testHashcode(){
        ImageComposite IC2 = new ImageComposite(1L, dateNow);
        int i = IC.hashCode();
        int x = IC2.hashCode();
        
        Assert.assertEquals(x, i);
    }
}
