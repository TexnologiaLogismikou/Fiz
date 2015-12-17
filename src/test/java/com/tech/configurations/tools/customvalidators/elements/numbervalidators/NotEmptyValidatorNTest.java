/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.numbervalidators;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class NotEmptyValidatorNTest {
    
    public NotEmptyValidatorNTest() {
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
    public void testValidateFail() {
        /* Responses.ID_INAPPROPRIATE_FORMAT,HttpStatus.UNPROCESSABLE_ENTITY */
        INumberValidator numberValidator = new NotEmptyValidatorN();
        
        Long i = null;
        
        Pair<Boolean,ResponseEntity> answer = numberValidator.validate(i);
        
        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
        Assert.assertEquals("Failure - expected '"+Responses.ID_INAPPROPRIATE_FORMAT.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.ID_INAPPROPRIATE_FORMAT.getData(),HttpStatus.UNPROCESSABLE_ENTITY));
    }
    
    @Test
    public void testValidateSuccess() {
        INumberValidator numberValidator = new NotEmptyValidatorN();
        
        Long i = 100L;
        
        Pair<Boolean,ResponseEntity> answer = numberValidator.validate(i);
        
        Assert.assertEquals("Failure - expected True but the answer was False",answer.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+Responses.SUCCESS.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK));
    }
    
}
