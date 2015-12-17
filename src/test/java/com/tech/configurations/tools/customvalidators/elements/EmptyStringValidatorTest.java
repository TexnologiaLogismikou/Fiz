/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotEmptyValidatorS;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import junit.framework.Assert;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class EmptyStringValidatorTest
{
    private static List<String> testStrings = new ArrayList<>();
    private static Pair<Boolean,ResponseEntity> answer;
    private static IStringValidator stringValidator;
    
    public EmptyStringValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        testStrings.add(null);
        testStrings.add("");
        testStrings.add("notNull");
        stringValidator = new EmptyStringValidator();
    }
    
    @AfterClass
    public static void tearDownClass()
    {
        testStrings = null;
        stringValidator = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testValidateFail_NullString()
    {
        answer = stringValidator.validate(testStrings.get(0));
        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
        Assert.assertEquals("Failure - expected '"+Responses.STRING_INAPPROPRIATE_FORMAT.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT,HttpStatus.NOT_ACCEPTABLE));
    public void testValidateNotEmpty() {

        IStringValidator stringValidator = new EmptyStringValidator();

        String str = null;

        Pair<Boolean,ResponseEntity> responce = stringValidator.validate(str);

        Assert.assertEquals("Failure - expected False but the answer was False",responce.getLeft(), Boolean.FALSE);
        Assert.assertEquals("failure - returned response was '" + responce.getRight() + " '",
                responce.getRight(), new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT, HttpStatus.NOT_ACCEPTABLE));
    }

    @Test
    public void testValidateNotNull() {

        IStringValidator stringValidator = new EmptyStringValidator();

        String str = "Billaras";

        Pair<Boolean,ResponseEntity> responce = stringValidator.validate(str);

        Assert.assertEquals("Failure - expected True but the answer was False",responce.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+Responses.SUCCESS.getData()+"' but the answer was '"+responce.getRight().getBody()+"'",
            responce.getRight(), new ResponseEntity<>(Responses.SUCCESS,HttpStatus.OK));
    }
    
    @Test
    public void testValidateFail_EmptyString()
    {
        answer = stringValidator.validate(testStrings.get(1));
        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+Responses.SUCCESS.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.SUCCESS,HttpStatus.OK));
    }

    @Test
    public void testValidateSuccess()
    {
        answer = stringValidator.validate(testStrings.get(2));
        Assert.assertEquals("Failure - expected True but the answer was False",answer.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+Responses.SUCCESS.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.SUCCESS,HttpStatus.OK));
    }
}
