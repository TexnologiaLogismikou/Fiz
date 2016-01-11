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
public class ChatroomMemberDTOTest {
    
    public ChatroomMemberDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        ChatroomMemberDTO.cleanValidator();        
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
        ChatroomMemberDTO.cleanValidator();        
    }
    
    @Test
    public void testRegisterRoomNameValidator() throws Exception
    {
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        
        str = ChatroomMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }

    @Test
    public void testRegisterMemberNameValidator() throws Exception 
    {
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = ChatroomMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterPasswordValidator() throws Exception
    {
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        
        str = ChatroomMemberDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }

    @Test
    public void testRegisterMethodValidator() throws Exception
    {
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.MODE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.MODE);
        
        str = ChatroomMemberDTO.getValidatorList(ValidationScopes.MODE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterWrongValidator()
    {
        try 
        {
            List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.LATITUDE);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.LATITUDE);
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
            List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomMemberDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
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
        ChatroomMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomMemberDTO.cleanValidator();
        str = ChatroomMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }
    
    @Test
    public void testGetValidatorList() throws Exception
    {
        ChatroomMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }
    
    @Test
    public void testRemoveRoomNameValidator() throws Exception 
    {
        ChatroomMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomMemberDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = ChatroomMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }

    @Test
    public void testRemoveUserNameValidator() throws Exception 
    {
        ChatroomMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomMemberDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = ChatroomMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
     @Test
    public void testRemovePasswordValidator() throws Exception 
    {
        ChatroomMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.PASSWORD);
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomMemberDTO.removeValidator(ValidationScopes.PASSWORD, 1);
        
        str = ChatroomMemberDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }

    @Test
    public void testRemoveMethodValidator() throws Exception 
    {
        ChatroomMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.MODE);
        ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.MODE);
        List<String> str = ChatroomMemberDTO.getValidatorList(ValidationScopes.MODE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomMemberDTO.removeValidator(ValidationScopes.MODE, 1);
        
        str = ChatroomMemberDTO.getValidatorList(ValidationScopes.MODE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception
    {
        Assert.assertFalse(ChatroomMemberDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomMemberDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomMemberDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }
    
     @Test
    public void testRemoveValidatorPasswordNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomMemberDTO.removeValidator(ValidationScopes.PASSWORD, 110));
    }
    
    @Test
    public void testRemoveValidatorMethodNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomMemberDTO.removeValidator(ValidationScopes.MODE, 20));
    }
    
    @Test
    public void testRemoveValidatorNotListed()
    {
        try 
        {
            ChatroomMemberDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
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
            ChatroomMemberDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } catch (ValidatorNotListedException ex)
        {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testAddNotListedValidator()
    {
        try
        {
            ChatroomMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);
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
    public void testNameGetter()
    {
        ChatroomMemberDTO CMDTO = new ChatroomMemberDTO();
        Assert.assertEquals("Failure expected the name to be the same","ChatroomNewMemberDTO",CMDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("password","1245333hrhrhr3");
        json.put("method","DELETE");
        
        ChatroomMemberDTO CMDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CMDTO.validate();
        
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
        json.put("method","DELETE");
        
        ChatroomMemberDTO CMDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CMDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
    
    @Test
    public void testValidateFailMemberName() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","%mixalis");
        json.put("password","123");
        json.put("method","DELETE");
        
        ChatroomMemberDTO CMDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CMDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailPassword() throws Exception
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("password"," ");
        json.put("method","DELETE");
        
        ChatroomMemberDTO CMDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CMDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
    
    @Test
    public void testValidateFailMethod() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("password","123");
        json.put("method","UPDATE");
        
        ChatroomMemberDTO CMDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomMemberDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CMDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
}
