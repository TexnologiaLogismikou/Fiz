/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import org.junit.Assert;
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
public class EmptyNumberValidatorTest {
    
    public EmptyNumberValidatorTest() {
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
    public void testValidateSuccess() {

        INumberValidator numberValidator = new EmptyNumberValidator();

        Long i = 100L;
        Pair<Boolean,ResponseEntity> answer = numberValidator.validate(i);

        Assert.assertEquals("Failure - expected True but the answer was False",answer.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+ Responses.SUCCESS.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(),new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK));
    }
    @Test
    public void testValidateFail() {

        INumberValidator numberValidator = new EmptyNumberValidator();

        Long i = null;
        Pair<Boolean,ResponseEntity> answer = numberValidator.validate(i);

        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
        Assert.assertEquals("Failure - expected '"+Responses.BAD_COORDINATES.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.BAD_COORDINATES.getData(),HttpStatus.UNPROCESSABLE_ENTITY));
    }
}
