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
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.MaxNumberAllowed;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotNegativeValidator;
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
public class ChatroomCreationDTOTest 
{
    
    public ChatroomCreationDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        ChatroomCreationDTO.cleanValidator();
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
        ChatroomCreationDTO.cleanValidator();
    }
    
    @Test
    public void testRegisterUserNameValidator() throws Exception 
    {
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }

    
    @Test
    public void testRegisterRoomNameValidator() throws Exception 
    {
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }

    @Test
    public void testRegisterRoomPrivilegeValidator() throws Exception 
    {
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_PRIVILEGE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_PRIVILEGE);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_PRIVILEGE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    
    @Test
    public void testRegisterRoomAccessMethodValidator() throws Exception 
    {
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_ACCESS_METHOD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_ACCESS_METHOD);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_ACCESS_METHOD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterLatitudeValidator() throws Exception 
    {
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCreationDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: LatitudeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterLongitudeValidator() throws Exception 
    {
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCreationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: LongitudeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterMaxRangeValidator() throws Exception 
    {
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.RANGE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCreationDTO.registerValidator(new NotNegativeValidator(), ValidationScopes.RANGE);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.RANGE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NotNegativeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterPasswordValidator() throws Exception 
    {
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterNotListedFloatValidator() 
    {
        try 
        {
            List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomCreationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.USER_NAME);
        } 
        catch (ValidatorNotListedException ex) 
        {
            Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } 
        catch (InappropriateValidatorException ex) 
        {
            Assert.fail("Exception should be ValidatorNotListedException not InappropriateValidatorException");
        }     
    }
    
    @Test
    public void testRegisterNotListedStringValidator() 
    {
        try 
        {
            List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.LONGITUDE);
        } 
        catch (ValidatorNotListedException ex) 
        {
            Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } 
        catch (InappropriateValidatorException ex) 
        {
            Assert.fail("Exception should be ValidatorNotListedException not InappropriateValidatorException");
        }     
    }
    
    @Test
    public void testRegisterNotListedNumberValidator() 
    {
        try 
        {
            List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomCreationDTO.registerValidator(new NotNegativeValidator(), ValidationScopes.LONGITUDE);
        } 
        catch (ValidatorNotListedException ex) 
        {
            Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } 
        catch (InappropriateValidatorException ex) 
        {
            Assert.fail("Exception should be ValidatorNotListedException not InappropriateValidatorException");
        }     
    }
    
    @Test
    public void testRegisterInappropriateValidator(){
        try 
        {
            List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomCreationDTO.registerValidator(null, ValidationScopes.USER_NAME);
        } 
        catch(ValidatorNotListedException ex) 
        {
            Assert.fail("Exception should be InappropriateValidatorException not ValidatorNotListedException");
        } 
        catch (InappropriateValidatorException ex) 
        {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
    
    @Test
    public void testCleanValidator() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomCreationDTO.cleanValidator();
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }
    
    @Test
    public void testGetValidatorList() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }
    
    @Test
    public void testRemoveUserNameValidator() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomCreationDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveRoomNameValidator() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomCreationDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveRoomPrivilegeValidator() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_PRIVILEGE);
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_PRIVILEGE);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_PRIVILEGE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomCreationDTO.removeValidator(ValidationScopes.ROOM_PRIVILEGE, 1);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_PRIVILEGE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveAccessMethodValidator() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_ACCESS_METHOD);
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_ACCESS_METHOD);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_ACCESS_METHOD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomCreationDTO.removeValidator(ValidationScopes.ROOM_ACCESS_METHOD, 1);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.ROOM_ACCESS_METHOD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveLatitudeValidator() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
        ChatroomCreationDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomCreationDTO.removeValidator(ValidationScopes.LATITUDE, 1);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveLongitudeValidator() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
        ChatroomCreationDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomCreationDTO.removeValidator(ValidationScopes.LONGITUDE, 1);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveMaxRangeValidator() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.RANGE);
        ChatroomCreationDTO.registerValidator(new NotNegativeValidator(), ValidationScopes.RANGE);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.RANGE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomCreationDTO.removeValidator(ValidationScopes.RANGE, 1);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.RANGE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemovePasswordValidator() throws Exception 
    {
        ChatroomCreationDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.PASSWORD);
        ChatroomCreationDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        List<String> str = ChatroomCreationDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomCreationDTO.removeValidator(ValidationScopes.PASSWORD, 1);
        
        str = ChatroomCreationDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    
  
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception
    {
        Assert.assertFalse(ChatroomCreationDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomCreationDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomCreationDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }
    
    @Test
    public void testRemoveValidatorPasswordNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomCreationDTO.removeValidator(ValidationScopes.PASSWORD, 50));
    }
    
    @Test
    public void testRemoveValidatorRoomPrivilegeNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomCreationDTO.removeValidator(ValidationScopes.ROOM_PRIVILEGE, 50));
    }
    
    @Test
    public void testRemoveValidatorAccessMethodNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomCreationDTO.removeValidator(ValidationScopes.ROOM_ACCESS_METHOD, 50));
    }
    
    @Test
    public void testRemoveValidatorRangeNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomCreationDTO.removeValidator(ValidationScopes.RANGE, 50));
    }
    
    @Test
    public void testRemoveValidatorLatitudeNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomCreationDTO.removeValidator(ValidationScopes.LATITUDE, 50));
    }
    
    @Test
    public void testRemoveValidatorLongitudeNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomCreationDTO.removeValidator(ValidationScopes.LONGITUDE, 60));
    }
    
    @Test
    public void testRemoveValidatorNotListed()
    {
        try 
        {
            ChatroomCreationDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
        } 
        catch (ValidatorNotListedException ex) 
        {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } 
        catch (InappropriateValidatorException ex) 
        {
            Assert.fail("InappropriateValidatorException should not rise here");
        }
    }
    
    @Test 
    public void testGetValidatorListNotListed()
    {
        try 
        {
            ChatroomCreationDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } 
        catch (ValidatorNotListedException ex) 
        {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter()
    {
        ChatroomCreationDTO MHRDTO = new ChatroomCreationDTO();
        Assert.assertEquals("Failure expected the name to be the same","ChatroomCreationDTO",MHRDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("room_name","teicm");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat","50");
        json.put("room_lng","40");
        json.put("room_max_range","50");
        json.put("password","12345");
        
        ChatroomCreationDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomCreationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    }    
    
    @Test
    public void testValidateFailUserName() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","@mixalis");
        json.put("room_name","teicm");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat","50");
        json.put("room_lng","40");
        json.put("room_max_range","50");
        json.put("password","12345");
       
        ChatroomCreationDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomCreationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailRoomName() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("room_name","%teicm");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat","50");
        json.put("room_lng","40");
        json.put("room_max_range","50");
        json.put("password","12345");
       
        ChatroomCreationDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomCreationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailRoomPrivilege() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("room_name","teicm");
        json.put("room_privilege","PUB LIC");
        json.put("access_method","blacklist");
        json.put("room_lat","50");
        json.put("room_lng","40");
        json.put("room_max_range","50");
        json.put("password","12345");
       
        ChatroomCreationDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomCreationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailAccessMethod() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("room_name","teicm");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","");
        json.put("room_lat","50");
        json.put("room_lng","40");
        json.put("room_max_range","50");
        json.put("password","12345");
       
        ChatroomCreationDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomCreationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailLatitude() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("room_name","teicm");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat","250");
        json.put("room_lng","40");
        json.put("room_max_range","50");
        json.put("password","12345");
        
        ChatroomCreationDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomCreationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }
    
    @Test
    public void testValidateFailLongitude() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("room_name","teicm");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat","50");
        json.put("room_lng","540");
        json.put("room_max_range","50");
        json.put("password","12345");
        
        ChatroomCreationDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomCreationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }
    
    @Test
    public void testValidateFailRoomMaxRange() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        ChatroomCreationDTO.registerValidator(new MaxNumberAllowed(10), ValidationScopes.RANGE);
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("room_name","teicm");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat","50");
        json.put("room_lng","40");
        json.put("room_max_range","30");
        json.put("password","12345");
        
        ChatroomCreationDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomCreationDTO.class);
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }
    
    @Test
    public void testValidateFailRoomPassword() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("room_name","teicm");
        json.put("room_privilege","PUBLIC");
        json.put("access_method","blacklist");
        json.put("room_lat","50");
        json.put("room_lng","40");
        json.put("room_max_range","50");
        json.put("password","12");
        
        ChatroomCreationDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomCreationDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }
}

