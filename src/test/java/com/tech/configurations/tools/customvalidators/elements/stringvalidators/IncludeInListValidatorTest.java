/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.stringvalidators;

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
public class IncludeInListValidatorTest 
{
    private static List<String> baseList = new ArrayList<>();
    private static List<String> testStrings = new ArrayList<>();
    private Pair<Boolean,ResponseEntity> answer;
    private static IStringValidator stringValidator;
    
    public IncludeInListValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        baseList.add("test1");
        baseList.add("test2");
        baseList.add("test3");
        
        testStrings.add("test1");
        testStrings.add("test0");
        testStrings.add("");
        
        stringValidator = new IncludeInListValidator(baseList);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        baseList = null;
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
    public void testValidateFail_RandomString() 
    {
        answer = stringValidator.validate(testStrings.get(1));
        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
        Assert.assertEquals("Failure - expected '"+Responses.STRING_INAPPROPRIATE_FORMAT.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT,HttpStatus.NOT_ACCEPTABLE)); 
    }
    
    @Test
    public void testValidateFail_EmptyString() 
    {
        answer = stringValidator.validate(testStrings.get(2));
        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
        Assert.assertEquals("Failure - expected '"+Responses.STRING_INAPPROPRIATE_FORMAT.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT,HttpStatus.NOT_ACCEPTABLE));
    }
    
    @Test
    public void testValidateSuccess() 
    {
        answer = stringValidator.validate(testStrings.get(0));
        Assert.assertEquals("Failure - expected True but the answer was False",answer.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+Responses.SUCCESS.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.SUCCESS,HttpStatus.OK));
    }
}
