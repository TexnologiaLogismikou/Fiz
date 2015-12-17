/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.stringvalidators;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class NotEmptyValidatorSTest {
    
    public NotEmptyValidatorSTest() {
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
    public void testValidateNotEmpty() {
        
        IStringValidator stringValidator = new NotEmptyValidatorS();
        
        String str = "";
        
        Pair<Boolean,ResponseEntity> responce = stringValidator.validate(str);
        
        Assert.assertEquals("Failure - expected False but the answer was False",responce.getLeft(), Boolean.FALSE);
        Assert.assertEquals("failure - returned response was '" + responce.getRight() + " '",
                responce.getRight(), new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT.getData(), HttpStatus.NOT_ACCEPTABLE)); 
    }
    
    @Test
    public void testValidateNotNull() {
        
        IStringValidator stringValidator = new NotEmptyValidatorS();
        
        String str = "Billaras";
        
        Pair<Boolean,ResponseEntity> responce = stringValidator.validate(str);
        
        Assert.assertEquals("Failure - expected True but the answer was False",responce.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+Responses.SUCCESS.getData()+"' but the answer was '"+responce.getRight().getBody()+"'",
            responce.getRight(), new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK));
    }
    
}
