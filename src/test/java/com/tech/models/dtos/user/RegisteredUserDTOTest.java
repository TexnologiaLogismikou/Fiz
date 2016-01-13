/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.user;

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
public class RegisteredUserDTOTest {
    
    public RegisteredUserDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        RegisteredUserDTO.cleanValidator();        
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
        RegisteredUserDTO.cleanValidator();
    }
    
    

    @Test
    public void testRegisterUsernameValidator() throws Exception {
        List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterPasswordValidator() throws Exception {
        List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        
        str = RegisteredUserDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterEmailValidator() throws Exception {
        List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.EMAIL);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.EMAIL);
        
        str = RegisteredUserDTO.getValidatorList(ValidationScopes.EMAIL);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
       @Test
    public void testRegisterNotListedStringValidator() {
        try {
            RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        } catch (ValidatorNotListedException ex) {
            org.junit.Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            org.junit.Assert.fail("Validator should be appropriate should exist");
        }

    }
    
    @Test
    public void testRegisterWrongValidator() {
        try {
            List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.ROOM_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            RegisteredUserDTO.registerValidator(new LongitudeValidator(), ValidationScopes.ROOM_NAME);
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
            List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            RegisteredUserDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
    
    @Test
    public void testCleanValidator() throws Exception {
        RegisteredUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        RegisteredUserDTO.cleanValidator();
        str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }

    @Test
    public void testGetValidatorList() throws Exception {
        RegisteredUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }

    @Test
    public void testRemoveRoomNameValidator() throws Exception {
        RegisteredUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        RegisteredUserDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }

    @Test
    public void testRemoveUserNameValidator() throws Exception {
        RegisteredUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        RegisteredUserDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = RegisteredUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemovePasswordValidator() throws Exception {
        RegisteredUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.PASSWORD);
        RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        RegisteredUserDTO.removeValidator(ValidationScopes.PASSWORD, 1);
        
        str = RegisteredUserDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveEmailValidator() throws Exception {
        RegisteredUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.EMAIL);
        RegisteredUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.EMAIL);
        List<String> str = RegisteredUserDTO.getValidatorList(ValidationScopes.EMAIL);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        RegisteredUserDTO.removeValidator(ValidationScopes.EMAIL, 1);
        
        str = RegisteredUserDTO.getValidatorList(ValidationScopes.EMAIL);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception{
        Assert.assertFalse(RegisteredUserDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception{
        Assert.assertFalse(RegisteredUserDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorPasswordNonExist() throws Exception{
        Assert.assertFalse(RegisteredUserDTO.removeValidator(ValidationScopes.PASSWORD, 20));
    }
    
    @Test
    public void testRemoveValidatorEmailNonExist() throws Exception{
        Assert.assertFalse(RegisteredUserDTO.removeValidator(ValidationScopes.EMAIL, 110));
    }
    

    
    @Test
    public void testRemoveValidatorNotListed(){
        try {
            RegisteredUserDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
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
            RegisteredUserDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter(){
        RegisteredUserDTO MHRDTO = new RegisteredUserDTO();
        Assert.assertEquals("Failure expected the name to be the same","RegisteredUserDTO",MHRDTO.getDTOName());
    }
    
    @Test
    public void testValidateFailUsername() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("username","@mixalis");
        json.put("password","mixalis");
        json.put("email","mixalis@teicm.gr");
        json.put("last_name", "#@mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("firstname", "mixalis");
        
        RegisteredUserDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),RegisteredUserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
    
        @Test
    public void testValidateFailPassword() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","hi");
        json.put("email","mixalis@teicm.gr");
        json.put("last_name", "#@mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("firstname", "mixalis");
        
        RegisteredUserDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),RegisteredUserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
    
        @Test
    public void testValidateFailEmail() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        json.put("email","mix");
        json.put("last_name", "mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("firstname", "mixalis");
        
        RegisteredUserDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),RegisteredUserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
    
    @Test
    public void testValidateFailLastname() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        json.put("email","mixalis@teicm.gr");
        json.put("last_name", "#@mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("firstname", "mixalis");
        
        RegisteredUserDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),RegisteredUserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }
    
 
}
