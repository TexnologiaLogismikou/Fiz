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
import com.tech.models.dtos.ChatroomDeleteDTO;
import com.tech.models.dtos.ChatroomMemberDTO;
import com.tech.models.entities.ChatroomBlacklist;
import com.tech.models.entities.ChatroomEntities;
import com.tech.models.entities.ChatroomMembers;
import com.tech.models.entities.ChatroomPrivileges;
import com.tech.models.entities.ChatroomWhitelist;
import com.tech.services.interfaces.IChatroomBlacklistService;
import com.tech.services.interfaces.IChatroomEntitiesService;
import com.tech.services.interfaces.IChatroomMembersService;
import com.tech.services.interfaces.IChatroomPrivilegesService;
import com.tech.services.interfaces.IChatroomWhitelistService;
import com.tech.services.interfaces.IUserService;
import java.util.Date;
import java.util.Objects;
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
        
        if(userService.getUserById(newChatroom.getUserid())==null){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
        }
        
        if(chatroomEntitesService.validateRoomnameExistance(newChatroom.getRoom_name())){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.FOUND);
        }
        
        ChatroomEntities CE = new ChatroomEntities(chatroomEntitesService.getNextID(), newChatroom);
        ChatroomPrivileges CP = new ChatroomPrivileges(CE.getRoom_id(), newChatroom);
        ChatroomMembers CM = new ChatroomMembers(CE.getRoom_id(), newChatroom);
        
        chatroomEntitesService.add(CE);
        chatroomMembersService.add(CM);
        chatroomPrivilegesService.add(CP);
        
        return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);
    }
   
    @RequestMapping(value = "/connectChatroom",method = RequestMethod.POST)
    public HttpEntity<String> connectToChatroom(@RequestBody ChatroomMemberDTO newMember){
        //TODO call to validator
        
        /*
            8a itan kali idea na kanw implement ena factory me register / kai lista wste na mporw na balw kai extra 
            login methods? ektos apo blacklist / whitelist. wste na min anaptisw polla if
        */
        
        if(!newMember.getMethod().equals("ADD")){
            return new ResponseEntity<>(Responses.ACCESS_METHOD_NOT_FOUND.getData(),HttpStatus.BAD_REQUEST);
        }
        
        if(userService.getUserById(newMember.getMember_id())==null){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
        }
        
        if(chatroomEntitesService.getRoomByName(newMember.getRoom_name())==null){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);           
        }
        
        Long roomId = chatroomEntitesService.getRoomByName(newMember.getRoom_name()).getRoom_id();
        ChatroomPrivileges CP = chatroomPrivilegesService.findByRoomId(roomId);
        
        if(CP.isRoom_password_protected()){
            if(!CP.getRoom_password().equals(newMember.getPassword())){
                return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);
            }
        }
        
        switch(CP.getRoom_access_method()){
            case "blacklist":
                ChatroomBlacklist CB = chatroomBlacklistService.findByRoomIDAndRoomMember(roomId,newMember.getMember_id());
                if (CB != null ){
                    if (CB.getRoom_expiration_time().after(new Date())){// if CB is later than Today
                        return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);
                    }
                    chatroomBlacklistService.delete(CB);
                    chatroomMembersService.add(new ChatroomMembers(roomId,newMember));
                    return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);
                } else {
                    chatroomMembersService.add(new ChatroomMembers(roomId,newMember));
                    return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);                    
                }
            case "whitelist":
                ChatroomWhitelist CW = chatroomWhitelistService.findByRoomIDAndRoomMember(roomId,newMember.getMember_id());
                if (CW != null){
                    chatroomMembersService.add(new ChatroomMembers(roomId,newMember));
                    return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);            
                } else {
                    return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(), HttpStatus.UNAUTHORIZED);                       
                }
            default :
                return new ResponseEntity<>(Responses.ACCESS_METHOD_NOT_FOUND.getData(), HttpStatus.BAD_REQUEST);     
        }
    }
   
    @RequestMapping(value ="/deleteChatroom",method = RequestMethod.POST)
    public HttpEntity<String> deleteChatroom(@RequestBody ChatroomDeleteDTO deleteRoom) {
        //TODO call sto validator
        if(chatroomEntitesService.validateRoomnameExistance(deleteRoom.getRoom_name())){
            return new ResponseEntity<>(Responses.ROOM_NOT_FOUND.getData(),HttpStatus.NOT_FOUND);            
        }
        
        ChatroomEntities CE = chatroomEntitesService.getRoomByName(deleteRoom.getRoom_name());
        
        if(!Objects.equals(CE.getRoom_creator(), deleteRoom.getCreator_id())) {
            return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.NOT_ACCEPTABLE);               
        }
        
        chatroomEntitesService.delete(CE);
        
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);
    }     
}