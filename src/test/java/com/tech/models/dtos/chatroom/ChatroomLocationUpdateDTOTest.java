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
import com.tech.configurations.tools.customvalidators.elements.EmptyFloatValidator;
import com.tech.configurations.tools.customvalidators.elements.EmptyValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.FloatNotNaNValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LatitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LongitudeValidator;
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
public class ChatroomLocationUpdateDTOTest {
    
    public ChatroomLocationUpdateDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {   
        ChatroomLocationUpdateDTO.cleanValidator();        
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
        ChatroomLocationUpdateDTO.cleanValidator();
    }
    
    
    
    
    
@Test
    public void testRegisterRoomNameValidator() throws Exception {
        List<String> str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomLocationUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        
        str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }

    
    @Test
    public void testRegisterLatitudeValidator() throws Exception {
        List<String> str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomLocationUpdateDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        
        str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: LatitudeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterLongitudeValidator() throws Exception {
        List<String> str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomLocationUpdateDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        
        str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: LongitudeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterNotListedStringValidator() {
        try {
            ChatroomLocationUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.EMAIL);
        } catch (ValidatorNotListedException ex) {
            org.junit.Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            org.junit.Assert.fail("Validator should be appropriate should exist");
        }

    }
    
    @Test
    public void testRegisterNotListedFloatValidator() {
        try {
            ChatroomLocationUpdateDTO.registerValidator(new EmptyFloatValidator(), ValidationScopes.EMAIL);
        } catch (ValidatorNotListedException ex) {
            org.junit.Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            org.junit.Assert.fail("Validator should be appropriate should exist");
        }

    }
    
    @Test
    public void testRegisterInappropriateValidator() {
        try {
            ChatroomLocationUpdateDTO.registerValidator(new EmptyValidator(), ValidationScopes.EMAIL);
        } catch (ValidatorNotListedException ex) {
            org.junit.Assert.fail("Should get InappropriateValidatorException");
        } catch (InappropriateValidatorException ex) {
            org.junit.Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        }

    }
    
    
    @Test
    public void testRegisterWrongValidator() {
        try {
            List<String> str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomLocationUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            Assert.fail("Validator should be appropriate should exist");
        }     
    }

    
    @Test
    public void testCleanValidator() throws Exception {
        ChatroomLocationUpdateDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomLocationUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomLocationUpdateDTO.cleanValidator();
        str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }

    @Test
    public void testGetValidatorList() throws Exception {
        ChatroomLocationUpdateDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomLocationUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }

    @Test
    public void testRemoveRoomNameValidator() throws Exception {
        ChatroomLocationUpdateDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomLocationUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomLocationUpdateDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }

    
    
    @Test
    public void testRemoveLatitudeValidator() throws Exception {
        ChatroomLocationUpdateDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
        ChatroomLocationUpdateDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        List<String> str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomLocationUpdateDTO.removeValidator(ValidationScopes.LATITUDE, 1);
        
        str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveLongitudeValidator() throws Exception {
        ChatroomLocationUpdateDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
        ChatroomLocationUpdateDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        List<String> str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomLocationUpdateDTO.removeValidator(ValidationScopes.LONGITUDE, 1);
        
        str = ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception{
        Assert.assertFalse(ChatroomLocationUpdateDTO.removeValidator(ValidationScopes.ROOM_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception{
        Assert.assertFalse(ChatroomLocationUpdateDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }
    
    @Test
    public void testRemoveValidatorLatitudeNonExist() throws Exception{
        Assert.assertFalse(ChatroomLocationUpdateDTO.removeValidator(ValidationScopes.LATITUDE, 50));
    }
    
    @Test
    public void testRemoveValidatorLongitudeNonExist() throws Exception{
        Assert.assertFalse(ChatroomLocationUpdateDTO.removeValidator(ValidationScopes.LONGITUDE, 60));
    }
    
    @Test
    public void testRemoveValidatorNotListed(){
        try {
            ChatroomLocationUpdateDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
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
            ChatroomLocationUpdateDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter(){
        ChatroomLocationUpdateDTO CLUDTO = new ChatroomLocationUpdateDTO();
        Assert.assertEquals("Failure expected the name to be the same","ChatroomLocationUpdateDTO",CLUDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("lat","50");
        json.put("lng","40");
        
        ChatroomLocationUpdateDTO CLUDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomLocationUpdateDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CLUDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    }    
    
    @Test
    public void testValidateFailRoomName() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","@teicm");
        json.put("lat","50");
        json.put("lng","40");
        
        ChatroomLocationUpdateDTO CLUDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomLocationUpdateDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CLUDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
    
    @Test
    public void testValidateFailMemberName() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","@teicm");
        json.put("lat","50");
        json.put("lng","40");
        
        ChatroomLocationUpdateDTO CLUDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomLocationUpdateDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CLUDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailLatitude() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("lat","250");
        json.put("lng","40");
        
        ChatroomLocationUpdateDTO CLUDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomLocationUpdateDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CLUDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
    
    @Test
    public void testValidateFailLongitude() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("lat","50");
        json.put("lng","540");
        
        ChatroomLocationUpdateDTO CLUDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomLocationUpdateDTO.class);
        
        Pair<Boolean,ResponseEntity> r = CLUDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
}
