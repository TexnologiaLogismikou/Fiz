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
public class ChatroomBlacklistDTOTest {
    
    public ChatroomBlacklistDTOTest() {
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
        ChatroomBlacklistDTO.cleanValidator();
    }
    
    @Test
    public void testRegisterRoomNameValidator() throws Exception { 
        List<String> str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.ROOM_NAME); //einai mia lista me ts periorismous pou exw balei st validator
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomBlacklistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);//kalw to validator kai tou dinw mono mi timi.to ROOMNAME
        
        str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterUserNameValidator() throws Exception {
        List<String> str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomBlacklistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        
        str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
    }
    
    @Test
    public void testRegisterWrongValidator() {
        try {
            List<String> str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.USER_NAME);//an einai lathos auto den mpenei pouthena allo
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomBlacklistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);//bazw sto new ena balidator pou iparxei(NoSpace) kai tou dinw ena sting
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
            List<String> str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomBlacklistDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        } 
    }
    
    @Test
    public void testCleanValidator() throws Exception {
        ChatroomBlacklistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomBlacklistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomBlacklistDTO.cleanValidator();
        str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size()); 
    }
    
    @Test
    public void testGetValidatorList() throws Exception {
        ChatroomBlacklistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomBlacklistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
    }
    
    @Test
    public void testRemoveRoomNameValidator() throws Exception {
        ChatroomBlacklistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomBlacklistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomBlacklistDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveUserNameValidator() throws Exception {
        ChatroomBlacklistDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ChatroomBlacklistDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        ChatroomBlacklistDTO.removeValidator(ValidationScopes.USER_NAME, 1);
        
        str = ChatroomBlacklistDTO.getValidatorList(ValidationScopes.USER_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }
    
    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception{
        Assert.assertFalse(ChatroomBlacklistDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }
    
//    @Test
//    public void testRemoveValidatorUsernameNonExist() throws Exception{
//        Assert.assertFalse(ChatroomBlacklistDTO.removeValidator(ValidationScopes.USER_NAME, 110));
//    }
//    
//    @Test
//    public void testRemoveValidatorRoomnameNonExist() throws Exception{
//        Assert.assertFalse(ChatroomBlacklistDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
//    }
    
    @Test
    public void testRemoveValidatorNotListed(){
        try {
            ChatroomBlacklistDTO.removeValidator(ValidationScopes.PROFILE_PHOTO,1);            
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
            ChatroomBlacklistDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);            
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }   
    }
    
    @Test
    public void testNameGetter(){
        ChatroomBlacklistDTO MHRDTO = new ChatroomBlacklistDTO();
        Assert.assertEquals("Failure expected the name to be the same","ChatroomBlacklistDTO",MHRDTO.getDTOName());
    }
    
    @Test/*!!!!!!!!!!!!!!!!!*/
    public void testValidateSuccess() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
                
        ChatroomBlacklistDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomBlacklistDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertTrue("Failure expected true",r.getLeft());
    }   
    
    @Test
    public void testValidateFailRoomName() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","@teicm");
        json.put("member_name","mixalis");
               
        ChatroomBlacklistDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomBlacklistDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected true",r.getLeft());
    }   
    
    @Test
    public void testValidateFailMemberName() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","%mixalis");
                
        ChatroomBlacklistDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),ChatroomBlacklistDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        Assert.assertFalse("Failure expected false",r.getLeft());
    }  

}
