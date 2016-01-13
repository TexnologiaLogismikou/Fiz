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
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LongitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.MessageHistoryRequestDTO;
import java.util.List;
import junit.framework.Assert;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomWhitelistDTOTest {
    
    public ChatroomWhitelistDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        InitializeValidators.CleanCustomValidators();
    }
    
    @AfterClass
    public static void tearDownClass() {
        InitializeValidators.InitializeCustomValidators();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        ChatroomWhitelistDTO.cleanValidator();
    }
    
    @Test
    public void testRegisterRoomNameValidator() throws Exception {
        List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        
        str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterMemberNameValidator() throws Exception {
        List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterRoomModeValidator() throws Exception {
        List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.MODE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.MODE);
        
        str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.MODE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterWrongValidator() {
        try {
            List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.MODE);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.TTL);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            Assert.fail("Validator should be appropriate should exist");
        }     
    }
    
    @Test
    public void testRegisterInappropriateValidator(){
        try {
            List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomWhitelistDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
    
    @Test
    public void testCleanValidator() throws Exception {
        ChatroomWhitelistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomWhitelistDTO.cleanValidator();
        str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }
    
    @Test
    public void testGetValidatorList() throws Exception {
        ChatroomWhitelistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }
    
    @Test
    public void testRemoveRoomNameValidator() throws Exception {
        ChatroomWhitelistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomWhitelistDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveUserNameValidator() throws Exception {
        ChatroomWhitelistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomWhitelistDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveRoomNameNameValidator() throws Exception {
        ChatroomWhitelistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomWhitelistDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveRoomModeValidator() throws Exception {
        ChatroomWhitelistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.MODE);
        ChatroomWhitelistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.MODE);
        List<String> str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.MODE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomWhitelistDTO.removeValidator(ValidationScopes.MODE, 1);
        
        str = ChatroomWhitelistDTO.getValidatorList(ValidationScopes.MODE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception{
        Assert.assertFalse(ChatroomWhitelistDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception{
        Assert.assertFalse(ChatroomWhitelistDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception{
        Assert.assertFalse(ChatroomWhitelistDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }
    
    @Test
    public void testRemoveValidatorLatitudeNonExist() throws Exception{
        Assert.assertFalse(ChatroomWhitelistDTO.removeValidator(ValidationScopes.MODE, 50));
    }
    
    @Test
    public void testRemoveValidatorNotListed(){
        try {
            ChatroomWhitelistDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            Assert.fail("InappropriateValidatorException should not rise here");
        }
    }
    
    @Test 
    public void testGetValidatorListNotListed(){
        try {
            ChatroomWhitelistDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter(){
        ChatroomWhitelistDTO MHRDTO = new ChatroomWhitelistDTO();
        Assert.assertEquals("Failure expected the name to be the same","ChatroomWhitelistDTO",MHRDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("mode","DELETE");
        
        ChatroomWhitelistDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomWhitelistDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    } 
    
    @Test
    public void testValidateFailRoomName() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","@teicm");
        json.put("member_name","iwanna");
        json.put("mode","DELETE");
               
        ChatroomWhitelistDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomWhitelistDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
    
    @Test
    public void testValidateFailMemberName() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","@iwanna");
        json.put("mode","DELETE");
               
        ChatroomWhitelistDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomWhitelistDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
    
    @Test
    public void testValidateFailMode() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","iwanna");
        json.put("mode","iwanna");
               
        ChatroomWhitelistDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomWhitelistDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
}
