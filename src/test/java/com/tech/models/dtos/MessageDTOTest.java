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
import com.tech.configurations.tools.customvalidators.elements.EmptyFloatValidator;
import com.tech.configurations.tools.customvalidators.elements.EmptyNumberValidator;
import com.tech.configurations.tools.customvalidators.elements.EmptyValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.FloatNotNaNValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LatitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LongitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.MaxNumberAllowed;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotNegativeValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotEmptyValidatorS;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.chatroom.ChatroomCheckInsideDTO;
import com.tech.models.dtos.chatroom.ChatroomUpdateDTO;
import org.json.simple.JSONObject;
import org.junit.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author Aenaos
 */
public class MessageDTOTest {
    
    public MessageDTOTest() {
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
        MessageDTO.cleanValidator();
    }

    @Test
    public void testRegisterLongtitudeValidator() throws Exception {
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);

        str = MessageDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be LongitudeValidator", "1: LongitudeValidator", str.get(0));
    }

    @Test
    public void testRegisterLatitudeValidator() throws Exception {
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);

        str = MessageDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: LatitudeValidator", str.get(0));
    }

    @Test
    public void testRegisterRoomNameValidator() throws Exception {
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);

        str = MessageDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterUserNameValidator() throws Exception {
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);

        str = MessageDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterMessageValidator() throws Exception {
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.STRING);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);

        str = MessageDTO.getValidatorList(ValidationScopes.STRING);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }


    @Test
    public void testRegisterTTLValidator() throws Exception {
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.TTL);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.TTL);

        str = MessageDTO.getValidatorList(ValidationScopes.TTL);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NotEmptyValidatorN", str.get(0));
    }

    @Test
    public void testRegisterNotListedStringValidator() {
        try {
            MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.EMAIL);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            Assert.fail("Validator should be appropriate should exist");
        }

    }

    @Test
    public void testRegisterNotListedFloatValidator() {
        try {
            MessageDTO.registerValidator(new EmptyFloatValidator(), ValidationScopes.EMAIL);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            Assert.fail("Validator should be appropriate should exist");
        }

    }

    @Test
    public void testRegisterNotListedNumberValidator() {
        try {
            MessageDTO.registerValidator(new EmptyNumberValidator(), ValidationScopes.EMAIL);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Exception should be ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            Assert.fail("Validator should be appropriate should exist");
        }

    }


    @Test
    public void testRegisterInappropriateValidator() {
        try {
            MessageDTO.registerValidator(new EmptyValidator(), ValidationScopes.EMAIL);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should get InappropriateValidatorException");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        }

    }

    @Test
    public void testCleanValidator() throws Exception {
        MessageDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        MessageDTO.cleanValidator();
        str = MessageDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());

    }

    @Test
    public void testGetValidatorList() throws Exception {
        MessageDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

    }


    @Test
    public void testRemoveLongtitudeValidator() throws Exception {
        MessageDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
        MessageDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.LONGITUDE);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        MessageDTO.removeValidator(ValidationScopes.LONGITUDE, 1);

        str = MessageDTO.getValidatorList(ValidationScopes.LONGITUDE);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveUserNameValidator() throws Exception {
        MessageDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.USER_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        MessageDTO.removeValidator(ValidationScopes.USER_NAME, 1);

        str = MessageDTO.getValidatorList(ValidationScopes.USER_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }


    @Test
    public void testRemoveLatitudeValidator() throws Exception {
        MessageDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
        MessageDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.LATITUDE);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        MessageDTO.removeValidator(ValidationScopes.LATITUDE, 1);

        str = MessageDTO.getValidatorList(ValidationScopes.LATITUDE);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveRoomNameValidator() throws Exception {
        MessageDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        MessageDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);

        str = MessageDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveMessageValidator() throws Exception {
        MessageDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.STRING);
        MessageDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.STRING);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        MessageDTO.removeValidator(ValidationScopes.STRING, 1);

        str = MessageDTO.getValidatorList(ValidationScopes.STRING);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveTTLValidator() throws Exception {
        MessageDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.TTL);
        MessageDTO.registerValidator(new NotNegativeValidator(), ValidationScopes.TTL);
        List<String> str = MessageDTO.getValidatorList(ValidationScopes.TTL);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        MessageDTO.removeValidator(ValidationScopes.TTL, 1);

        str = MessageDTO.getValidatorList(ValidationScopes.TTL);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception {
        Assert.assertFalse(MessageDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }


    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception {
        Assert.assertFalse(MessageDTO.removeValidator(ValidationScopes.USER_NAME, 20));
    }


    @Test
    public void testRemoveValidatorRoomNameNonExist() throws Exception {
        Assert.assertFalse(MessageDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }

    @Test
    public void testRemoveValidatorLatitudeNonExist() throws Exception {
        Assert.assertFalse(MessageDTO.removeValidator(ValidationScopes.LATITUDE, 20));
    }

    @Test
    public void testRemoveValidatorLongitudeNonExist() throws Exception {
        Assert.assertFalse(MessageDTO.removeValidator(ValidationScopes.LONGITUDE, 10));
    }

    @Test
    public void testRemoveValidatorStringNonExist() throws Exception {
        Assert.assertFalse(MessageDTO.removeValidator(ValidationScopes.STRING, 10));
    }


    @Test
    public void testRemoveValidatorTTLNonExist() throws Exception {
        Assert.assertFalse(MessageDTO.removeValidator(ValidationScopes.TTL, 10));
    }

    @Test
    public void testRemoveValidatorNotListed() {
        try {
            MessageDTO.removeValidator(ValidationScopes.PROFILE_PHOTO, 1);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        } catch (InappropriateValidatorException ex) {
            Assert.fail("InappropriateValidatorException should not rise here");
        }

    }

    @Test
    public void testGetValidatorListNotListed() {
        try {
            MessageDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }

    }

    @Test
    public void testNameGetter() {
        MessageDTO MHRDTO = new MessageDTO();
        Assert.assertEquals("Failure expected the name to be the same", "MessageDTO", MHRDTO.getDTOName());
    }

    @Test
    public void testValidateSuccess() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("chatroom_name", "teicm");
        json.put("username", "mixalis");
        json.put("lng", 0.0);
        json.put("lat", 0.0);
        json.put("message", "shdkgjhsdlgjds");
        json.put("ttl", 15);

        MessageDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), MessageDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertTrue("Failure expected true", r.getLeft());
    }


    @Test
    public void testValidateFailRoomName() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("chatroom_name", "@teicm");
        json.put("username", "mixalis");
        json.put("lng", 0.0);
        json.put("lat", 0.0);
        json.put("message", "shdkgjhsdlgjds");
        json.put("ttl", 15);

        MessageDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), MessageDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }

    @Test
    public void testValidateFailUserName() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("chatroom_name", "teicm");
        json.put("username", "@mixalis");
        json.put("lng", 0.0);
        json.put("lat", 0.0);
        json.put("message", "shdkgjhsdlgjds");
        json.put("ttl", 15);

        MessageDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), MessageDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }

    @Test
    public void testValidateFailLongitude() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("chatroom_name", "teicm");
        json.put("username", "mixalis");
        json.put("lng", 70000);
        json.put("lat", 0.0);
        json.put("message", "shdkgjhsdlgjds");
        json.put("ttl", 15);

        MessageDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), MessageDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }

    @Test
    public void testValidateFailLatitude() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("chatroom_name", "teicm");
        json.put("username", "mixalis");
        json.put("lng", 0.0);
        json.put("lat", 70000);
        json.put("message", "shdkgjhsdlgjds");
        json.put("ttl", 15);

        MessageDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), MessageDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }

    @Test
    public void testValidateFailMessage() throws Exception {


        InitializeValidators.InitializeCustomValidators();
        MessageDTO.registerValidator(new MaxLengthValidator(10), ValidationScopes.STRING);
        JSONObject json = new JSONObject();

        json.put("chatroom_name", "teicm");
        json.put("username", "mixalis");
        json.put("lng", 0.0);
        json.put("lat", 0.0);
        json.put("message", "abcdefghijkl");
        json.put("ttl", 15);

        MessageDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), MessageDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }


    @Test
    public void testValidateFailTTL() throws Exception {
        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("chatroom_name", "teicm");
        json.put("username", "mixalis");
        json.put("lng", 0.0);
        json.put("lat", 0.0);
        json.put("message", "shdkgjhsdlgjds");
        json.put("ttl", -150);

        MessageDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), MessageDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }


}
