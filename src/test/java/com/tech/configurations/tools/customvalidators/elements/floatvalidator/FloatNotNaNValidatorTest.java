/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.floatvalidator;

import com.tech.configurations.tools.JSONToolConverter;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.MaxNumberAllowedValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IFloatValidator;
import com.tech.models.dtos.MessageDTO;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class FloatNotNaNValidatorTest {
    
    public FloatNotNaNValidatorTest() {
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
    public void testValidateFailNaN() {
        IFloatValidator floatValidator = new FloatNotNaNValidator();
        Float a = Float.NaN;
        Pair<Boolean ,ResponseEntity> answer = floatValidator.validate(a);
        
        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
        Assert.assertEquals("Failure - expected '"+Responses.BAD_COORDINATES.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.BAD_COORDINATES.getData(),HttpStatus.UNPROCESSABLE_ENTITY));
    }  
    
    @Test
    public void testValidateSuccesNaN() {
        IFloatValidator floatValidator = new FloatNotNaNValidator();
        Pair<Boolean ,ResponseEntity> answer = floatValidator.validate(3f);
        
        Assert.assertEquals("Failure - expected True but the answer was FALSE",answer.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+Responses.SUCCESS.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK));
    }   
    
    @Test
    public void testLastValidator() throws Exception {
        MessageDTO.cleanValidator();
        JSONObject json = new JSONObject();
        
        MessageDTO.registerValidator(new FloatNotNaNValidator(), ValidationScopes.LATITUDE);
        MessageDTO.registerValidator(new LatitudeValidator(), ValidationScopes.LATITUDE);
        
        json.put("message", "somemessage");
        json.put("username", "somemessage");
        json.put("chatroom_name", "somemessage");
        json.put("lng", 5);
        json.put("lat", 522);
        json.put("ttl", 4);        
         
        MessageDTO MDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageDTO.class);
        
        Assert.assertFalse("Failure - expected to be false", MDTO.validate().getLeft());
    }
}
