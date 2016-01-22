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
public class LoginUserDTOTest {
    
    public LoginUserDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        LoginUserDTO.cleanValidator();        
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
        LoginUserDTO.cleanValidator();
    }    
    
    

    @Test
    public void testRegisterUsernameValidator() throws Exception {
        List<String> str = LoginUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = LoginUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterPasswordValidator() throws Exception {
        List<String> str = LoginUserDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        
        str = LoginUserDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterNotListedStringValidator() {
        try {
            LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.EMAIL);
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
            List<String> str = LoginUserDTO.getValidatorList(ValidationScopes.ROOM_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
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
            List<String> str = LoginUserDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            LoginUserDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
    
    @Test
    public void testCleanValidator() throws Exception {
        LoginUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = LoginUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        LoginUserDTO.cleanValidator();
        str = LoginUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }

    @Test
    public void testGetValidatorList() throws Exception {
        LoginUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = LoginUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }


    @Test
    public void testRemoveUserNameValidator() throws Exception {
        LoginUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = LoginUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        LoginUserDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = LoginUserDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemovePasswordValidator() throws Exception {
        LoginUserDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.PASSWORD);
        LoginUserDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        List<String> str = LoginUserDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        LoginUserDTO.removeValidator(ValidationScopes.PASSWORD, 1);
        
        str = LoginUserDTO.getValidatorList(ValidationScopes.PASSWORD);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception{
        Assert.assertFalse(LoginUserDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception{
        Assert.assertFalse(LoginUserDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }
    
    @Test
    public void testRemoveValidatorPasswordNonExist() throws Exception{
        Assert.assertFalse(LoginUserDTO.removeValidator(ValidationScopes.PASSWORD, 100));
    }
    
    
    
    @Test
    public void testRemoveValidatorNotListed(){
        try {
            LoginUserDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
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
            LoginUserDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter(){
        LoginUserDTO LUDTO = new LoginUserDTO();
        Assert.assertEquals("Failure expected the name to be the same","LoginUserDTO",LUDTO.getDTOName());
    }
    
    
       
    
    @Test
    public void testValidateFailUsername() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("username","@mixalis");
        json.put("password","mixalis");
        
        LoginUserDTO LUDTO = JSONToolConverter.mapFromJson(json.toJSONString(),LoginUserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = LUDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
    
    @Test
    public void testValidateSuccess() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        
        LoginUserDTO LUDTO = JSONToolConverter.mapFromJson(json.toJSONString(),LoginUserDTO.class);
        
        Pair<Boolean,ResponseEntity> r = LUDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    }       
    
    @Test
    public void testGetters() throws Exception {
        
        JSONObject json = new JSONObject();
        
        json.put("username","mixalis");
        json.put("password","mixalis");
        
        LoginUserDTO LUDTO = JSONToolConverter.mapFromJson(json.toJSONString(),LoginUserDTO.class);
        
        Assert.assertTrue("Failure expected true",LUDTO.getPassword().equals("mixalis"));
        Assert.assertTrue("Failure expected true",LUDTO.getUsername().equals("mixalis"));
    }
    
    
}
