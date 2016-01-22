/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.AbstractControllerTest;
import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.MaxNumberAllowedValidator;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotMatchValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.FriendDTO;
import com.tech.models.dtos.chatroom.ChatroomLocationUpdateDTO;
import com.tech.models.dtos.responses.UserResponseDTO;
import com.tech.models.dtos.user.RegisteredUserDTO;
import com.tech.models.entities.user.User;
import com.tech.models.entities.user.UserInfo;
import com.tech.services.user.UserInfoService;
import com.tech.services.user.UserService;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.verify;

/**
 *
 * @author milena
 */
@Transactional
@ActiveProfiles({"milena","milena"})
public class UserControllerTest extends AbstractControllerTest
{
    String uri;

    @Mock
    private UserService userService;

    @Mock
    private UserInfoService userInfoService;

    @InjectMocks
    private UserController controller;
    
    public UserControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass()
    {     
        InitializeValidators.InitializeCustomValidators();
    }  
    
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        
        super.setUp(controller);
        
        uri = "/user";
    }
    
    @After
    @Override
    public void tearDown() {
        InitializeValidators.CleanCustomValidators();
    }

    /**
     * @author milena
     * Test of loadUserProfile method, of class UserController.
     * @throws java.lang.Exception
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testLoadUserProfile() throws Exception
    {
        json.put("username", "milenaAz");
        json.put("firstname","milena");
        json.put("last_name","Azwidou");
        json.put("profile_photo","C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg");
        json.put("email","milena@gmail.com");
        json.put("birthday","22/11/1994");
        json.put("hometown","kabala");
        json.put("status","status");
        
        when(userService.checkUsername("milenaAz")).thenReturn(true);
        when(userService.getUserByUsername("milenaAz")).thenReturn(new User(1L,"milenaAz","milena",true));
        when(userInfoService.getUserInfoByUserId(1L)).thenReturn(new UserInfo(1L,"milena@gmail.com","C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg",
                                                                "status","Azwidou",Date.valueOf("1994-11-22"),"kabala","milena" ));

        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/milenaAz")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        UserResponseDTO URDTO = super.mapFromJson(content,UserResponseDTO.class);

        verify(userService, times(1)).checkUsername("milenaAz");
        verify(userService, times(1)).getUserByUsername("milenaAz");
        verify(userInfoService, times(1)).getUserInfoByUserId(1L);
        
        Assert.assertEquals("failure - expected HTTP response OK",
                200, status);
        Assert.assertTrue("failure - expected HTTP response 'username' to be 'milenaAz'",
                URDTO.getUsername().equals("milenaAz"));
        Assert.assertTrue("failure - expected HTTP response 'firstname' to be 'milena'",
                URDTO.getFirstname().equals("milena"));
        Assert.assertTrue("failure - expected HTTP response 'last_name' to be 'Azwidou'",
                URDTO.getLast_name().equals("Azwidou"));
        Assert.assertTrue("failure - expected HTTP response 'profile_photo' to be 'C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg'",
                URDTO.getProfile_photo().equals("C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg"));
        Assert.assertTrue("failure - expected HTTP response 'email' to be 'milena@gmail.com'",
                URDTO.getEmail().equals("milena@gmail.com"));
        Assert.assertTrue("failure - expected HTTP response 'birthday' to be '22/11/1994'",
                URDTO.getBirthday().equals("1994-11-22"));
        Assert.assertTrue("failure - expected HTTP response 'hometown' to be 'kabala'",
                URDTO.getHometown().equals("kabala"));
        Assert.assertTrue("failure - expected HTTP response 'status' to be 'status'",
                URDTO.getStatus().equals("status"));
        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.SUCCESS.getData()  + " '",
                URDTO.getResponse().equals(Responses.SUCCESS.getData() ));
    }

    /**
     * @author milena
     * @throws Exception 
     */
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testUserProfileNotExist() throws Exception
    {
//        json.put("username", "eliza");
       
        when(userService.checkUsername("eliza")).thenReturn(false);
       
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/eliza"))
//                .content(json.toJSONString())
//                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        UserResponseDTO URDTO = super.mapFromJson(content,UserResponseDTO.class);
        
        verify(userService, times(1)).checkUsername("eliza");
        verify(userService, times(0)).getUserByUsername(anyString());
        verify(userInfoService, times(0)).getUserInfoByUserId(anyLong());
        
        Assert.assertEquals("failure - expected HTTP response NOT_FOUND",
                404, status);
   
        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.NOT_AVAILABLE.getData()  + " '",
                URDTO.getResponse().equals(Responses.NOT_AVAILABLE.getData() ));
    }
    
    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    public void testUserProfileInAppropriateFormat() throws Exception
    {
        try 
        {
            UserController.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
        } 
        catch (InappropriateValidatorException | ValidatorNotListedException ex) 
        {
            Logger.getLogger(ChatroomLocationUpdateDTO.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail("something went wrong while registering");
        } 
        
    
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/@45el"))
                .andReturn();
         
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertNotNull(content);
        
        UserResponseDTO URDTO = super.mapFromJson(content,UserResponseDTO.class);
        
        verify(userService, times(0)).checkUsername(anyString());
        verify(userService, times(0)).getUserByUsername(anyString());
        verify(userInfoService, times(0)).getUserInfoByUserId(anyLong());
        
        Assert.assertEquals("failure - expected HTTP response NOT_ACCEPTABLE",
                406, status);
   
        Assert.assertTrue("failure - expected HTTP response 'response' to be '" + Responses.STRING_INAPPROPRIATE_FORMAT.getData()  + " '",
                URDTO.getResponse().equals(Responses.STRING_INAPPROPRIATE_FORMAT.getData()));
        
        UserController.cleanValidator();
    }    
    
    @Test
    public void testDeactivateUserFailUsername() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        
        json.put("username", "milenaAz");
        json.put("firstname","milena");
        json.put("last_name","Azwidou");
        json.put("profile_photo","C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg");
        json.put("email","milena@gmail.com");
        json.put("birthday",dateFormat.format(new java.util.Date()));
        json.put("hometown","kabala");
        json.put("status","status");
        json.put("enabled",true);
        json.put("hasRoom",true);
        json.put("password","12345");
        
        when(userService.checkUsername("milenaAz")).thenReturn(Boolean.FALSE);
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/milenaAz/modify")
        .content(json.toJSONString())
        .contentType(MediaType.APPLICATION_JSON))
        .andReturn();
        
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("milenaAz");
        verify(userService,times(0)).getUserByUsername(anyString());
        verify(userInfoService,times(0)).getUserInfoByUserId(anyLong());
                
        Assert.assertTrue("Failure - expected to be OK", status == 404);
    } 
    
    @Test
    public void testDeactivateUserValidatorFail() throws Exception {
        
        InitializeValidators.InitializeCustomValidators();    
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        
        json.put("username", "@milenaAz");
        json.put("firstname","@milena");
        json.put("last_name","@Azwidou");
        json.put("profile_photo","C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg");
        json.put("email","milena@gmail.com");
        json.put("birthday",dateFormat.format(new java.util.Date()));
        json.put("hometown","kabala");
        json.put("status","status");
        json.put("enabled",true);
        json.put("hasRoom",true);
        json.put("password","12345");
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/*milenaAz/modify")
        .content(json.toJSONString())
        .contentType(MediaType.APPLICATION_JSON))
        .andReturn();
        
        int status = result.getResponse().getStatus();
        
        verify(userService,times(0)).checkUsername(anyString());
        verify(userService,times(0)).getUserByUsername(anyString());
        verify(userInfoService,times(0)).getUserInfoByUserId(anyLong());
                
        Assert.assertTrue("Failure - expected to be not found", status == 406);
        
        InitializeValidators.CleanCustomValidators();
    } 
    
    @Test
    public void testDeactivateUser() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        
        json.put("username", "milenaAz");
        json.put("firstname","milena");
        json.put("last_name","Azwidou");
        json.put("profile_photo","C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg");
        json.put("email","milena@gmail.com");
        json.put("birthday",dateFormat.format(new java.util.Date()));
        json.put("hometown","kabala");
        json.put("status","status");
        json.put("enabled",true);
        json.put("hasRoom",true);
        json.put("password","12345");
        
        User tempUser = new User(2L,"milenaAz","12345",true,true);
        UserInfo tempUserInfo = new UserInfo (2L,"milena@gmail.com","C:\\FizData\\DefaultImages\\NoImage\\noImg.jpg",
                "status","Azwidou",new java.util.Date(),"kabala","milena");
        
        
        when(userService.checkUsername("milenaAz")).thenReturn(Boolean.TRUE);
        when(userService.getUserByUsername("milenaAz")).thenReturn(tempUser);
        when(userInfoService.getUserInfoByUserId(tempUser.getId())).thenReturn(tempUserInfo);
        doNothing().when(userService).modifyUser(any(User.class));
        doNothing().when(userInfoService).modifyUserInfo(any(UserInfo.class));
        
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/milenaAz/modify")
                .content(json.toJSONString())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
        int status = result.getResponse().getStatus();
        
        verify(userService,times(1)).checkUsername("milenaAz");
        verify(userService,times(1)).getUserByUsername("milenaAz");
        verify(userService,times(1)).modifyUser(any(User.class));
        verify(userInfoService,times(1)).modifyUserInfo(any(UserInfo.class));
                
        Assert.assertTrue("Failure - expected to be OK", status == 200);
    }
    
    /*DTO TEST*/
    
    @Test
    public void testRegisterInapropriateValidator() throws Exception {
        try 
        {
            List<String> str = UserController.getValidatorList(ValidationScopes.USER_NAME);
            junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            UserController.registerValidator(new MaxNumberAllowedValidator(2), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex)
        {
            junit.framework.Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex)
        {
            junit.framework.Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
        
    @Test
    public void testRegisterValidatorNotListed() throws Exception {
        try 
        {
            UserController.registerValidator(new NoSpacesValidator(),ValidationScopes.PASSWORD);
            junit.framework.Assert.fail("Failure - was supposed to reach this");
        } catch (ValidatorNotListedException ex)
        {
            junit.framework.Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }  
    }
    
    @Test
    public void testRegisterNewValidator() throws Exception{
        List<String> str = UserController.getValidatorList(ValidationScopes.USER_NAME);
       junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
       
       UserController.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
       str = UserController.getValidatorList(ValidationScopes.USER_NAME);
       junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
       junit.framework.Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));       
    }
    
    @Test
    public void testDeRegisterNameValidator() throws Exception {
        
        UserController.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        UserController.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = UserController.getValidatorList(ValidationScopes.USER_NAME);
        
        junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        UserController.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = UserController.getValidatorList(ValidationScopes.USER_NAME);
        
        junit.framework.Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
    }
    
    @Test
    public void testDeRegisterNameValidatorWrongPosition() throws Exception {        
        junit.framework.Assert.assertFalse(UserController.removeValidator(ValidationScopes.USER_NAME, 10));
    }
    
    @Test
    public void testDeRegisterNameValidatorZeroPosition() throws Exception {        
        junit.framework.Assert.assertFalse(UserController.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorNotListed()
    {
        try
        {
            UserController.removeValidator(ValidationScopes.ROOM_PRIVILEGE,1);            
        } catch (ValidatorNotListedException ex)
        {
            junit.framework.Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex)
        {
            junit.framework.Assert.fail("InappropriateValidatorException should not rise here");
        }
    }
}
