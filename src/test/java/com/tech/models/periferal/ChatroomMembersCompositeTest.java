/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.periferal;

import com.tech.models.entities.embeddedIds.ChatroomMembersComposite;
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
public class ChatroomMembersCompositeTest {
    private ChatroomMembersComposite CMC;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {        
        CMC = new ChatroomMembersComposite(1L, 2L);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEqualsSuccess(){
        ChatroomMembersComposite CMC2 = new ChatroomMembersComposite(1L, 2L);
        
        Assert.assertTrue("failure - expected to be the same", CMC.equals(CMC2));        
    }
    
    @Test
    public void testEqualFailOnNumber(){        
        ChatroomMembersComposite CMC2 = new ChatroomMembersComposite(1L, 3L);
        
        Assert.assertFalse("failure - expected numbers to be diff", CMC.equals(CMC2));       
    }
    
    @Test
    public void testEqualFailDate(){        
        ChatroomMembersComposite CMC2 = new ChatroomMembersComposite(3L, 2L);
        
        Assert.assertFalse("failure - expected dates to be diff", CMC.equals(CMC2));       
    }
    
    @Test
    public void testHashcode(){
        ChatroomMembersComposite CMC2 = new ChatroomMembersComposite(1L, 2L);
        int i = CMC.hashCode();
        int x = CMC2.hashCode();
        
        Assert.assertEquals(x, i);
    }
    
    @Test
    public void testNullEquals(){
        Assert.assertFalse(CMC.equals(null));
    }
    
    @Test
    public void testWrongEquals(){
        Assert.assertFalse(CMC.equals(this));
    }
    
    @Test
    public void testEqualsSameObject(){
        Assert.assertTrue(CMC.equals(CMC));
    }
}
