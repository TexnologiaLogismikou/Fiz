/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.Controllers;

import com.tech.Models.User;
import com.tech.services.UserService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author KuroiTenshi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "src/main/resources/application.yml",
                                    "src/main/resources/hibernate.properties",
                                    "src/main/resources/spring.xml"})
public class RegistrationControllerTest {
    private User user;
    public RegistrationControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        user = null;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of saveUser method, of class RegistrationController.
     */
    @Test
    public void testSaveUser() {
        System.out.println("saveUser");
        String username = "";
        String password = "";
        RegistrationController instance = new RegistrationController();
        HttpEntity<String> expResult = null;
        HttpEntity<String> result = instance.saveUser(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadHtml method, of class RegistrationController.
     */
    @Test
    public void testLoadHtml() {
        System.out.println("loadHtml");
        RegistrationController instance = new RegistrationController();
        HttpEntity<String> expResult = null;
        HttpEntity<String> result = instance.loadHtml();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
