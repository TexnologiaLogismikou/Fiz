/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import com.tech.AbstractControllerTest;
import com.tech.AbstractTest;
import com.tech.services.MessageService;
import com.tech.services.interfaces.IMessageService;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class MessageDeleterTest extends AbstractControllerTest{
    
//    @Mock
//    MessageService MS;
//    
//    @InjectMocks
//    MessageDeleter MD;
    
    @Autowired
    MessageDeleter MD;
    
    @Autowired
    IMessageService MS;
    
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
    @Sql(scripts = "classpath:populateDB.sql")
    public void testRun() {
        MD.run();
        Long i = MS.getCount();
        Assert.assertTrue("Failure - expected to be true",MS.getCount() == 0);
    }
    
}
