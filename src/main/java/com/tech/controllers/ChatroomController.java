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
import com.tech.models.dtos.chatroom.ChatroomBlacklistDTO;
import com.tech.models.dtos.chatroom.ChatroomCreationDTO;
import com.tech.models.dtos.chatroom.ChatroomDeleteDTO;
import com.tech.models.dtos.chatroom.ChatroomMemberDTO;
import com.tech.models.dtos.chatroom.ChatroomUpdateDTO;
import com.tech.models.dtos.chatroom.ChatroomWhitelistDTO;
import com.tech.models.entities.chatroom.ChatroomBlacklist;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.chatroom.ChatroomMembers;
import com.tech.models.entities.chatroom.ChatroomPrivileges;
import com.tech.models.entities.chatroom.ChatroomWhitelist;
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
     * Validates that the chatroom name doesn't already exist before creating the entity and all the peripheral data that the 
     * new chatroom will need
     * @param newChatroom
     * @return Returns success only after completing the creation of the new chatroom else a NOT_AVAILABLE / FOUND response 
     *         will be returned if the chatroom name was found
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
   
    /**
     * In order to execute the function the method provided must be ADD. After checking the credential requirements of the room
     * that the user is trying to connect (and validating them with the provided if the are required) it will check the access method 
     * that the room supports and it will confirm that the user is able to join or not. If the room uses blacklist it will check if the user
     * is already on the blacklist or if the blacklist ban time has expired. If the user is on the blacklist and the time has not expired it won't 
     * let him login. On the other hand if the time has expired it will delete the record from the blacklist and it will allow the user to join. Last
     * if the user is not on the blacklist he will be allowed to join. If the method is whitelist then only if the user is on the whitelist he will be 
     * able to join the room
     * @param newMember
     * @return Returns success only after completing a login session with the user successfully joining the room. Else he will take a NOT_AUTHORIZED/
     *        UNAUTHORIZED return and he won't be logged in. If the login method that was provided was wrong the response will be 
     *        ACCESS_METHOD_NOT_FOUND/BAD_REQUEST
     */
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
   
    /**
     * DELETE a chatroom. it requires the correct chatroom name, the id of its creator and the 
     *        correct password that the room has at the time.
     * @param deleteRoom
     * @return Returns success only if all the requirements are met and it completes the delete.
     *         else it will return NOT_AUTHORIZED / UNAUTHORIZED if the credentials are bad or 
     *         NOT_FOUND if the data themselves are bad
     */
    @RequestMapping(value ="/deleteChatroom",method = RequestMethod.POST)
    public HttpEntity<String> deleteChatroom(@RequestBody ChatroomDeleteDTO deleteRoom) {
        //TODO call sto validator
        if(chatroomEntitesService.validateRoomnameExistance(deleteRoom.getRoom_name())){
            return new ResponseEntity<>(Responses.ROOM_NOT_FOUND.getData(),HttpStatus.NOT_FOUND);            
        }
        
        ChatroomEntities CE = chatroomEntitesService.getRoomByName(deleteRoom.getRoom_name());
        
        if(!Objects.equals(CE.getRoom_creator(), deleteRoom.getCreator_id())) {
            return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);               
        }
        
        ChatroomPrivileges CP = chatroomPrivilegesService.findByRoomId(CE.getRoom_id());        
        
        if(CP.isRoom_password_protected()){
            if(!CP.getRoom_password().equals(deleteRoom.getRoom_password())){
                return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);
            }
        }
        chatroomEntitesService.delete(CE);
        
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);
    }  
    
    /**
     * ADD,UPDATE or REMOVE a record from the blackList table. Depending on the use. 
     *         ADD if the record provided doesn't exist in the blackList it will be added
     *         UPDATE if the record exists in the blackList and the expiration time is a future day from today the records 
     *                expiration date will be updated to the provided expiration date (previous data will be overridden)
     *         DELETE if the record exists in the blackList and the expiration time has expired the record will be removed
     *                from the table. 
     * @param banDTO
     * @return Returns success upon completing either <i><b>ADD</b></i>,<i><b>UPDATE</b></i> or <i><b>DELETE</b></i>. 
     *         The rest of the responses are <i><b>NOT_FOUND</b></i> depending on wrong data input
     */
    @RequestMapping(value = "/banFromChatroom",method = RequestMethod.POST)
    public HttpEntity<String> handleBans(@RequestBody ChatroomBlacklistDTO banDTO){
        //TODO call sto Validator
        if (userService.getUserById(banDTO.getMember_id()) == null){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);            
        }
        if(chatroomEntitesService.validateRoomnameExistance(banDTO.getRoom_name())){
            return new ResponseEntity<>(Responses.ROOM_NOT_FOUND.getData(),HttpStatus.NOT_FOUND);            
        }
        
        Long roomID = chatroomEntitesService.getRoomByName(banDTO.getRoom_name()).getRoom_id();
        
        ChatroomBlacklist CB = chatroomBlacklistService.findByRoomIDAndRoomMember(roomID,banDTO.getMember_id());
        
        if(CB != null){
            if (banDTO.getExpiration_date().before(new Date())){// if CB is before than Today
                chatroomBlacklistService.delete(CB);
                return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);
            } else {
                CB.setRoom_expiration_time(banDTO.getExpiration_date());
                chatroomBlacklistService.setNewTime(roomID,banDTO.getMember_id(),banDTO.getExpiration_date());
                return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);
            }
        }
        CB = new ChatroomBlacklist(roomID,banDTO);
        
        chatroomBlacklistService.add(CB);
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);        
    }
    
    /**
     * Accepts two methods ADD / DELETE only.
     *         ADD if the whiteList record doesn't exist it will be added to the DB
     *         DELETE if the whiteList record exists it will be deleted.
     * @param whiteDTO
     * @return returns success only after completing its task. 
     *         ADD if the whiteList record exists it will return ALREADY_EXISTS / FOUND 
     *         DELETE if the whiteList record doesn't exist it will return NOT_AVAILABLE / NOT_FOUND
     */
    @RequestMapping(value = "/handleWhitelist",method = RequestMethod.POST)
    public HttpEntity<String> handleWhitelist(@RequestBody ChatroomWhitelistDTO whiteDTO){
        //TODO call sto Validator
        
        if(userService.getUserById(whiteDTO.getMember_id()) == null){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);              
        }
        
        if(chatroomEntitesService.validateRoomnameExistance(whiteDTO.getRoom_name())){
            return new ResponseEntity<>(Responses.ROOM_NOT_FOUND.getData(),HttpStatus.NOT_FOUND);            
        }
        
        Long roomID = chatroomEntitesService.getRoomByName(whiteDTO.getRoom_name()).getRoom_id();
        
        ChatroomWhitelist CW ;
        
        switch(whiteDTO.getMode()){
            case "ADD":
                CW = new ChatroomWhitelist(roomID,whiteDTO);
                if (chatroomWhitelistService.findByRoomIDAndRoomMember(roomID, whiteDTO.getMember_id()) != null) {
                    return new ResponseEntity<>(Responses.ALREADY_EXISTS.getData(),HttpStatus.FOUND);                      
                }
                chatroomWhitelistService.add(CW);
                return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);      
            case "DELETE":
                CW = chatroomWhitelistService.findByRoomIDAndRoomMember(roomID,whiteDTO.getMember_id());
                if(CW != null) {
                    chatroomWhitelistService.delete(CW);
                    return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);     
                } 
                return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);     
            default:
                return new ResponseEntity<>(Responses.ACCESS_METHOD_NOT_FOUND.getData(), HttpStatus.BAD_REQUEST);  
        }       
    }
    
    /**
     * Locates the member in the chatroom . Verifies that it needs no credentials or that the credentials provided are correct
     * and then it deletes him.
     * @param memberDTO
     * @return returns success after the deletion of the user. if the chatroom had credentials to login
     *         and the credentials provided where wrong it will return unauthorized
     */
    @RequestMapping(value = "/removeMember",method = RequestMethod.POST)
    public HttpEntity<String> removeMember(@RequestBody ChatroomMemberDTO memberDTO){
        //TODO call sto Validator
        if(!memberDTO.getMethod().equals("DELETE")){
            return new ResponseEntity<>(Responses.ACCESS_METHOD_NOT_FOUND.getData(),HttpStatus.BAD_REQUEST);
        }
        
        if(userService.getUserById(memberDTO.getMember_id())==null){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
        }
        
        if(chatroomEntitesService.getRoomByName(memberDTO.getRoom_name())==null){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);           
        }
        
        Long roomId = chatroomEntitesService.getRoomByName(memberDTO.getRoom_name()).getRoom_id();
        ChatroomPrivileges CP = chatroomPrivilegesService.findByRoomId(roomId);        
        
        if(!chatroomMembersService.checkIfMemberExistsInChatroom(memberDTO.getMember_id(), roomId)) {
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);            
        }
        
        ChatroomMembers CM = new ChatroomMembers(roomId, memberDTO);
        
        if(CP.isRoom_password_protected()){
            if(!CP.getRoom_password().equals(memberDTO.getPassword())){
                return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);
            }
        }
        
        chatroomMembersService.delete(CM);
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);          
    }
    
    /**
     * Simple mass data updater. without confirming changes it just overrides them
     * @param updateDTO
     * @return returns Success only when managing to update the record
     */
    @RequestMapping(value = "/updateChatroom", method = RequestMethod.POST)
    public HttpEntity<String> updateChatroom(@RequestBody ChatroomUpdateDTO updateDTO){
        //TODO call sto Validator
        if(!chatroomEntitesService.validateRoomnameExistance(updateDTO.getRoom_name())){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
        }
        
        Long roomID = chatroomEntitesService.getRoomByName(updateDTO.getRoom_name()).getRoom_id();
        
        chatroomEntitesService.setChatroomEntities(updateDTO.getNew_room_name(), roomID);
        chatroomPrivilegesService.setChatroomPrivileges(updateDTO.getRoom_privilege(),
                updateDTO.isPasswordProtected(), updateDTO.getPassword(), updateDTO.getAccess_method(),roomID);
        
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK); 
    }
}