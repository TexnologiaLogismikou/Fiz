/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.superclass;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class StringValidatorTest {
    
    public StringValidatorTest() {
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

    @Test
    public void testSetNext(){
        NoSpacesValidator NSV = new NoSpacesValidator();
        try{
            NSV.setNext(null);            
        }catch (InappropriateValidatorException ex) {            
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } catch (Exception ex ){            
            Assert.fail("Should get InappropriateValidatorException");
        }
    }
    @Test
    public void testReplaceNext() throws Exception {
        NoSpacesValidator NSV = new NoSpacesValidator();
        try{
            NSV.replaceNext(null);            
        }catch (InappropriateValidatorException ex) {            
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } catch (Exception ex ){            
            Assert.fail("Should get InappropriateValidatorException");
        }
    }    
}
