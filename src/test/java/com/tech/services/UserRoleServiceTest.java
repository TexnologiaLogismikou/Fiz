/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.configurations.tools.AvailableRoles;
import com.tech.configurations.tools.Responses;
import com.tech.models.entities.user.UserRole;
import com.tech.services.interfaces.IUserRoleService;
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
 * @author arxa
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class UserRoleServiceTest extends AbstractTest
{
    @Autowired
    private IUserRoleService service;

    UserRole existingUser = null;
    UserRole updatedUser = null;
    
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() 
    {
        existingUser = new UserRole(1L,AvailableRoles.ROLE_USER.getData());
        updatedUser = new UserRole(3L,AvailableRoles.ROLE_ADMIN.getData());
    }

    @After
    public void tearDown() 
    {
        existingUser = null;
        updatedUser = null;
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddUserRole()
    { 
        //Epidi sto populate.db den iparxei perithorio eisagogis kainourgiou userRole, tha prepei na diagrapsoume prwta ena
        service.deleteUserRole(existingUser);
        service.addUserRole(existingUser);
        Assert.assertNotNull(Responses.ERROR.getData(),service.getRoleByUserID(existingUser.getUserID()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetAllUserRoles()
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getAllUserRoles().size()==3);
        service.deleteUserRole(existingUser);
        Assert.assertTrue(Responses.ERROR.getData(),service.getAllUserRoles().size()==2);
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetRoleByUserID()
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getRoleByUserID(1L).equals(AvailableRoles.ROLE_USER.getData()));
        
        /* Theloume na testaroume episis kai gia to ROLE_ADMIN. Efoson den iparxei sto populate.db tha prepei na 
        eisagoume ena diko mas afou prota diagrapsoume ena gia na exoume mia thesi eletheri opos eipame parapano */
        service.deleteUserRole(existingUser);
        UserRole temp = new UserRole(1L,AvailableRoles.ROLE_ADMIN.getData());
        service.addUserRole(temp);
        Assert.assertTrue(Responses.ERROR.getData(),service.getRoleByUserID(1L).equals(AvailableRoles.ROLE_ADMIN.getData()));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetUserRolesByRoles()
    {
        Assert.assertTrue(Responses.ERROR.getData(),service.getUserRolesByRoles(AvailableRoles.ROLE_USER.getData()).size()==3);
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserRole()
    {
        service.modifyUserRole(updatedUser);
        Assert.assertTrue(Responses.ERROR.getData(),service.getRoleByUserID(updatedUser.getUserID()).equals(AvailableRoles.ROLE_ADMIN.getData()));
    }
}
