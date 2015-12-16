/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.stringvalidators;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author KuroiTenshi
 */
public class MatchValidatorTest {

    public MatchValidatorTest() {
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

        String test = "testString";
        IStringValidator stringValidator = new MatchValidator("/testString/");
        Pair<Boolean, ResponseEntity> answer = stringValidator.validate(test);

        Assert.assertEquals("Failure - expected true but the answer was false", Boolean.TRUE, answer.getLeft());
        Assert.assertEquals("Failure - expected '" + Responses.SUCCESS.getData() + "' but the answer was '" + answer.getRight().getBody() + "'",
                answer.getRight(), new ResponseEntity<>(Responses.SUCCESS, HttpStatus.OK));


    }

    @Test
    public void testValidateFail() {

        String test = "testString";
        IStringValidator stringValidator = new MatchValidator("");
        Pair<Boolean, ResponseEntity> answer = stringValidator.validate(test);

        Assert.assertNotEquals("Failure - expected false but the answer was true", Boolean.TRUE, answer.getLeft());
        Assert.assertEquals("Failure - expected '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData() + "' but the answer was '" + answer.getRight().getBody() + "'",
                answer.getRight(), new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE));


    }

}
