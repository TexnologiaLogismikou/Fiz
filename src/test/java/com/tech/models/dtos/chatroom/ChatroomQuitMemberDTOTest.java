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
import org.json.simple.JSONObject;
import org.junit.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author KuroiTenshi
 */
public class ChatroomQuitMemberDTOTest {

    public ChatroomQuitMemberDTOTest() {
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
        ChatroomQuitMemberDTO.cleanValidator();
    }

    @Test
    public void testRegisterRoomNameValidator() throws Exception {
        List<String> str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomQuitMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);

        str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterUserNameValidator() throws Exception {
        List<String> str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomQuitMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);

        str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterWrongValidator() {
        try {
            List<String> str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomQuitMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);
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
            List<String> str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomQuitMemberDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.USER_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        }

    }

    @Test
    public void testCleanValidator() throws Exception {
        ChatroomQuitMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomQuitMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomQuitMemberDTO.cleanValidator();
        str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());

    }

    @Test
    public void testGetValidatorList() throws Exception {
        ChatroomQuitMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomQuitMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

    }

    @Test
    public void testRemoveRoomNameValidator() throws Exception {
        ChatroomQuitMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomQuitMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomQuitMemberDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);

        str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveUserNameValidator() throws Exception {
        ChatroomQuitMemberDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.USER_NAME);
        ChatroomQuitMemberDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
        List<String> str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.USER_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomQuitMemberDTO.removeValidator(ValidationScopes.USER_NAME, 1);

        str = ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.USER_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception {
        Assert.assertFalse(ChatroomQuitMemberDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }


    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception {
        Assert.assertFalse(ChatroomQuitMemberDTO.removeValidator(ValidationScopes.USER_NAME, 110));
    }

    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception {
        Assert.assertFalse(ChatroomQuitMemberDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }

    @Test
    public void testRemoveValidatorNotListed() {
        try {
            ChatroomQuitMemberDTO.removeValidator(ValidationScopes.PROFILE_PHOTO, 1);
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
            ChatroomQuitMemberDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }

    }

    @Test
    public void testNameGetter() {
        ChatroomQuitMemberDTO MHRDTO = new ChatroomQuitMemberDTO();
        Assert.assertEquals("Failure expected the name to be the same", "ChatroomQuitMemberDTO", MHRDTO.getDTOName());
    }

    @Test
    public void testValidateSuccess() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("room_name", "teicm");
        json.put("user_name", "mixalis");

        ChatroomQuitMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomQuitMemberDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertTrue("Failure expected true", r.getLeft());
    }


    @Test
    public void testValidateFailRoomName() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("room_name", "@teicm");//einai lathos gt exoume balei parametro na mn pernei tetoios xaraktires
        json.put("user_name", "mixalis");

        ChatroomQuitMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomQuitMemberDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }

    @Test
    public void testValidateFailUserName() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        JSONObject json = new JSONObject();

        json.put("room_name", "teicm");
        json.put("user_name", "%mixalis");

        ChatroomQuitMemberDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomQuitMemberDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected false", r.getLeft());
    }

}