/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.Models.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
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
    private IUserService service;
//    private IUserService service = Mockito.mock(IUserService.class);
    private List<User> list = null;    
    User user = null;
    @Before
    public void setUp() {
        list = new ArrayList();
//        list.add(new User(1L,"mixalis","mixalis"));
//        list.add(new User(2L,"iwanna","iwanna"));
//        list.add(new User(3L,"milena","milena"));
        list.add(new User(4L,"mixalis2","mixalis2"));
        list.add(new User(5L,"iwanna2","iwanna2"));
        list.add(new User(6L,"milena2","milena2"));
        user = new User(2L,"iwanna","iwanna");
//        service.addUsers(list);
    }
    
    @After
    public void tearDown() {   
        user = null;
        list = null;
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testFindAll() {
        Collection<User> list2 = service.getAllUsers();
        
        Assert.assertNotNull("Failure - expected not null",list2);
        Assert.assertEquals("Failure - expected size",3,list2.size());
//        when(service.getAllUsers()).thenReturn(list);    
//        Assert.assertEquals("Failure - expected same size",3, service.getAllUsers().size());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetUserByID(){
//        when(service.getUserById(2L)).thenReturn(user);
        
        Assert.assertEquals("Failure - wrong id returned",user.getId(),service.getUserById(2L).getId());
        Assert.assertEquals("Failure - wrong username returned",user.getUsername(),service.getUserById(2L).getUsername());
        Assert.assertEquals("Failure - wrong password returned",user.getPassword(),service.getUserById(2L).getPassword());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddUser(){
        User user = new User(4L,"basilis","basilis");
        service.addUser(user);
        Assert.assertEquals("Failure - user wasnt added",user.getId(),service.getUserById(user.getId()).getId());
    } 
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddUsers(){
        service.addUsers(list);
        Assert.assertEquals("Failure - user 4 wasnt added",list.get(0).getId(),service.getUserById(list.get(0).getId()).getId());
        Assert.assertEquals("Failure - user 5 wasnt added",list.get(1).getId(),service.getUserById(list.get(1).getId()).getId());
        Assert.assertEquals("Failure - user 6 wasnt added",list.get(2).getId(),service.getUserById(list.get(2).getId()).getId());
        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testDeleteUser(){
        service.deleteUser(user);
        Assert.assertFalse("Failure - user wasnt deleted",service.checkUsername(user.getUsername()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetAllUsers(){
        list = new ArrayList();
        list.add(new User(1L,"mixalis","mixalis"));
        list.add(new User(2L,"iwanna","iwanna"));
        list.add(new User(3L,"milena","milena"));
        
        List<User> list2 = service.getAllUsers();
        
        Assert.assertEquals("Failure first record doesnt match",list.get(0).getId(),list2.get(0).getId());
        Assert.assertEquals("Failure second record doesnt match",list.get(1).getId(),list2.get(1).getId());
        Assert.assertEquals("Failure third record doesnt match",list.get(2).getId(),list2.get(2).getId());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckUsernameTrue(){ 
        Assert.assertTrue("Username wasnt found", service.checkUsername(user.getUsername()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckUsernameFail(){
        Assert.assertFalse("Username was found", service.checkUsername("UserDoesntExist"));
        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testValidateUser(){        
        Assert.assertTrue("User wasnt validated",service.validateUser(user));
        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testValidateUserFromStrings(){
        Assert.assertTrue("User wasnt validated",service.validateUser(user.getUsername(),user.getPassword()));
        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testValidateUserFailPassword(){
        Assert.assertFalse("User wasnt validated",service.validateUser(list.get(2).getUsername(),"Wrong password"));        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testValidateUserFail(){
        Assert.assertFalse("User wasnt validated",service.validateUser("Wrong username",list.get(2).getPassword()));        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetCount(){
        Assert.assertEquals("Counting was wrong",3,service.getCount());        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetNextID(){
        Assert.assertEquals("Counting was wrong",4,service.getNextID());        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetUserByUsername(){
        Assert.assertEquals("User wasnt found",user.getUsername(),service.getUserByUsername(user.getUsername()).getUsername());
    }
    
}
