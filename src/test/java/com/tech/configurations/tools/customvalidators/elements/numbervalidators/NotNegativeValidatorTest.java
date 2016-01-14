/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.numbervalidators;

import com.tech.configurations.tools.JSONToolConverter;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.interfaces.INumberValidator;
import com.tech.models.dtos.MessageDTO;
import junit.framework.Assert;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class NotNegativeValidatorTest {
    
    public NotNegativeValidatorTest() {
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
    public void testValidateSuccess() {
        INumberValidator numberValidator = new NotEmptyValidatorN();
        
        Long i = 100L;
        
        Pair<Boolean,ResponseEntity> answer = numberValidator.validate(i);
        
        Assert.assertEquals("Failure - expected True but the answer was False",answer.getLeft(), Boolean.TRUE);
        Assert.assertEquals("Failure - expected '"+Responses.SUCCESS.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK));
    }
    
    @Test
    public void testValidateFail() {
        INumberValidator numberValidator = new NotNegativeValidator();
        
        Long i = -100L;
        
        Pair<Boolean,ResponseEntity> answer = numberValidator.validate(i);
        
        Assert.assertEquals("Failure - expected False but the answer was True",answer.getLeft(), Boolean.FALSE);
        Assert.assertEquals("Failure - expected '"+Responses.ID_INAPPROPRIATE_FORMAT.getData()+"' but the answer was '"+answer.getRight().getBody()+"'",
                answer.getRight(), new ResponseEntity<>(Responses.ID_INAPPROPRIATE_FORMAT.getData(),HttpStatus.UNPROCESSABLE_ENTITY));
    }
    
    @Test
    public void testLastValidator() throws Exception {
        MessageDTO.cleanValidator();
        JSONObject json = new JSONObject();
        
        MessageDTO.registerValidator(new NotNegativeValidator(), ValidationScopes.TTL);
        
        json.put("message", "somemessage");
        json.put("username", "somemessage");
        json.put("chatroom_name", "somemessage");
        json.put("lng", 5);
        json.put("lat", 5);
        json.put("ttl", 4);        
         
        MessageDTO MDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageDTO.class);
        
        Assert.assertTrue("Failure - expected to be false", MDTO.validate().getLeft());
    }
}
