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
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.FloatNotNaNValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LatitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LongitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MatchValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MinLenghtValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotEmptyValidatorS;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotMatchValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MessageHistoryRequestDTOTest {
    
    public MessageHistoryRequestDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        MessageHistoryRequestDTO.cleanValidator();        
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
        MessageHistoryRequestDTO.cleanValidator();
    }

    @Test
    public void testRegisterRoomNameValidator() throws Exception {
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }

    @Test
    public void testRegisterMemberNameValidator() throws Exception {
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterLatitudeValidator() throws Exception {
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageHistoryRequestDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: LatitudeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterLongitudeValidator() throws Exception {
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageHistoryRequestDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: LongitudeValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterWrongValidator() {
        try {
            List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            MessageHistoryRequestDTO.registerValidator(new LongitudeValidator(), ValidationScopes.USER_NAME);
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
            List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
    
    @Test
    public void testCleanValidator() throws Exception {
        MessageHistoryRequestDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        MessageHistoryRequestDTO.cleanValidator();
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }

    @Test
    public void testGetValidatorList() throws Exception {
        MessageHistoryRequestDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }

    @Test
    public void testRemoveRoomNameValidator() throws Exception {
        MessageHistoryRequestDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        MessageHistoryRequestDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }

    @Test
    public void testRemoveUserNameValidator() throws Exception {
        MessageHistoryRequestDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        MessageHistoryRequestDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveLatitudeValidator() throws Exception {
        MessageHistoryRequestDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
        MessageHistoryRequestDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        MessageHistoryRequestDTO.removeValidator(ValidationScopes.LATITUDE, 1);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.LATITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveLongitudeValidator() throws Exception {
        MessageHistoryRequestDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
        MessageHistoryRequestDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        MessageHistoryRequestDTO.removeValidator(ValidationScopes.LONGITUDE, 1);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.LONGITUDE);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception{
        Assert.assertFalse(MessageHistoryRequestDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception{
        Assert.assertFalse(MessageHistoryRequestDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception{
        Assert.assertFalse(MessageHistoryRequestDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }
    
    @Test
    public void testRemoveValidatorLatitudeNonExist() throws Exception{
        Assert.assertFalse(MessageHistoryRequestDTO.removeValidator(ValidationScopes.LATITUDE, 50));
    }
    
    @Test
    public void testRemoveValidatorLongitudeNonExist() throws Exception{
        Assert.assertFalse(MessageHistoryRequestDTO.removeValidator(ValidationScopes.LONGITUDE, 60));
    }
    
    @Test
    public void testRemoveValidatorNotListed(){
        try {
            MessageHistoryRequestDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
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
            MessageHistoryRequestDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter(){
        MessageHistoryRequestDTO MHRDTO = new MessageHistoryRequestDTO();
        Assert.assertEquals("Failure expected the name to be the same","MessageHistoryRequestDTO",MHRDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("lat","50");
        json.put("lng","40");
        
        MessageHistoryRequestDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageHistoryRequestDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    }    
    
    @Test
    public void testValidateFailRoomName() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","@teicm");
        json.put("member_name","mixalis");
        json.put("lat","50");
        json.put("lng","40");
        
        MessageHistoryRequestDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageHistoryRequestDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
    
    @Test
    public void testValidateFailMemberName() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","%mixalis");
        json.put("lat","50");
        json.put("lng","40");
        
        MessageHistoryRequestDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageHistoryRequestDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }   
    
    @Test
    public void testValidateFailLatitude() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("lat","250");
        json.put("lng","40");
        
        MessageHistoryRequestDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageHistoryRequestDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
    
    @Test
    public void testValidateFailLongitude() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","@mixalis");
        json.put("lat","50");
        json.put("lng","540");
        
        MessageHistoryRequestDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageHistoryRequestDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
}
