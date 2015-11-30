/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author bill5_000
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class UserServiceTest extends AbstractTest{
    
    @Autowired
    private IUserService service;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }    
    
    @Before
    public void setUp(){ //TODO check user_roles for corresponding changes       
    }
    
    @After
    public void tearDown() {        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserByName(){
        User userOrigin = service.getUserById(2L);
        User user = new User(2L,"vasilis","iwanna",true);
        service.modifyUser(user);
        
        Assert.assertNotEquals("Failure expected changed username",
                userOrigin.getUsername(), user.getUsername());
        
        Assert.assertEquals("Failure expected same password", 
                userOrigin.getPassword(), user.getPassword());
        
        Assert.assertEquals("Failure expected same id", 
                userOrigin.getId(), user.getId());
        
        Assert.assertEquals("Failure expected same status", 
                userOrigin.isEnabled(), user.isEnabled());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserByPassword(){
        User userOrigin = service.getUserById(2L);
        User user = new User(2L,"iwanna","vasilis",true);
        service.modifyUser(user);
        
        Assert.assertNotEquals("Failure expected changed password",
                userOrigin.getPassword(), user.getPassword());
        
        Assert.assertEquals("Failure expected same username", 
                userOrigin.getUsername(), user.getUsername());
        
        Assert.assertEquals("Failure expected same id", 
                userOrigin.getId(), user.getId());
        
        Assert.assertEquals("Failure expected same status", 
                userOrigin.isEnabled(), user.isEnabled());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserByStatus(){
        User userOrigin = service.getUserById(2L);
        User user = new User(2L,"iwanna","iwanna",false);
        service.modifyUser(user);
        
        Assert.assertNotEquals("Failure expected changed status",
                userOrigin.isEnabled(), user.isEnabled());
        
        Assert.assertEquals("Failure expected same username", 
                userOrigin.getUsername(), user.getUsername());
        
        Assert.assertEquals("Failure expected same id", 
                userOrigin.getId(), user.getId());
        
        Assert.assertEquals("Failure expected same password", 
                userOrigin.getPassword(), user.getPassword());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserByUsernameAndPassword(){
        User userOrigin = service.getUserById(2L);
        User user = new User(2L,"vasilis","vasilis",true);
        service.modifyUser(user);
        
        Assert.assertNotEquals("Failure expected changed password",
                userOrigin.getPassword(), user.getPassword());
        
        Assert.assertNotEquals("Failure expected changed username", 
                userOrigin.getUsername(), user.getUsername());
        
        Assert.assertEquals("Failure expected same id", 
                userOrigin.getId(), user.getId());
        
        Assert.assertEquals("Failure expected same status", 
                userOrigin.isEnabled(), user.isEnabled());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserByUsernameAndStatus(){
        User userOrigin = service.getUserById(2L);
        User user = new User(2L,"vasilis","iwanna",false);
        service.modifyUser(user);
        
        Assert.assertEquals("Failure expected same password",
                userOrigin.getPassword(), user.getPassword());
        
        Assert.assertNotEquals("Failure expected changed username", 
                userOrigin.getUsername(), user.getUsername());
        
        Assert.assertEquals("Failure expected same id", 
                userOrigin.getId(), user.getId());
        
        Assert.assertNotEquals("Failure expected changed status", 
                userOrigin.isEnabled(), user.isEnabled());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserByPasswordAndStatus(){
        User userOrigin = service.getUserById(2L);
        User user = new User(2L,"iwanna","vasilis",false);
        service.modifyUser(user);
        
        Assert.assertNotEquals("Failure expected changed password",
                userOrigin.getPassword(), user.getPassword());
        
        Assert.assertEquals("Failure expected same username", 
                userOrigin.getUsername(), user.getUsername());
        
        Assert.assertEquals("Failure expected same id", 
                userOrigin.getId(), user.getId());
        
        Assert.assertNotEquals("Failure expected changed status", 
                userOrigin.isEnabled(), user.isEnabled());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserByUsernameAndPasswordAndStatus(){
        User userOrigin = service.getUserById(2L);
        User user = new User(2L,"vasilis","vasilis",false);
        service.modifyUser(user);
        
        Assert.assertNotEquals("Failure expected changed password",
                userOrigin.getPassword(), user.getPassword());
        
        Assert.assertNotEquals("Failure expected changed username", 
                userOrigin.getUsername(), user.getUsername());
        
        Assert.assertEquals("Failure expected same id", 
                userOrigin.getId(), user.getId());
        
        Assert.assertNotEquals("Failure expected changed status", 
                userOrigin.isEnabled(), user.isEnabled());
    }
}
