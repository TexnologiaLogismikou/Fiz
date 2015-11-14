/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.Models.User;
import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author KuroiTenshi
 */
@Transactional
public class UserServiceTest extends AbstractTest {
    
    @Autowired
    protected IUserService service;
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindAll() {
        Collection<User> list = service.getAllUsers();
        
        Assert.assertNotNull("Failure - expected not null",list);
        Assert.assertEquals("Failure - expected size",3,list.size());
    }
       
}
