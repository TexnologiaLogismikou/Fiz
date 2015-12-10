/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author arxa
 */

@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class ValidatorTest 
{
    private final String emptyString = "";
    private final String smallString = "ab";
    private final String stringWithSpaces = "                  test                  ";
    private final String stringWithSpacesRight = "arxa                     ";
    private final String stringWithSpacesLeft = "                arxa";
    private final String stringWithSpecialChars1 = "user!n%*@ame";
    private final String stringWithSpecialChars2 = "1234user!n%*@ame";
    private final String stringWithNumbers = "1234username";
    private List<String> accessMethods = new ArrayList();
    
    public ValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() 
    {
        accessMethods.add("blacklist");
        accessMethods.add("whitelist");
    }
    
    @After
    public void tearDown() 
    {
        accessMethods = null;
    }

    /**
     * Test of usernameValidation method, of class Validator.
     */
    @Test
    public void testUsernameValidation() 
    {
        // Testing for empty username
        Assert.assertFalse(Responses.ERROR.getData(),Validator.nameValidation(emptyString));
        // Testing for usernames with spaces
        Assert.assertFalse(Responses.ERROR.getData(),Validator.nameValidation(stringWithSpaces));
        Assert.assertFalse(Responses.ERROR.getData(),Validator.nameValidation(stringWithSpacesRight));
        Assert.assertFalse(Responses.ERROR.getData(),Validator.nameValidation(stringWithSpacesLeft));
        // Testing for a string input, larger(120000 chars) than the one permitted(16 chars)
        Assert.assertFalse(Responses.ERROR.getData(),Validator.nameValidation(LargeStringFactory.getLargeString()));
        // Testing if the username has any special characters and if it starts with a character
        Assert.assertFalse(Responses.ERROR.getData(),Validator.nameValidation(stringWithSpecialChars1));
        Assert.assertFalse(Responses.ERROR.getData(),Validator.nameValidation(stringWithSpecialChars2));
        Assert.assertFalse(Responses.ERROR.getData(),Validator.nameValidation(stringWithNumbers));
    }

    /**
     * Test of passwordValidator method, of class Validator.
     */
    @Test
    public void testPasswordValidator() 
    {
        Assert.assertFalse(Responses.ERROR.getData(),Validator.passwordValidator(emptyString));
        Assert.assertFalse(Responses.ERROR.getData(),Validator.passwordValidator(stringWithSpaces));
        Assert.assertFalse(Responses.ERROR.getData(),Validator.passwordValidator(stringWithSpacesRight));
        Assert.assertFalse(Responses.ERROR.getData(),Validator.passwordValidator(stringWithSpacesLeft));
        Assert.assertFalse(Responses.ERROR.getData(),Validator.passwordValidator(LargeStringFactory.getLargeString()));
        Assert.assertFalse(Responses.ERROR.getData(),Validator.passwordValidator(smallString));
    }
}
