/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import com.tech.AbstractTest;
import com.tech.services.interfaces.IMessageService;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author KuroiTenshi
 */
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class MessageDeleterTest extends AbstractTest{
    
    public MessageDeleterTest() {
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
    public void testRun() {
//        MessageDeleter MD = new MessageDeleter();
//        MD.run();
    }
    
}
