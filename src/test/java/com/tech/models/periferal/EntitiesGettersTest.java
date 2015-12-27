/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.periferal;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.models.dtos.user.UserDTO;
import com.tech.models.entities.ImagesMod;
import com.tech.models.entities.chatroom.ChatroomBlacklist;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.chatroom.ChatroomLocation;
import com.tech.models.entities.chatroom.ChatroomMembers;
import com.tech.models.entities.chatroom.ChatroomPrivileges;
import com.tech.models.entities.chatroom.ChatroomWhitelist;
import com.tech.models.entities.user.User;
import com.tech.models.entities.user.UserInfo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import junit.framework.Assert;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author KuroiTenshi
 */
public class EntitiesGettersTest {
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        
    }
    
    @BeforeClass
    public static void setUpClass(){
        
    }
    
    @AfterClass
    public static void tearDownClass(){
        
    }
    
    @Test
    public void testAllChatroomEntitiesGetters(){
        ChatroomEntities CE = new ChatroomEntities(1L,1L,"hello");
        Date date = CE.getRoom_creation_date();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        
        Assert.assertEquals("Failure expected creation date to be today", 
                dateFormat.format(new Date()),dateFormat.format(date));
    }
    
    @Test
    public void testAllChatroomLocationGetters(){
        ChatroomLocation CL = new ChatroomLocation(1L, 2, 2, 100);
        float lat = 2;
        float lng = 2;
        int range = 100;
        
        Assert.assertEquals("Failure - expected to be the same ",lat, CL.getRoom_lat());
        Assert.assertEquals("Failure - expected to be the same ",lng, CL.getRoom_lng());
        Assert.assertEquals("Failure - expected to be the same ",range, CL.getRoom_max_range());
    }
    
    @Test
    public void testAllChatroomBlacklistGetters(){
        ChatroomBlacklist CB = new ChatroomBlacklist(1L,1L,java.sql.Date.valueOf("2100-10-10"));
        
        Date date = CB.getRoom_ban_time();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        
        Assert.assertEquals("Failure expected creation date to be today", 
                dateFormat.format(new Date()),dateFormat.format(date));
    }
    
    @Test
    public void testAllChatroomPrivilegesGetters(){
        ChatroomPrivileges CP = new ChatroomPrivileges(1L,"PUBLIC",true,"haha","blacklist");
        
        Assert.assertEquals("Failure - expected to be the same","PUBLIC", CP.getRoom_privileges());
    }
    
    @Test
    public void testAllImagesModGetters(){
        ImagesMod IM = new ImagesMod(1L);
        
        Date date = IM.getTimestamp();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        
        Assert.assertEquals("Failure expected creation date to be today", 
                dateFormat.format(new Date()),dateFormat.format(date));
    }
    
    @Test
    public void testAllChatroomMembersGetters(){
        ChatroomMembers CM = new ChatroomMembers(1L, 2L);        
        
        Date date = CM.getRoom_joined_date();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        
        Assert.assertEquals("Failure expected creation date to be today", 
                dateFormat.format(new Date()),dateFormat.format(date));
    }
    
    @Test
    public void testAllChatroomWhitelistGetters(){
        ChatroomWhitelist CW = new ChatroomWhitelist(1L, 2L);     
        
        Date date = CW.getRoom_invitation_time();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        
        Assert.assertEquals("Failure expected creation date to be today", 
                dateFormat.format(new Date()),dateFormat.format(date));
    }
    
    @Test
    public void testAllUserInfoGetters() throws JsonMappingException, IOException{
        JSONObject json = new JSONObject();
        
        json.put("username", "izanagi");
        json.put("password", "nopass");
        json.put("enabled", true);
        json.put("hasRoom", false);
        json.put("email", "something@email.com");
        json.put("profile_photo", "123");
        json.put("status", "whatstatus");
        json.put("last_name", "somethingelse");
        json.put("birthday", "1994-12-05");
        json.put("hometown", "thess");
        json.put("firstname", "mixalis");
        
        UserDTO UDTO = mapFromJson(json.toJSONString(),UserDTO.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        UserInfo UI = new UserInfo(1L,UDTO);
        User U = new User(1L,UDTO);
        
        Assert.assertEquals("Failure - expected to be the same",json.get("birthday"),dateFormat.format(UI.getBirthday()));
        Assert.assertEquals("Failure - expected to be the same",json.get("profile_photo"),UI.getProfilePhoto());
        Assert.assertEquals("Failure - expected to be the same",json.get("email"),UI.getEmail());
        Assert.assertEquals("Failure - expected to be the same",json.get("status"),UI.getStatus());
        Assert.assertEquals("Failure - expected to be the same",json.get("last_name"),UI.getLastName());
        Assert.assertEquals("Failure - expected to be the same",json.get("hometown"),UI.getHometown());
        Assert.assertEquals("Failure - expected to be the same",json.get("firstname"),UI.getFirstName());
        
        Assert.assertEquals("Failure - expected to be the same",json.get("username"),U.getUsername());
        Assert.assertEquals("Failure - expected to be the same",json.get("password"),U.getPassword());
        Assert.assertFalse("Failure - expected to be false",U.hasRoom());
        Assert.assertTrue("Failure - expected to be true",U.isEnabled());
        
        Assert.assertEquals("Failure - expected to be the same", U.getId(),UI.getUserid());
    }
    
    protected <T> T mapFromJson(String json,Class<T> clazz) throws JsonParseException,JsonMappingException,IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }
}
