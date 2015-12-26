/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.periferal;

import com.tech.models.entities.embeddedIds.ChatroomEntitiesComposite;
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
public class ChatroomEntitiesCompositeTest {
    private ChatroomEntitiesComposite CEC;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {        
        CEC = new ChatroomEntitiesComposite(1L, 2L);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEqualsSuccess(){
        ChatroomEntitiesComposite CEC2 = new ChatroomEntitiesComposite(1L, 2L);
        
        Assert.assertTrue("failure - expected to be the same", CEC.equals(CEC2));        
    }
    
    @Test
    public void testEqualFailOnNumber(){        
        ChatroomEntitiesComposite CEC2 = new ChatroomEntitiesComposite(1L, 3L);
        
        Assert.assertFalse("failure - expected numbers to be diff", CEC.equals(CEC2));       
    }
    
    @Test
    public void testEqualFailDate(){        
        ChatroomEntitiesComposite CEC2 = new ChatroomEntitiesComposite(3L, 2L);
        
        Assert.assertFalse("failure - expected dates to be diff", CEC.equals(CEC2));       
    }
    
    @Test
    public void testHashcode(){
        ChatroomEntitiesComposite CEC2 = new ChatroomEntitiesComposite(1L, 2L);
        int i = CEC.hashCode();
        int x = CEC2.hashCode();
        
        Assert.assertEquals(x, i);
    }
}
