/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.configurations.tools.Attr;
import com.tech.models.entities.UserInfo;
import com.tech.services.interfaces.IUserInfoService;
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
 * @author iwann
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class UserInfoServiceTest extends AbstractTest{
    
    @Autowired
    private IUserInfoService service;
    UserInfo userExist = null;
    UserInfo userNotExist = null;
    
    public UserInfoServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        userNotExist = new UserInfo(4L,"mixalis2@gmail.com",Attr.NO_IMAGE_ASSIGNED.getData(),
                "status","mixailidis","25/08/1994","thessaloniki");
        userExist = new UserInfo(2L,"iwanna@gmail.com",Attr.NO_IMAGE_ASSIGNED.getData(),
                "Status","Fwtiadoy","23/01/1994","serres");
       
    }
    
    @After
    public void tearDown() {
        userNotExist = null;
        userExist = null;
    }

    /**
     * Test of modifyUserInfo method, of class UserInfoService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserInfo() {
        UserInfo userinfoOrigin = service.getUserInfoByUserId(userExist.getUserid());
        userNotExist.setUserid(userExist.getUserid());
        service.modifyUserInfo(userNotExist);
        userNotExist = service.getUserInfoByUserId(userExist.getUserid());
        System.out.println(userNotExist.getUserid());
      
        Assert.assertEquals("Fail Modify User Info", userinfoOrigin.getUserid(), userNotExist.getUserid());
        Assert.assertEquals("Fail in modify Emai",userinfoOrigin.getEmail(),userNotExist.getEmail());
        Assert.assertEquals("Fail in Modify last name",userinfoOrigin.getLastName(),userNotExist.getLastName() );
        
    }

    /**
     * Test of addUserInfo method, of class UserInfoService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddUserInfo() {
        UserInfo userinfo = new UserInfo(3L,"iwanna@gmail5.com",Attr.NO_IMAGE_ASSIGNED.getData(),
                "Status","Fwtiadoy5","23/01/1994","kommotini");
        service.addUserInfo(userinfo);
        Assert.assertEquals("Fail add user",userExist.getUserid(),service.getUserInfoByUserId(userExist.getUserid()).getUserid());
    }

    /**
     * Test of getUserInfoByUserId method, of class UserInfoService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetUserInfoByUserId() {
        Assert.assertEquals("Fail get User By Id",userExist.getUserid(),service.getUserInfoByUserId(2L).getUserid());
    }
    
}
