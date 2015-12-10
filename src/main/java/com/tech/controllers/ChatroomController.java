/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.configurations.tools.Host;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.Validator;
import com.tech.models.dtos.ChatroomCreationDTO;
import com.tech.services.interfaces.IChatroomBlacklistService;
import com.tech.services.interfaces.IChatroomEntitiesService;
import com.tech.services.interfaces.IChatroomMembersService;
import com.tech.services.interfaces.IChatroomPrivilegesService;
import com.tech.services.interfaces.IChatroomWhitelistService;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author KuroiTenshi
 */
@CrossOrigin(origins = Host.apache)
@RestController
@RequestMapping("/chatroom")
public class ChatroomController {
    @Autowired
    IUserService userService;
    
    @Autowired 
    IChatroomEntitiesService chatroomEntitesService;
    
    @Autowired 
    IChatroomBlacklistService chatroomBlacklistService; 
    
    @Autowired 
    IChatroomWhitelistService chatroomWhitelistService; 
    
    @Autowired 
    IChatroomPrivilegesService chatroomPrivilegesService; 
    
    @Autowired 
    IChatroomMembersService chatroomMembersService;
    
    /** 
     * Chatroom = ChatRoom code , Chatroom name , Chatroom Creator, Chatroom member! 
     * @param newChatroom
     * @return 
     */
   @RequestMapping(value = "/newChatroom",method = RequestMethod.POST)
   public HttpEntity<String> handleNewChatroom(@RequestBody ChatroomCreationDTO newChatroom){
       Pair p = Validator.validateDTO(newChatroom);
       if(!p.getBoolean()) {
           return p.getResponse();
       }

       return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);
   }
   
   @RequestMapping(value = "/connectChatroom",method = RequestMethod.POST)
   public HttpEntity<String> connectToChatroom(/*@RequestBody ChatroomDTO chatroom*/){
       return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);
   }
   
   @RequestMapping(value ="/deleteChatroom",method = RequestMethod.POST)
   public HttpEntity<String> deleteChatroom(/*@RequestBody ChatroomDTO chatroom*/) {
       return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);
   }   
}