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
public class UserDTOTest {
    
    public UserDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        UserDTO.cleanValidator();        
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
        UserDTO.cleanValidator();
    }
    
    @Test
    public void testRegisterUsernameValidator() throws Exception {
        List<String> str = UserDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = UserDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterPasswordValidator() throws Exception {
        List<String> str = UserDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        
        str = UserDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterEmailValidator() throws Exception {
        List<String> str = UserDTO.getValidatorList(ValidationScopes.EMAIL);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.EMAIL);
        
        str = UserDTO.getValidatorList(ValidationScopes.EMAIL);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterStringValidator() throws Exception {
        List<String> str = UserDTO.getValidatorList(ValidationScopes.STRING);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);
        
        str = UserDTO.getValidatorList(ValidationScopes.STRING);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterPhotoValidator() throws Exception {
        List<String> str = UserDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PROFILE_PHOTO);
        
        str = UserDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterStatusValidator() throws Exception {
        List<String> str = UserDTO.getValidatorList(ValidationScopes.STATUS);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STATUS);
        
        str = UserDTO.getValidatorList(ValidationScopes.STATUS);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterNotListedStringValidator() {
        try {
            UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
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
            List<String> str = UserDTO.getValidatorList(ValidationScopes.ROOM_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
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
            List<String> str = UserDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            UserDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
    
    @Test
    public void testCleanValidator() throws Exception {
        UserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = UserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        UserDTO.cleanValidator();
        str = UserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }

    @Test
    public void testGetValidatorList() throws Exception {
        UserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = UserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }

     @Test
    public void testRemoveUserNameValidator() throws Exception {
        UserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = UserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        UserDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = UserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemovePasswordValidator() throws Exception {
        UserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.PASSWORD);
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        List<String> str = UserDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        UserDTO.removeValidator(ValidationScopes.PASSWORD, 1);
        
        str = UserDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveEmailValidator() throws Exception {
        UserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.EMAIL);
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.EMAIL);
        List<String> str = UserDTO.getValidatorList(ValidationScopes.EMAIL);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        UserDTO.removeValidator(ValidationScopes.EMAIL, 1);
        
        str = UserDTO.getValidatorList(ValidationScopes.EMAIL);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
     @Test
    public void testRemoveStringValidator() throws Exception {
        UserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.STRING);
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);
        List<String> str = UserDTO.getValidatorList(ValidationScopes.STRING);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        UserDTO.removeValidator(ValidationScopes.STRING, 1);
        
        str = UserDTO.getValidatorList(ValidationScopes.STRING);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemovePhotoValidator() throws Exception {
        UserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.PROFILE_PHOTO);
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PROFILE_PHOTO);
        List<String> str = UserDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        UserDTO.removeValidator(ValidationScopes.PROFILE_PHOTO, 1);
        
        str = UserDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveStatusValidator() throws Exception {
        UserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.STATUS);
        UserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STATUS);
        List<String> str = UserDTO.getValidatorList(ValidationScopes.STATUS);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        UserDTO.removeValidator(ValidationScopes.STATUS, 1);
        
        str = UserDTO.getValidatorList(ValidationScopes.STATUS);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception{
        Assert.assertFalse(UserDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception{
        Assert.assertFalse(UserDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorPasswordNonExist() throws Exception{
        Assert.assertFalse(UserDTO.removeValidator(ValidationScopes.PASSWORD, 20));
    }
    
    @Test
    public void testRemoveValidatorEmailNonExist() throws Exception{
        Assert.assertFalse(UserDTO.removeValidator(ValidationScopes.EMAIL, 110));
    }
    
    @Test
    public void testRemoveValidatorStringNonExist() throws Exception{
        Assert.assertFalse(UserDTO.removeValidator(ValidationScopes.STRING, 110));
    }
    
    @Test
    public void testRemoveValidatorPhotoNonExist() throws Exception{
        Assert.assertFalse(UserDTO.removeValidator(ValidationScopes.PROFILE_PHOTO, 20));
    }
    
    @Test
    public void testRemoveValidatorStatusNonExist() throws Exception{
        Assert.assertFalse(UserDTO.removeValidator(ValidationScopes.STATUS, 110));
    }
    
    @Test
    public void testRemoveValidatorNotListed(){
        try {
            UserDTO.removeValidator(ValidationScopes.ROOM_NAME,1);            
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
            UserDTO.getValidatorList(ValidationScopes.ROOM_NAME);            
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter(){
        UserDTO UDTO = new UserDTO();
        Assert.assertEquals("Failure expected the name to be the same","UserDTO",UDTO.getDTOName());
    }
    
    @Test
    public void testValidateSuccess() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        json.put("enabled", true);
        json.put("hasRoom", true);
        json.put("email","mixalis@teicm.gr");
        json.put("profile_photo","teicm");
        json.put("status","mixalis");
        json.put("last_name", "mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("hometown", "Thessaloniki");
        json.put("firstname", "mixalis");
        
        UserDTO UDTO = JSONToolConverter.mapFromJson(json.toJSONString(),UserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = UDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    }    
    
    @Test
    public void testValidateFailUsername() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","@mixalis");
        json.put("password","mixalis");
        json.put("enabled", true);
        json.put("hasRoom", true);
        json.put("email","mixalis@teicm.gr");
        json.put("profile_photo","teicm");
        json.put("status","mixalis");
        json.put("last_name", "mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("hometown", "Thessaloniki");
        json.put("firstname", "mixalis");
        
        UserDTO UDTO = JSONToolConverter.mapFromJson(json.toJSONString(),UserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = UDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
        
        
    }
    
    @Test
    public void testValidateFailPassword() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mix");
        json.put("enabled", true);
        json.put("hasRoom", true);
        json.put("email","mixalis@teicm.gr");
        json.put("profile_photo","teicm");
        json.put("status","mixalis");
        json.put("last_name", "mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("hometown", "Thessaloniki");
        json.put("firstname", "mixalis");
        
        UserDTO UDTO = JSONToolConverter.mapFromJson(json.toJSONString(),UserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = UDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
        
        
    }
    
    @Test
    public void testValidateFail() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        json.put("enabled", true);
        json.put("hasRoom", true);
        json.put("email","mix");
        json.put("profile_photo","teicm");
        json.put("status","mixalis");
        json.put("last_name", "mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("hometown", "Thessaloniki");
        json.put("firstname", "mixalis");
        
        UserDTO UDTO = JSONToolConverter.mapFromJson(json.toJSONString(),UserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = UDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
        
        
    }
    
    @Test
    public void testValidateFailLastname() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        json.put("enabled", true);
        json.put("hasRoom", true);
        json.put("email","mixalis@teicm.gr");
        json.put("profile_photo","teicm");
        json.put("status","mixalis");
        json.put("last_name", "mi");
        json.put("birthday",new java.util.Date().getTime());
        json.put("hometown", "Thessaloniki");
        json.put("firstname", "mixalis");
        
        UserDTO UDTO = JSONToolConverter.mapFromJson(json.toJSONString(),UserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = UDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
        
        
    }
    
        @Test
    public void testValidateFailProfilePhoto() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        json.put("enabled", true);
        json.put("hasRoom", true);
        json.put("email","mixalis@teicm.gr");
        json.put("profile_photo",null);
        json.put("status","mixalis");
        json.put("last_name", "mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("hometown", "Thessaloniki");
        json.put("firstname", "mixalis");
        
        UserDTO UDTO = JSONToolConverter.mapFromJson(json.toJSONString(),UserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = UDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
        
        
    }
    
    @Test
    public void testValidateFailHometown() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        json.put("enabled", true);
        json.put("hasRoom", true);
        json.put("email","mixalis@teicm.gr");
        json.put("profile_photo","teicm");
        json.put("status","mixalis");
        json.put("last_name", "mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("hometown", null);
        json.put("firstname", "mixalis");
        
        UserDTO UDTO = JSONToolConverter.mapFromJson(json.toJSONString(),UserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = UDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
        
        
    }
    
        @Test
    public void testValidateFailStatus() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        json.put("enabled", true);
        json.put("hasRoom", true);
        json.put("email","mixalis@teicm.gr");
        json.put("profile_photo","teicm");
        json.put("status",null);
        json.put("last_name", "mixalis");
        json.put("birthday",new java.util.Date().getTime());
        json.put("hometown", "Thessaloniki");
        json.put("firstname", "mixalis");
        
        UserDTO UDTO = JSONToolConverter.mapFromJson(json.toJSONString(),UserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = UDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
        
        
    }
}
