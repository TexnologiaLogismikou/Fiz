/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations;

import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.MessageHistoryRequestDTO;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author KuroiTenshi
 */
public class InitializeValidatorsTest {
    
    public InitializeValidatorsTest() {
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
    public void testInitializeCustomValidators() throws ValidatorNotListedException {
        List<String> L = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        InitializeValidators.InitializeCustomValidators();
        List<String> L2 = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertNotEquals("Failure - expected to be different", L.size(), L2.size());
    }

    @Test
    public void testCleanCustomValidators() throws ValidatorNotListedException, InappropriateValidatorException {
//        System.out.println(new Date("23/01/1994"));
//        System.out.println(java.sql.Date.valueOf("1994-01-23"));
        MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        InitializeValidators.CleanCustomValidators();
        List<String> L2 = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected to be different", 0, L2.size());
    }
    
}
