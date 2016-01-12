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
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.FloatNotNaNValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LatitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LongitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import org.json.simple.JSONObject;
import org.junit.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Aenaos
 */
public class ChatroomCheckInsideDTOTest {

    public ChatroomCheckInsideDTOTest() {
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
        ChatroomCheckInsideDTO.cleanValidator();
    }

    @Test
    public void testRegisterLongtitudeValidator() throws Exception {
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCheckInsideDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);

        str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.LONGITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be LongitudeValidator", "1: LongitudeValidator", str.get(0));
    }

    @Test
    public void testRegisterLatitudeValidator() throws Exception {
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCheckInsideDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);

        str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.LATITUDE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: LatitudeValidator", str.get(0));
    }

    @Test
    public void testRegisterRoomNameValidator() throws Exception {
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCheckInsideDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);

        str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterUserNameValidator() throws Exception {
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomCheckInsideDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);

        str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterWrongValidator() {
        try {
            List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomCheckInsideDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);
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
            List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomCheckInsideDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        }

    }

    @Test
    public void testCleanValidator() throws Exception {
        ChatroomCheckInsideDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomCheckInsideDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomCheckInsideDTO.cleanValidator();
        str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());

    }

    @Test
    public void testGetValidatorList() throws Exception {
        ChatroomCheckInsideDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomCheckInsideDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

    }


    @Test
    public void testRemoveLongtitudeValidator() throws Exception {
        ChatroomCheckInsideDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
        ChatroomCheckInsideDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.LONGITUDE);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomCheckInsideDTO.removeValidator(ValidationScopes.LONGITUDE, 1);

        str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.LONGITUDE);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }


    @Test
    public void testRemoveLatitudeValidator() throws Exception {
        ChatroomCheckInsideDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
        ChatroomCheckInsideDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.LATITUDE);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomCheckInsideDTO.removeValidator(ValidationScopes.LATITUDE, 1);

        str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.LATITUDE);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveRoomNameValidator() throws Exception {
        ChatroomCheckInsideDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomCheckInsideDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomCheckInsideDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);

        str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveUserNameValidator() throws Exception {
        ChatroomCheckInsideDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ChatroomCheckInsideDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.USER_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomCheckInsideDTO.removeValidator(ValidationScopes.USER_NAME, 1);

        str = ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.USER_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception {
        Assert.assertFalse(ChatroomCheckInsideDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }


    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception {
        Assert.assertFalse(ChatroomCheckInsideDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }

    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception {
        Assert.assertFalse(ChatroomCheckInsideDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }

    @Test
    public void testRemoveValidatorNotListed() {
        try {
            ChatroomCheckInsideDTO.removeValidator(ValidationScopes.PROFILE_PHOTO, 1);
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
            ChatroomCheckInsideDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }

    }

    @Test
    public void testNameGetter() {
        ChatroomCheckInsideDTO MHRDTO = new ChatroomCheckInsideDTO();
        Assert.assertEquals("Failure expected the name to be the same", "ChatroomCheckInsideDTO", MHRDTO.getDTOName());
    }

    @Test
    public void testValidateSuccess() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("room_name", "teicm");
        json.put("user_name", "mixalis");
        json.put("lng", 0.0);
        json.put("lat", 0.0);

        ChatroomCheckInsideDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomCheckInsideDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertTrue("Failure expected true", r.getLeft());
    }


    @Test
    public void testValidateFailRoomName() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("room_name", "@teicm");//einai lathos gt exoume balei parametro na mn pernei tetoios xaraktires
        json.put("user_name", "mixalis");
        json.put("lng", 0.0);
        json.put("lat", 0.0);

        ChatroomCheckInsideDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomCheckInsideDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }



    @Test
    public void testValidateFailUserName() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("room_name", "teicm");
        json.put("user_name", "%mixalis");
        json.put("lng",0.0);
        json.put("lat",0.0);

        ChatroomCheckInsideDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomCheckInsideDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected false", r.getLeft());
    }
}
