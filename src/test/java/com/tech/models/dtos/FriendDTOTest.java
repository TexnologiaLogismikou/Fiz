/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.JSONToolConverter;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import java.util.List;
import junit.framework.Assert;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class FriendDTOTest {
    
    public FriendDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        FriendDTO.cleanValidator();
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass()
    {
        InitializeValidators.InitializeCustomValidators();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown()
    {
        FriendDTO.cleanValidator();
    }
    
    @Test
    public void testRegisterNameValidator() throws Exception
    {
       List<String> str = FriendDTO.getValidatorList(ValidationScopes.USER_NAME);
       Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
       
       FriendDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
       str = FriendDTO.getValidatorList(ValidationScopes.USER_NAME);
       Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
       Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));       
    }
    
    @Test
    public void testRegisterWrongValidator()
    {
        try
        {
            List<String> str = FriendDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            FriendDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);
        } catch (ValidatorNotListedException ex)
        {
            Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex)
        {
            Assert.fail("Validator should be appropriate should exist");
        }     
    }
    
    @Test
    public void testRegisterInappropriateValidator()
    {
        try 
        {
            List<String> str = FriendDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            FriendDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex)
        {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex)
        {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
    
    @Test
    public void testCleanValidator() throws Exception
    {
        FriendDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        FriendDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = FriendDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        FriendDTO.cleanValidator();
        str = FriendDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }
    
    @Test
    public void testGetValidatorList() throws Exception
    {
        FriendDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        FriendDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = FriendDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }
    
     @Test
    public void testRemoveNameValidator() throws Exception
    {
        FriendDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        FriendDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = FriendDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        FriendDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = FriendDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemovelidatorUsernameNotExist() throws Exception
    {
        Assert.assertFalse(FriendDTO.removeValidator(ValidationScopes.USER_NAME, 10));
    }
    
    @Test
    public void testRemovelidatorUsernameNotExistOnZeroSpot() throws Exception
    {
        Assert.assertFalse(FriendDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorNotListed()
    {
        try
        {
            FriendDTO.removeValidator(ValidationScopes.ROOM_PRIVILEGE,1);            
        } catch (ValidatorNotListedException ex)
        {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex)
        {
            Assert.fail("InappropriateValidatorException should not rise here");
        }
    }
    
    @Test 
    public void testGetValidatorListNotListed()
    {
        try
        {
            FriendDTO.getValidatorList(ValidationScopes.ROOM_PRIVILEGE);            
        } catch (ValidatorNotListedException ex)
        {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter()
    {
        FriendDTO FDTO = new FriendDTO();
        Assert.assertEquals("Failure expected the name to be the same","FriendDTO",FDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","milena");
        json.put("friendname","mixalis");
     
        FriendDTO FDTO = JSONToolConverter.mapFromJson(json.toJSONString(),FriendDTO.class);
        
        Pair<Boolean,ResponseEntity> r = FDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    } 
    
    @Test
    public void testValidateFailName() throws Exception
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("username","@milena");
        json.put("friendname","mixalis");
      
        FriendDTO FDTO = JSONToolConverter.mapFromJson(json.toJSONString(),FriendDTO.class);
        
        Pair<Boolean,ResponseEntity> r = FDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
}
