/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.periferal;

import com.tech.models.entities.embeddedIds.CRBlacklistComposite;
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
public class CRBlacklistCompositeTest {
    private CRBlacklistComposite CRB;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {        
        CRB = new CRBlacklistComposite(1L, 2L);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEqualsSuccess(){
        CRBlacklistComposite CRB2 = new CRBlacklistComposite(1L, 2L);
        
        Assert.assertTrue("failure - expected to be the same", CRB.equals(CRB2));        
    }
    
    @Test
    public void testEqualFailOnNumber(){        
        CRBlacklistComposite CRB2 = new CRBlacklistComposite(1L, 3L);
        
        Assert.assertFalse("failure - expected numbers to be diff", CRB.equals(CRB2));       
    }
    
    @Test
    public void testEqualFailDate(){        
        CRBlacklistComposite CRB2 = new CRBlacklistComposite(3L, 2L);
        
        Assert.assertFalse("failure - expected dates to be diff", CRB.equals(CRB2));       
    }
    
    @Test
    public void testHashcode(){
        CRBlacklistComposite CRB2 = new CRBlacklistComposite(1L, 2L);
        int i = CRB.hashCode();
        int x = CRB2.hashCode();
        
        Assert.assertEquals(x, i);
    }
    
    @Test
    public void testNullEquals(){
        Assert.assertFalse(CRB.equals(null));
    }
    
    @Test
    public void testWrongEquals(){
        Assert.assertFalse(CRB.equals(this));
    }
    
    @Test
    public void testEqualsSameObject(){
        Assert.assertTrue(CRB.equals(CRB));
    }
}
