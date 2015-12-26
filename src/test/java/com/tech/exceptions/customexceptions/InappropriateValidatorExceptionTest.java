/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.exceptions.customexceptions;

import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.models.dtos.MessageHistoryRequestDTO;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author KuroiTenshi
 */
public class InappropriateValidatorExceptionTest {
    
    public InappropriateValidatorExceptionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass() {
        InitializeValidators.InitializeCustomValidators();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testThrowNewInnapropriateValidatorException() {
        try {
            MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.MODE);
            Assert.fail("Expected Exception to be thrown");
        } catch (InappropriateValidatorException | ValidatorNotListedException ex) {
            Assert.assertTrue("Failure - expected thrown cause to be InappropriateValidator",
                    new InappropriateValidatorException().getMessage().equals(ex.getMessage()));
        }
    }
    
}
