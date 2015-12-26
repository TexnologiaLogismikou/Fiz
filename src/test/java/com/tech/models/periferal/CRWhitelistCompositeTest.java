/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.periferal;

import com.tech.models.entities.embeddedIds.CRWhitelistComposite;
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
public class CRWhitelistCompositeTest {
    private CRWhitelistComposite CRW;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {        
        CRW = new CRWhitelistComposite(1L, 2L);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEqualsSuccess(){
        CRWhitelistComposite CRW2 = new CRWhitelistComposite(1L, 2L);
        
        Assert.assertTrue("failure - expected to be the same", CRW.equals(CRW2));        
    }
    
    @Test
    public void testEqualFailOnNumber(){        
        CRWhitelistComposite CRW2 = new CRWhitelistComposite(1L, 3L);
        
        Assert.assertFalse("failure - expected numbers to be diff", CRW.equals(CRW2));       
    }
    
    @Test
    public void testEqualFailDate(){        
        CRWhitelistComposite CRW2 = new CRWhitelistComposite(3L, 2L);
        
        Assert.assertFalse("failure - expected dates to be diff", CRW.equals(CRW2));       
    }
    
    @Test
    public void testHashcode(){
        CRWhitelistComposite CRW2 = new CRWhitelistComposite(1L, 2L);
        int i = CRW.hashCode();
        int x = CRW2.hashCode();
        
        Assert.assertEquals(x, i);
    }
}
