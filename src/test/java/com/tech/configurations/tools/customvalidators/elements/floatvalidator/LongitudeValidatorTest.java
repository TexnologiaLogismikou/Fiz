/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.floatvalidator;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.IFloatValidator;
import static java.lang.Float.NaN;
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
public class LongitudeValidatorTest {
    
    public LongitudeValidatorTest() {
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
    public void testValidateFailMax() {
        IFloatValidator floatValidator = new LongitudeValidator();
        
        Pair<Boolean ,ResponseEntity> answer = floatValidator.validate(Float.POSITIVE_INFINITY);
        
        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
        Assert.assertEquals("Failure - expected '"+Responses.BAD_COORDINATES.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.BAD_COORDINATES,HttpStatus.UNPROCESSABLE_ENTITY));
    }
    
    @Test
    public void testValidateFailMin() {
        IFloatValidator floatValidator = new LongitudeValidator();
        
        Pair<Boolean ,ResponseEntity> answer = floatValidator.validate(Float.NEGATIVE_INFINITY);
        
        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
        Assert.assertEquals("Failure - expected '"+Responses.BAD_COORDINATES.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.BAD_COORDINATES,HttpStatus.UNPROCESSABLE_ENTITY));
    }
    
    @Test
    public void testValidateSuccess() {
        IFloatValidator floatValidator = new LongitudeValidator();
        
        Pair<Boolean ,ResponseEntity> answer = floatValidator.validate(50f);
        
        Assert.assertEquals("Failure - expected True but the answer was False",answer.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+Responses.SUCCESS.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.SUCCESS,HttpStatus.OK));
    }
    
//    @Test
//    public void testValidateFailNull() {
//        IFloatValidator floatValidator = new LongitudeValidator();
//        Float a = null;
//        Pair<Boolean ,ResponseEntity> answer = floatValidator.validate(a);
//        
//        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
//        Assert.assertEquals("Failure - expected '"+Responses.BAD_COORDINATES.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
//                answer.getRight(), new ResponseEntity<>(Responses.BAD_COORDINATES,HttpStatus.UNPROCESSABLE_ENTITY));
//    }
}
