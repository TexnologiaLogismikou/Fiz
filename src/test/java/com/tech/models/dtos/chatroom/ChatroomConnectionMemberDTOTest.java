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
public class ChatroomConnectionMemberDTOTest {
    
    public ChatroomConnectionMemberDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        ChatroomConnectionMemberDTO.cleanValidator();
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
        ChatroomConnectionMemberDTO.cleanValidator();
    }
    
    @Test
    public void testRegisterRoomNameValidator() throws Exception 
    {
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }

    @Test
    public void testRegisterMemberNameValidator() throws Exception 
    {
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterPasswordValidator() throws Exception 
    {
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterModeValidator() throws Exception 
    {
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.MODE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.MODE);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.MODE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterLatitudeValidator() throws Exception 
    {
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomConnectionMemberDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: LatitudeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterLongitudeValidator() throws Exception 
    {
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomConnectionMemberDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: LongitudeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterNotListedFloatValidator() 
    {
        try 
        {
            List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomConnectionMemberDTO.registerValidator(new LongitudeValidator(), ValidationScopes.USER_NAME);
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
            List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.LONGITUDE);
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
            List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomConnectionMemberDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
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
        ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomConnectionMemberDTO.cleanValidator();
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }
    
    @Test
    public void testGetValidatorList() throws Exception 
    {
        ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }
    
    @Test
    public void testRemoveRoomNameValidator() throws Exception 
    {
        ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveUserNameValidator() throws Exception 
    {
        ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemovePasswordValidator() throws Exception 
    {
        ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.PASSWORD);
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.PASSWORD, 1);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveModeValidator() throws Exception 
    {
        ChatroomConnectionMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.MODE);
        ChatroomConnectionMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.MODE);
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.MODE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.MODE, 1);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.MODE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveLatitudeValidator() throws Exception 
    {
        ChatroomConnectionMemberDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
        ChatroomConnectionMemberDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.LATITUDE, 1);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveLongitudeValidator() throws Exception 
    {
        ChatroomConnectionMemberDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
        ChatroomConnectionMemberDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        List<String> str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.LONGITUDE, 1);
        
        str = ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception
    {
        Assert.assertFalse(ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }
    
    @Test
    public void testRemoveValidatorPasswordNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.PASSWORD, 50));
    }
    
    @Test
    public void testRemoveValidatorModeNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.MODE, 50));
    }
    
    @Test
    public void testRemoveValidatorLatitudeNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.LATITUDE, 50));
    }
    
    @Test
    public void testRemoveValidatorLongitudeNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.LONGITUDE, 60));
    }
    
    @Test
    public void testRemoveValidatorNotListed()
    {
        try 
        {
            ChatroomConnectionMemberDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
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
            ChatroomConnectionMemberDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
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
        ChatroomConnectionMemberDTO MHRDTO = new ChatroomConnectionMemberDTO();
        Assert.assertEquals("Failure expected the name to be the same","ChatroomConnectionMemberDTO",MHRDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("password","12345");
        json.put("mode","ADD");
        json.put("lat","50");
        json.put("lng","40");
        
        ChatroomConnectionMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomConnectionMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    }    
    
    @Test
    public void testValidateFailRoomName() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","@teicm");
        json.put("member_name","mixalis");
        json.put("password","123");
        json.put("mode","ADD");
        json.put("lat","50");
        json.put("lng","40");
        
        ChatroomConnectionMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomConnectionMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailMemberName() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","%mixalis");
        json.put("password","123");
        json.put("mode","ADD");
        json.put("lat","50");
        json.put("lng","40");
        
        ChatroomConnectionMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomConnectionMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailPassword() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("password","12 3");
        json.put("mode","ADD");
        json.put("lat","50");
        json.put("lng","40");
        
        ChatroomConnectionMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomConnectionMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailMode() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("password","123");
        json.put("mode","AD D");
        json.put("lat","50");
        json.put("lng","40");
        
        ChatroomConnectionMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomConnectionMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailLatitude() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("password","123");
        json.put("mode","ADD");
        json.put("lat","250");
        json.put("lng","40");
        
        ChatroomConnectionMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomConnectionMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
    
    @Test
    public void testValidateFailLongitude() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("password","123");
        json.put("mode","ADD");
        json.put("lat","50");
        json.put("lng","540");
        
        ChatroomConnectionMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomConnectionMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
}

