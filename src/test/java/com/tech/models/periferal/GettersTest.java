/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.periferal;

import com.tech.Application;
import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.Host;
import com.tech.configurations.tools.NameCoder;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyValidator;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.models.entities.ImagesMod;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author KuroiTenshi
 */
public class GettersTest {
    
    @BeforeClass
    public static void setUpClass(){
        
    }
    
    @AfterClass
    public static void tearDownClass(){
        
    }
    
    @Before
    public void setUp(){
        
    }
    
    @After
    public void tearDown(){
        
    }
    
    @Test
    public void testScopeString(){
        ValidationScopes VS = ValidationScopes.STRING;
        
        Assert.assertEquals("Failure - expected to be the same","string",VS.getData());
    }
    
    @Test
    public void testNameCoderSmallID(){
        Date dt = new Date();
        int i = dt.hashCode();
        
        Long l = NameCoder.nameConverter(12L,i);
        
        i = Math.abs(i);
        
        String str = String.valueOf(l);
        
        String preffix = str.substring(0,3);
        String suffix = str.substring(str.length()-1);
        String data = str.substring(3,str.length()-1);
        
        Assert.assertTrue("Failure - expected to be equals",preffix.equals("101"));
        Assert.assertTrue("Failure - expected to be equals",suffix.equals("2"));
        Assert.assertTrue("Failure - expected to be equals",data.equals(String.valueOf(i)));
    }
    
    @Test
    public void testNameCoderBigID(){
        Date dt = new Date();
        int i = dt.hashCode();
        try {
            Long l = NameCoder.nameConverter(212324569L,i);    
            Assert.fail("Wasnt expected to reach this line");
        } catch (Exception ex ){
            Assert.assertTrue("Failure - expected exception message to be 'number was too big'", 
                    ex.getMessage().equalsIgnoreCase("number was too big"));
        }
    }
    
    @Test
    public void testInvalidImageName(){
        ImagesMod IM = new ImagesMod(212324569L);
        
    }
    
    @Test
    public void testInitializeClass(){
        NameCoder NC = new NameCoder();
        InitializeValidators IV = new InitializeValidators();
        Host H = new Host();
    }
    
    @Test
    public void testForEmptyValidator() throws Exception{
        EmptyValidator EV = new EmptyValidator();
        EV.setNext(null);
        EV.replaceNext(null);
        EV.getNext();
        EV.getName();
    }
    
    @Test
    public void testForPair(){
        Pair<Integer,Integer> P = Pair.of(1, 1);
        Pair<Integer,Integer> P2 = Pair.of(1, 1);
        P.hashCode();
        Assert.assertTrue(P.equals(P2));
        Assert.assertFalse(P.equals("hi"));
        
    }
}
