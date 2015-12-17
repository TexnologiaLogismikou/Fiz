/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.configurations.tools.JSONToolConverter;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyStringValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.FloatNotNaNValidator;
import com.tech.configurations.tools.customvalidators.elements.floatvalidator.LongitudeValidator;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MatchValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MaxLengthValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.MinLenghtValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotEmptyValidatorS;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotMatchValidator;
import java.io.IOException;
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
public class MessageHistoryRequestDTOTest {
    
    public MessageHistoryRequestDTOTest() {
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
        MessageHistoryRequestDTO.cleanValidator();
    }

    @Test
    public void testRegisterValidator() throws Exception {
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 0", 0, str.size());
        MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
        Assert.assertEquals("Failure - expected validatorList size to be NoSpacesValidator", "1: NoSpacesValidator", str.get(0));   
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
    public void testRemoveValidator() throws Exception {
        MessageHistoryRequestDTO.registerValidator(new MaxLengthValidator(2), ValidationScopes.ROOM_NAME);
        MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
        List<String> str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 2", 2, str.size()); 
        
        MessageHistoryRequestDTO.removeValidator(ValidationScopes.ROOM_NAME, 1);
        
        str = MessageHistoryRequestDTO.getValidatorList(ValidationScopes.ROOM_NAME);
        
        Assert.assertEquals("Failure - expected validatorList size to be 1", 1, str.size()); 
    }

    @Test
    public void testValidate() throws Exception {
        initializeMessageHistoryRequestDTO();
        JSONObject json = new JSONObject();
        
        json.put("room_name","teicm");
        json.put("member_name","mixalis");
        json.put("lat","50");
        json.put("lng","40");
        
        MessageHistoryRequestDTO MHRDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageHistoryRequestDTO.class);
        
        Pair<Boolean,ResponseEntity> r = MHRDTO.validate();
        
        System.out.println("");
    }    
    
    private void initializeMessageHistoryRequestDTO() throws Exception{
            MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.ROOM_NAME);
            MessageHistoryRequestDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.ROOM_NAME);
            
//            MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorS(), ValidationScopes.USER_NAME);
//            MessageHistoryRequestDTO.registerValidator(new NoSpacesValidator(), ValidationScopes.USER_NAME);
//            MessageHistoryRequestDTO.registerValidator(new MaxLengthValidator(16), ValidationScopes.USER_NAME);
//            MessageHistoryRequestDTO.registerValidator(new MinLenghtValidator(4), ValidationScopes.USER_NAME);
//            MessageHistoryRequestDTO.registerValidator(new MatchValidator("[^A-Za-z0-9]"), ValidationScopes.USER_NAME);
//            MessageHistoryRequestDTO.registerValidator(new NotMatchValidator("^[A-Za-z]"), ValidationScopes.USER_NAME);
            
//            MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LONGITUDE);
            MessageHistoryRequestDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LONGITUDE);
            MessageHistoryRequestDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LONGITUDE);
            
            //MessageHistoryRequestDTO.registerValidator(new NotEmptyValidatorN(), ValidationScopes.LATITUDE);
            MessageHistoryRequestDTO.registerValidator(new LongitudeValidator(), ValidationScopes.LATITUDE);
            MessageHistoryRequestDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
    }
}
