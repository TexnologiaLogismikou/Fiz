/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos.chatroom;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MatchValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MinLenghtValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotMatchValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.NoValidatorsAssignedException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import java.io.IOException;
import java.util.List;
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
public class ChatroomUpdateDTOTest {
    
    public ChatroomUpdateDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
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
        
        ChatroomUpdateDTO dto = (ChatroomUpdateDTO)mapFromJson(json.toJSONString(),ChatroomUpdateDTO.class);
        
        Pair<Boolean,ResponseEntity> a = dto.validate();
    }
    
    @Test
    public void testAddingPrintingAndRemovingValidators() throws ValidatorNotListedException, InappropriateValidatorException{
        ChatroomUpdateDTO.cleanValidator();
        printList();
        
        ChatroomUpdateDTO.registerValidator(new MaxLengthValidator(15), ValidationScopes.ROOM_NAME);
        printList();
        
        ChatroomUpdateDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
        ChatroomUpdateDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
        printList();
        
        ChatroomUpdateDTO.removeValidator(ValidationScopes.ROOM_NAME,2);
        printList();        
    }
    
    
    private void printList() throws ValidatorNotListedException{        
        List<String> list = ChatroomUpdateDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        System.out.println("Print starting :\n");
        if(!list.isEmpty()){
            for(String vLookUp:list){
                System.out.println(vLookUp);
            }
        }
        System.out.println("\nPrint ended...\n");
        
    }
    
    private <T> T mapFromJson(String json,Class<T> clazz) throws JsonParseException,JsonMappingException,IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    @Test
    public void testCleanValidator() {
    }

    @Test
    public void testRegisterValidator() throws Exception {
    }

    @Test
    public void testGetValidatorList() throws Exception {
    }

    @Test
    public void testRemoveValidator() throws Exception {
    }

    @Test
    public void testValidate() {
    }

    @Test
    public void testGetDTOName() {
    }

    @Test
    public void testGetRoom_name() {
    }

    @Test
    public void testGetNew_room_name() {
    }

    @Test
    public void testGetRoom_privilege() {
    }

    @Test
    public void testGetAccess_method() {
    }

    @Test
    public void testGetPassword() {
    }

    @Test
    public void testGetMax_range() {
    }

    @Test
    public void testIsPasswordProtection() {
    }
    
}
