/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators;

import com.tech.models.dtos.superclass.BaseDTO;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomCreationDTOValidatorTest {
    
    public ChatroomCreationDTOValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of validate method, of class ChatroomCreationDTOValidator.
     */
    @Test
    public void testValidate() {
//        System.out.println("validate");
//        BaseDTO DTO = null;
//        ChatroomCreationDTOValidator instance = new ChatroomCreationDTOValidator();
//        boolean expResult = false;
//        boolean result = instance.validate(DTO);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of response method, of class ChatroomCreationDTOValidator.
     */
    @Test
    public void testResponse() {
//        System.out.println("response");
//        ChatroomCreationDTOValidator instance = new ChatroomCreationDTOValidator();
//        ResponseEntity expResult = null;
//        ResponseEntity result = instance.response();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    @Test
    public void testHashCode() {
        ChatroomCreationDTOValidator instance = new ChatroomCreationDTOValidator();
        Assert.assertEquals("ChatroomCreationDTO".toLowerCase().hashCode() + "\n"+instance.hashCode(),"ChatroomCreationDTO".toLowerCase().hashCode(),instance.hashCode());
        
    }
    
}
