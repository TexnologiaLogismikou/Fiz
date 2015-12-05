/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.AbstractTest;
import com.tech.configurations.tools.Attr;
import com.tech.models.entities.UserInfo;
import com.tech.repositories.IUserInfoRepository;
import com.tech.services.interfaces.IUserInfoService;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author iwann
 */
@Transactional
@WebAppConfiguration
@ActiveProfiles({"iwanna","iwanna"})
public class UserInfoServiceTest extends AbstractTest{
    
    @Mock
    private IUserInfoRepository repository;
    @InjectMocks
    private UserInfoService mockService;
    
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
        MockitoAnnotations.initMocks(this);
        
        userNotExist = new UserInfo(4L,"mixalis2@gmail.com",Attr.NO_IMAGE_ASSIGNED.getData(),
                "status","mixailidis","25/08/1994","thessaloniki","mixalis");
        userExist = new UserInfo(2L,"iwanna@gmail.com",Attr.NO_IMAGE_ASSIGNED.getData(),
                "Status","Fwtiadoy","23/01/1994","serres","serres");
       
    }
    
    @After
    public void tearDown() {
        userNotExist = null;
        userExist = null;
    }

    /**
     * Transactional annotation disables modifications of the DB. So testing a modification function was unable with 
     * direct access of database. Mocking the service / repository allow us to verify that the modification function is called 
     * with the correct arguments. the Query is already tested for being correct so if its called it means the the modification is done
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testModifyUserInfo() {
        UserInfo mockedChangedUser = new UserInfo(Long.parseLong("2"),"mixalis2@gmail.com",Attr.NO_IMAGE_ASSIGNED.getData(),
                "status","mixailidis","25/08/1994","thessaloniki","mixalis");
        
        UserInfo userinfoOrigin = service.getUserInfoByUserId(userExist.getUserid());
        when(mockService.getUserInfoByUserId(2L)).thenReturn(mockedChangedUser);
        
        mockService.modifyUserInfo(mockedChangedUser);
        UserInfo tmp = mockService.getUserInfoByUserId(userExist.getUserid());
           
        verify(repository, times(1)).findByUserid(anyLong());
        verify(repository, times(1)).setUserInfoById(mockedChangedUser.getEmail(), mockedChangedUser.getProfilePhoto(),
                mockedChangedUser.getStatus(), mockedChangedUser.getLastName(),mockedChangedUser.getBirthday(),mockedChangedUser.getHometown(),
                mockedChangedUser.getFirstName(),mockedChangedUser.getUserid());
        
        Assert.assertEquals("Fail Modify User Info,expected same id", userinfoOrigin.getUserid(), tmp.getUserid());
        Assert.assertNotEquals("Fail in modify Emai expected : " + userinfoOrigin.getEmail()
                + " but Found : " + tmp.getEmail() ,userinfoOrigin.getEmail(),tmp.getEmail());
        Assert.assertNotEquals("Fail in Modify last name",userinfoOrigin.getLastName(),tmp.getLastName() );
        
    }

    /**
     * Test of addUserInfo method, of class UserInfoService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testAddUserInfo() {
        UserInfo userinfo = new UserInfo(3L,"iwanna@gmail5.com",Attr.NO_IMAGE_ASSIGNED.getData(),
                "Status","Fwtiadoy5","23/01/1994","kommotini","thessaloniki");
        service.addUserInfo(userinfo);
        Assert.assertNotNull("Fail add User",service.getUserInfoByUserId(userinfo.getUserid()));
    }

    /**
     * Test of getUserInfoByUserId method, of class UserInfoService.
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testGetUserInfoByUserId() {
        Assert.assertEquals("Fail get User By Id",userExist.getUserid(),service.getUserInfoByUserId(userExist.getUserid()).getUserid());
    }
    
}
