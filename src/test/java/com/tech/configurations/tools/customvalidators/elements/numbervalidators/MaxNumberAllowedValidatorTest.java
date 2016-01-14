/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools.customvalidators.elements.numbervalidators;

import com.tech.configurations.tools.JSONToolConverter;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.models.dtos.MessageDTO;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author KuroiTenshi
 */
public class MaxNumberAllowedValidatorTest {
    
    public MaxNumberAllowedValidatorTest() {
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
    public void testNextValidator() throws Exception {
        MessageDTO.cleanValidator();
        JSONObject json = new JSONObject();
        
        MessageDTO.registerValidator(new MaxNumberAllowedValidator(2), ValidationScopes.TTL);
        MessageDTO.registerValidator(new NotNegativeValidator(), ValidationScopes.TTL);
        
        json.put("message", "somemessage");
        json.put("username", "somemessage");
        json.put("chatroom_name", "somemessage");
        json.put("lng", 5);
        json.put("lat", 5);
        json.put("ttl", 1);        
         
        MessageDTO MDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageDTO.class);
        
        Assert.assertTrue("Failure - expected to be false", MDTO.validate().getLeft());
    }
    
    @Test
    public void testLastValidator() throws Exception {
        MessageDTO.cleanValidator();
        JSONObject json = new JSONObject();
        
        MessageDTO.registerValidator(new MaxNumberAllowedValidator(2), ValidationScopes.TTL);
        
        json.put("message", "somemessage");
        json.put("username", "somemessage");
        json.put("chatroom_name", "somemessage");
        json.put("lng", 5);
        json.put("lat", 5);
        json.put("ttl", 1);        
         
        MessageDTO MDTO = JSONToolConverter.mapFromJson(json.toJSONString(),MessageDTO.class);
        
        Assert.assertTrue("Failure - expected to be false", MDTO.validate().getLeft());
    }
}
