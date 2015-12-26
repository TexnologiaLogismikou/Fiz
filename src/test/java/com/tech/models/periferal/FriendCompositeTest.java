/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.periferal;

import com.tech.models.entities.embeddedIds.FriendComposite;
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
public class FriendCompositeTest {
    private FriendComposite FC;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {        
        FC = new FriendComposite(1L, 2L);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEqualsSuccess(){
        FriendComposite FC2 = new FriendComposite(1L, 2L);
        
        Assert.assertTrue("failure - expected to be the same", FC.equals(FC2));        
    }
    
    @Test
    public void testEqualFailOnNumber(){        
        FriendComposite FC2 = new FriendComposite(1L, 3L);
        
        Assert.assertFalse("failure - expected numbers to be diff", FC.equals(FC2));       
    }
    
    @Test
    public void testEqualFailDate(){        
        FriendComposite FC2 = new FriendComposite(3L, 2L);
        
        Assert.assertFalse("failure - expected dates to be diff", FC.equals(FC2));       
    }
    
    @Test
    public void testHashcode(){
        FriendComposite FC2 = new FriendComposite(1L, 2L);
        int i = FC.hashCode();
        int x = FC2.hashCode();
        
        Assert.assertEquals(x, i);
    }
    
    @Test
    public void testNullEquals(){
        Assert.assertFalse(FC.equals(null));
    }
    
    @Test
    public void testWrongEquals(){
        Assert.assertFalse(FC.equals(this));
    }
    
    @Test
    public void testEqualsSameObject(){
        Assert.assertTrue(FC.equals(FC));
    }
}
