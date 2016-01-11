/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.JSONToolConverter;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.FloatNotNaNValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LatitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LongitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
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
public class ChatroomLocationDTOTest {
    
    public ChatroomLocationDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        ChatroomLocationDTO.cleanValidator();        
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
        ChatroomLocationDTO.cleanValidator(); 
    }
    
    @Test
    public void testRegisterLatitudeValidator() throws Exception 
    {
        List<String> str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomLocationDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        
        str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: LatitudeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterLongitudeValidator() throws Exception 
    {
        List<String> str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomLocationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        
        str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: LongitudeValidator", str.get(0));   
    }
    
     @Test
    public void testRegisterWrongValidator()
    {
        try 
        {
            List<String> str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LONGITUDE);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomLocationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.USER_NAME);
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
            List<String> str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LONGITUDE);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomLocationDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LONGITUDE);
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
        ChatroomLocationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        ChatroomLocationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
        List<String> str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomLocationDTO.cleanValidator();
        str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }
    
    @Test
    public void testGetValidatorList() throws Exception 
    {
        ChatroomLocationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        ChatroomLocationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
        List<String> str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }
    
     @Test
    public void testRemoveLatitudeValidator() throws Exception 
    {
        ChatroomLocationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
        ChatroomLocationDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        List<String> str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomLocationDTO.removeValidator(ValidationScopes.LATITUDE, 1);
        
        str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveLongitudeValidator() throws Exception 
    {
        ChatroomLocationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
        ChatroomLocationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        List<String> str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomLocationDTO.removeValidator(ValidationScopes.LONGITUDE, 1);
        
        str = ChatroomLocationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorLatitudeNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomLocationDTO.removeValidator(ValidationScopes.LATITUDE, 50));
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception{
        Assert.assertFalse(ChatroomLocationDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorLongitudeNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomLocationDTO.removeValidator(ValidationScopes.LONGITUDE, 60));
    }
    
    @Test
    public void testRemoveValidatorNotListed()
    {
        try 
        {
            ChatroomLocationDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
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
            ChatroomLocationDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } catch (ValidatorNotListedException ex)
        {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter()
    {
        ChatroomLocationDTO CLDTO = new ChatroomLocationDTO();
        Assert.assertEquals("Failure expected the name to be the same","ChatroomNewMemberDTO",CLDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
         
        json.put("lng","40");
        json.put("lat","50");
        
        
        ChatroomLocationDTO CLDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomLocationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CLDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    }  
    
    @Test
    public void testValidateFailLatitude() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("lng","40");
        json.put("lat","250");
       
        
        ChatroomLocationDTO CLDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomLocationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CLDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
    
    @Test
    public void testValidateFailLongitude() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("lng","540");
        json.put("lat","50");
        
        
        ChatroomLocationDTO CLDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomLocationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CLDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
}
