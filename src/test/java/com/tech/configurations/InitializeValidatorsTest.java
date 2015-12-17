/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
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
    public void testInitializeCustomValidators() {
        InitializeValidators.InitializeCustomValidators();
    }

    @Test
    public void testCleanCustomValidators() {
        System.out.println(new Date("23/01/1994"));
        System.out.println(java.sql.Date.valueOf("1994-01-23"));
        System.out.println("");
    }
    
}
