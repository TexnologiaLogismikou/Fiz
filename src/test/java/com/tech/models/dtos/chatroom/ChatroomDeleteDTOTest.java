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
public class ChatroomDeleteDTOTest 
{
    
    public ChatroomDeleteDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        ChatroomDeleteDTO.cleanValidator();
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
        ChatroomDeleteDTO.cleanValidator();
    }
    
    @Test
    public void testRegisterRoomNameValidator() throws Exception 
    {
        List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        
        str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }

    @Test
    public void testRegisterCreatorNameNameValidator() throws Exception 
    {
        List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterPasswordValidator() throws Exception 
    {
        List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        
        str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList's first element to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    
    @Test
    public void testRegisterNotListedStringValidator() 
    {
        try 
        {
            List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.MODE);
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
            List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomDeleteDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
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
        ChatroomDeleteDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomDeleteDTO.cleanValidator();
        str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }
    
    @Test
    public void testGetValidatorList() throws Exception 
    {
        ChatroomDeleteDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }
    
    @Test
    public void testRemoveRoomNameValidator() throws Exception 
    {
        ChatroomDeleteDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomDeleteDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveUserNameValidator() throws Exception 
    {
        ChatroomDeleteDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomDeleteDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemovePasswordValidator() throws Exception 
    {
        ChatroomDeleteDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.PASSWORD);
        ChatroomDeleteDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        List<String> str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomDeleteDTO.removeValidator(ValidationScopes.PASSWORD, 1);
        
        str = ChatroomDeleteDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception
    {
        Assert.assertFalse(ChatroomDeleteDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomDeleteDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomDeleteDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }
    
    @Test
    public void testRemoveValidatorPasswordNonExist() throws Exception
    {
        Assert.assertFalse(ChatroomDeleteDTO.removeValidator(ValidationScopes.PASSWORD, 50));
    }
    
    @Test
    public void testRemoveValidatorNotListed()
    {
        try 
        {
            ChatroomDeleteDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
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
            ChatroomDeleteDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
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
        ChatroomDeleteDTO MHRDTO = new ChatroomDeleteDTO();
        Assert.assertEquals("Failure expected the name to be the same","ChatroomDeleteDTO",MHRDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("creator_name","mixalis");
        json.put("room_name","teicm");
        json.put("room_password","12345");
        
        ChatroomDeleteDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomDeleteDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    }    
    
    @Test
    public void testValidateFailUserName() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("creator_name","mix");
        json.put("room_name","teicm");
        json.put("room_password","12345");
        
        ChatroomDeleteDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomDeleteDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }    
    
    @Test
    public void testValidateFailRoomName() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("creator_name","mixalis");
        json.put("room_name","tei");
        json.put("room_password","12345");
        
        ChatroomDeleteDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomDeleteDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }    
    
    @Test
    public void testValidateFailPassword() throws Exception 
    {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("creator_name","mixalis");
        json.put("room_name","teicm");
        json.put("room_password","15");
        
        ChatroomDeleteDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomDeleteDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }     
}

