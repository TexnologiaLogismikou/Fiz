/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.JSONToolConverter;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.FloatNotNaNValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LatitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LongitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotNegativeValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.*;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.NoValidatorsAssignedException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;

import java.io.IOException;
import java.util.List;

import net.minidev.json.JSONObject;
import org.junit.*;
import org.springframework.http.ResponseEntity;

/**
 * @author Aenaos
 */
public class ChatroomUpdateDTOTest {

    public ChatroomUpdateDTOTest() {
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
    public void testSomething() throws InappropriateValidatorException, ValidatorNotListedException, JsonMappingException, IOException, NoValidatorsAssignedException {
        ChatroomUpdateDTO.registerValidator(new MinLenghtValidator(3), ValidationScopes.ROOM_NAME);
        ChatroomUpdateDTO.registerValidator(new MinLenghtValidator(3), ValidationScopes.PASSWORD);
        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
        ChatroomUpdateDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
        ChatroomUpdateDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.ROOM_NAME);

        JSONObject json = new JSONObject();
        json.put("room_name", "adasdasdasdsaasa");
        json.put("new_room_name", "aasdfsdfsddfsa");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "Blacklist");
        json.put("passwordProtection", "true");
        json.put("password", "mixalis");
        json.put("max_range", 300);

        ChatroomUpdateDTO dto = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomUpdateDTO.class);

        Pair<Boolean, ResponseEntity> a = dto.validate();
    }

    @Test
    public void testAddingPrintingAndRemovingValidators() throws ValidatorNotListedException, InappropriateValidatorException {
        ChatroomUpdateDTO.cleanValidator();
        printList();

        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.ROOM_NAME);
        printList();

        ChatroomUpdateDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
        ChatroomUpdateDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        printList();

        ChatroomUpdateDTO.removeValidator(ValidationScopes.ROOM_NAME, 2);
        printList();
    }


    private void printList() throws ValidatorNotListedException {
        List<String> list = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        System.out.println("Print starting :\n");
        if (!list.isEmpty()) {
            for (String vLookUp : list) {
                System.out.println(vLookUp);
            }
        }
        System.out.println("\nPrint ended...\n");

    }


    @Test
    public void testRegisterRoomAccessMethodValidator() throws Exception {
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_ACCESS_METHOD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_ACCESS_METHOD);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_ACCESS_METHOD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be LongitudeValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterPasswordValidator() throws Exception {
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.PASSWORD);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterRoomNameValidator() throws Exception {
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterRoomPrivilegeValidator() throws Exception {
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_PRIVILEGE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_PRIVILEGE);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_PRIVILEGE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));
    }

    @Test
    public void testRegisterRoomRangeValidator() throws Exception {
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.RANGE);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        ChatroomUpdateDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.RANGE);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.RANGE);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NotEmptyValidatorN", str.get(0));
    }

    @Test
    public void testRegisterWrongValidator() {
        try {
            List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.USER_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.STRING);
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
            List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
            Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
            ChatroomUpdateDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
        } catch (ValidatorNotListedException ex) {
            Assert.fail("Should not reach this");
        } catch (InappropriateValidatorException ex) {
            Assert.assertTrue("Exception should be InappropriateValidatorException",
                    ex.getMessage().equals(new InappropriateValidatorException().getMessage()));
        }
    }

    @Test
    public void testCleanValidator() throws Exception {
        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomUpdateDTO.cleanValidator();
        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());

    }

    @Test
    public void testGetValidatorList() throws Exception {
        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

    }

    @Test
    public void testRemovePasswordValidator() throws Exception {
        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.PASSWORD);
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.PASSWORD);
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.PASSWORD);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomUpdateDTO.removeValidator(ValidationScopes.PASSWORD, 1);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.PASSWORD);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }


    @Test
    public void testRemoveRoomAccessMethodValidator() throws Exception {
        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_ACCESS_METHOD);
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_ACCESS_METHOD);
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_ACCESS_METHOD);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomUpdateDTO.removeValidator(ValidationScopes.ROOM_ACCESS_METHOD, 1);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_ACCESS_METHOD);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveRoomNameValidator() throws Exception {
        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomUpdateDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveRangeValidator() throws Exception {
        ChatroomUpdateDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.RANGE);
        ChatroomUpdateDTO.registerValidator(new NotNegativeValidator(), ValidationScopes.RANGE);
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.RANGE);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomUpdateDTO.removeValidator(ValidationScopes.RANGE, 1);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.RANGE);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemovePrivilegeValidator() throws Exception {
        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_PRIVILEGE);
        ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_PRIVILEGE);
        List<String> str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_PRIVILEGE);

        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size());

        ChatroomUpdateDTO.removeValidator(ValidationScopes.ROOM_PRIVILEGE, 1);

        str = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_PRIVILEGE);

        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size());

    }

    @Test
    public void testRemoveValidatorOnZeroSpot() throws Exception {
        Assert.assertFalse(ChatroomUpdateDTO.removeValidator(ValidationScopes.USER_NAME, 0));
    }


    @Test
    public void testRemoveValidatorUsernameNonExist() throws Exception {
        Assert.assertFalse(ChatroomUpdateDTO.removeValidator(ValidationScopes.ROOM_ACCESS_METHOD, 20));
    }

    @Test
    public void testRemoveValidatorRoomnameNonExist() throws Exception {
        Assert.assertFalse(ChatroomUpdateDTO.removeValidator(ValidationScopes.ROOM_NAME, 20));
    }

    @Test
    public void testRemoveValidatorNotListed() {
        try {
            ChatroomUpdateDTO.removeValidator(ValidationScopes.PROFILE_PHOTO, 1);
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
            ChatroomUpdateDTO.getValidatorList(ValidationScopes.PROFILE_PHOTO);
        } catch (ValidatorNotListedException ex) {
            Assert.assertTrue("Expected ValidatorNotListedException",
                    ex.getMessage().equals(new ValidatorNotListedException().getMessage()));
        }

    }

    @Test
    public void testNameGetter() {
        ChatroomUpdateDTO MHRDTO = new ChatroomUpdateDTO();
        Assert.assertEquals("Failure expected the name to be the same", "ChatroomUpdateDTO", MHRDTO.getDTOName());
    }

    @Test
    public void testValidateSuccess() throws Exception {

        org.json.simple.JSONObject json = new org.json.simple.JSONObject();

        json.put("room_name", "room");
        json.put("new_room_name", "room1");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "Blacklist");
        json.put("passwordProtection", "true");
        json.put("password", "mixalis");
        json.put("max_range", 300);


        ChatroomUpdateDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomUpdateDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertTrue("Failure expected true", r.getLeft());
    }


    @Test
    public void testValidateFailRoomName() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        org.json.simple.JSONObject json = new org.json.simple.JSONObject();

        json.put("room_name", "*room");
        json.put("new_room_name", "room1");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "Blacklist");
        json.put("passwordProtection", "true");
        json.put("password", "mixalis");
        json.put("max_range", 300);

        ChatroomUpdateDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomUpdateDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }

    @Test
    public void testValidateNewRoomName() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        org.json.simple.JSONObject json = new org.json.simple.JSONObject();

        json.put("room_name", "room");
        json.put("new_room_name", "@room1");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "Blacklist");
        json.put("passwordProtection", "true");
        json.put("password", "mixalis");
        json.put("max_range", 300);

        ChatroomUpdateDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomUpdateDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }

    @Test
    public void testValidateFailPrivilege() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        org.json.simple.JSONObject json = new org.json.simple.JSONObject();

        json.put("room_name", "room");
        json.put("new_room_name", "room1");
        json.put("room_privilege", "@makis");
        json.put("access_method", "Blacklist");
        json.put("passwordProtection", "true");
        json.put("password", "mixalis");
        json.put("max_range", 300);

        ChatroomUpdateDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomUpdateDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected true", r.getLeft());
    }

    @Test
    public void testValidateFailRoomAccessMethod() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        org.json.simple.JSONObject json = new org.json.simple.JSONObject();

        json.put("room_name", "room");
        json.put("new_room_name", "room1");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "@Blacklist");
        json.put("passwordProtection", "true");
        json.put("password", "mixalis");
        json.put("max_range", 300);

        ChatroomUpdateDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomUpdateDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected false", r.getLeft());
    }

    @Test
    public void testValidateFailPassword() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        org.json.simple.JSONObject json = new org.json.simple.JSONObject();

        json.put("room_name", "room");
        json.put("new_room_name", "room1");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "Blacklist");
        json.put("passwordProtection", "true");
        json.put("password", "");
        json.put("max_range", 300);

        ChatroomUpdateDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomUpdateDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected false", r.getLeft());
    }

    @Test
    public void testValidateFailPasswordProtection() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        org.json.simple.JSONObject json = new org.json.simple.JSONObject();

        json.put("room_name", "room");
        json.put("new_room_name", "room1");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "Blacklist");
        json.put("passwordProtection", "");
        json.put("password", "enaduo");
        json.put("max_range", 300);

        ChatroomUpdateDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomUpdateDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected false", r.getLeft());
    }

    @Test
    public void testValidateFailRange() throws Exception {

        InitializeValidators.InitializeCustomValidators();
        org.json.simple.JSONObject json = new org.json.simple.JSONObject();

        json.put("room_name", "room");
        json.put("new_room_name", "room1");
        json.put("room_privilege", "PUBLIC");
        json.put("access_method", "Blacklist");
        json.put("passwordProtection", "");
        json.put("password", "enaduo");
        json.put("max_range", "60000");

        ChatroomUpdateDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(), ChatroomUpdateDTO.class);

        Pair<Boolean, ResponseEntity> r = MHRDTO.validate();

        Assert.assertFalse("Failure expected false", r.getLeft());
    }
}


