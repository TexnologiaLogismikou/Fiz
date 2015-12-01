/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import static java.nio.file.Files.list;
import java.util.ArrayList;
import java.util.List;
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
    private List<User> list = null;
    User userExist = null;
    //    private IUserService service = Mockito.mock(IUserService.class);
    User userNotExist = null;
    
   
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }    
    
    @Before
    public void setUp(){
        list = new ArrayList();
        list.add(new User(4L,"mixalis2","mixalis2",true));
        list.add(new User(5L,"iwanna2","iwanna2",true));
        list.add(new User(6L,"milena2","milena2",true));
        //TODO check user_roles for corresponding changes   
        
        userExist = new User (2L,"iwanna","iwanna",true);
        
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
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetUserByID(){
//        when(service.getUserById(2L)).thenReturn(user);
        Assert.assertEquals("Failure - wrong id returned",userExist.getId(),service.getUserById(2L).getId());
        Assert.assertEquals("Failure - wrong username returned",userExist.getUsername(),service.getUserById(2L).getUsername());
        Assert.assertEquals("Failure - wrong password returned",userExist.getPassword(),service.getUserById(2L).getPassword());  
       
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetUserByUsername(){
        Assert.assertEquals("User wasnt found",userExist.getUsername(),service.getUserByUsername(userExist.getUsername()).getUsername());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddUser(){
        User user = new User(4L,"basilis","basilis",true);
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
        service.deleteUser(userExist);
        Assert.assertFalse("Failure - user wasnt deleted",service.checkUsername(userExist.getUsername()));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetAllUsers(){
        list = new ArrayList();
        list.add(new User(1L,"mixalis","mixalis",true));
        list.add(new User(2L,"iwanna","iwanna",true));
        list.add(new User(3L,"milena","milena",true));
        
        List<User> list2 = service.getAllUsers();
        
        Assert.assertEquals("Failure first record doesnt match",list.get(0).getId(),list2.get(0).getId());
        Assert.assertEquals("Failure second record doesnt match",list.get(1).getId(),list2.get(1).getId());
        Assert.assertEquals("Failure third record doesnt match",list.get(2).getId(),list2.get(2).getId());
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckUsernameTrue(){ 
        Assert.assertTrue("Username wasnt found", service.checkUsername(userExist.getUsername()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testCheckUsernameFail(){
        Assert.assertFalse("Username was found", service.checkUsername("UserDoesntExist"));
        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testValidateUser(){        
        Assert.assertTrue("User wasnt validated",service.validateUser(userExist));
        
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testValidateUserFromStrings(){
        Assert.assertTrue("User wasnt validated",service.validateUser(userExist.getUsername(),userExist.getPassword()));
        
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
    
}