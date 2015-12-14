/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.configurations.tools.Host;
import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.controllers.superclass.BaseController;
import com.tech.models.dtos.chatroom.ChatroomBlacklistDTO;
import com.tech.models.dtos.chatroom.ChatroomCheckInsideDTO;
import com.tech.models.dtos.chatroom.ChatroomConnectionMemberDTO;
import com.tech.models.dtos.chatroom.ChatroomCreationDTO;
import com.tech.models.dtos.chatroom.ChatroomDeleteDTO;
import com.tech.models.dtos.chatroom.ChatroomLocationDTO;
import com.tech.models.dtos.chatroom.ChatroomLocationUpdateDTO;
import com.tech.models.dtos.chatroom.ChatroomMemberDTO;
import com.tech.models.dtos.chatroom.ChatroomQuitMemberDTO;
import com.tech.models.dtos.chatroom.ChatroomUpdateDTO;
import com.tech.models.dtos.chatroom.ChatroomWhitelistDTO;
import com.tech.models.entities.chatroom.ChatroomBlacklist;
import com.tech.models.entities.chatroom.ChatroomEntities;
import com.tech.models.entities.chatroom.ChatroomLocation;
import com.tech.models.entities.chatroom.ChatroomMembers;
import com.tech.models.entities.chatroom.ChatroomPrivileges;
import com.tech.models.entities.chatroom.ChatroomWhitelist;
import com.tech.services.interfaces.IChatroomBlacklistService;
import com.tech.services.interfaces.IChatroomEntitiesService;
import com.tech.services.interfaces.ICRLocationService;
import com.tech.services.interfaces.IChatroomMembersService;
import com.tech.services.interfaces.IChatroomPrivilegesService;
import com.tech.services.interfaces.IChatroomWhitelistService;
import com.tech.services.interfaces.IUserService;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.json.simple.JSONObject;
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
public class ChatroomController extends BaseController{
    
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
    
    @Autowired
    ICRLocationService chatroomLocationService;
    
    /** 
     * Validates that the chatroom name doesn't already exist and that the user doesn't have a room on his name already 
     * before creating the entity and all the peripheral data that the new chatroom will need
     * @param newChatroom
     * @return Returns success only after completing the creation of the new chatroom else a NOT_AVAILABLE / FOUND response 
     *         will be returned if the chatroom name was found
     */
    @RequestMapping(value = "/newChatroom",method = RequestMethod.POST)
    public HttpEntity<String> handleNewChatroom(@RequestBody ChatroomCreationDTO newChatroom){
        //Todo call sto validator
        
        if(!userService.checkUsername(newChatroom.getUsername())){ //validates if the user exists or not
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
        }
        
        if(chatroomEntitesService.validateRoomnameExistance(newChatroom.getRoom_name())){ //validates if the the name already exists or not
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.FOUND); //CONFLICT 8a eprepe na exei edw
        }
        
        Long userid = userService.getUserByUsername(newChatroom.getUsername()).getId();
        
        if(userService.getUserById(userid).hasRoom()){ //if he has room he cant make more
            return new ResponseEntity<>(Responses.ALREADY_HAS_A_ROOM.getData(),HttpStatus.FOUND);
        }
        
        userService.updateUserRoom(true,userid);
        ChatroomEntities CE = new ChatroomEntities(chatroomEntitesService.getNextID(),userid, newChatroom);
        ChatroomPrivileges CP = new ChatroomPrivileges(CE.getRoom_id(), newChatroom);
        ChatroomMembers CM = new ChatroomMembers(CE.getRoom_id(), userid);
        ChatroomLocation CL = new ChatroomLocation(CE.getRoom_id(),newChatroom);
        
        chatroomEntitesService.add(CE);
        chatroomMembersService.add(CM);
        chatroomPrivilegesService.add(CP);
        chatroomLocationService.add(CL);
        
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
    public HttpEntity<String> connectToChatroom(@RequestBody ChatroomConnectionMemberDTO newMember){
        //TODO call to validator
        
        /*
            8a itan kali idea na kanw implement ena factory me register / kai lista wste na mporw na balw kai extra 
            login methods? ektos apo blacklist / whitelist. wste na min anaptisw polla if
        */
        
        if(!newMember.getMethod().equals("ADD")){
            return new ResponseEntity<>(Responses.ACCESS_METHOD_NOT_FOUND.getData(),HttpStatus.BAD_REQUEST);
        }
        
        if(userService.checkUsername(newMember.getMember_id())){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
        }
        
        if(chatroomEntitesService.getRoomByName(newMember.getRoom_name())==null){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);           
        }
        
        Long roomId = chatroomEntitesService.getRoomByName(newMember.getRoom_name()).getRoom_id();
        Long userID = userService.getUserByUsername(newMember.getMember_id()).getId();
        
        if(!chatroomLocationService.checkIfStillInside(roomId,newMember.getLng(), newMember.getLat())){
            return new ResponseEntity<>(Responses.OUTSIDE_RANGE.getData(),HttpStatus.GONE);
        }
        
        ChatroomPrivileges CP = chatroomPrivilegesService.findByRoomId(roomId);
        
        if(CP.isRoom_password_protected()){
            if(!CP.getRoom_password().equals(newMember.getPassword())){
                return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);
            }
        }
        
        switch(CP.getRoom_access_method()){
            case "blacklist":
                ChatroomBlacklist CB = chatroomBlacklistService.findByRoomIDAndRoomMember(roomId,userID);
                if (CB != null ){
                    if (CB.getRoom_expiration_time().after(new Date())){// if CB is later than Today
                        return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);
                    }
                    chatroomBlacklistService.delete(CB);
                    chatroomMembersService.add(new ChatroomMembers(roomId,userID));
                    return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);
                } else {
                    chatroomMembersService.add(new ChatroomMembers(roomId,userID));
                    return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);                    
                }
            case "whitelist":
                ChatroomWhitelist CW = chatroomWhitelistService.findByRoomIDAndRoomMember(roomId,userID);
                if (CW != null){
                    chatroomMembersService.add(new ChatroomMembers(roomId,userID));
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
        if(!chatroomEntitesService.validateRoomnameExistance(deleteRoom.getRoom_name())){
            return new ResponseEntity<>(Responses.ROOM_NOT_FOUND.getData(),HttpStatus.NOT_FOUND);            
        }
        
        ChatroomEntities CE = chatroomEntitesService.getRoomByName(deleteRoom.getRoom_name());
        Long creator_id = userService.getUserByUsername(deleteRoom.getCreator_name()).getId();
        
        if(!userService.getUserById(creator_id).hasRoom()) {
            return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);  
        }
        
        if(!Objects.equals(CE.getRoom_creator(),creator_id)) { //<- TODO  Verify
            return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);               
        }
        
        ChatroomPrivileges CP = chatroomPrivilegesService.findByRoomId(CE.getRoom_id());        
        
        if(CP.isRoom_password_protected()){
            if(!CP.getRoom_password().equals(deleteRoom.getRoom_password())){
                return new ResponseEntity<>(Responses.NOT_AUTHORIZED.getData(),HttpStatus.UNAUTHORIZED);
            }
        }
        chatroomEntitesService.delete(CE);
        userService.updateUserRoom(false, creator_id);
        
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
        if (!userService.checkUsername(banDTO.getMember_name())){ //if user doesnt exist
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);            
        }
        if(chatroomEntitesService.validateRoomnameExistance(banDTO.getRoom_name())){
            return new ResponseEntity<>(Responses.ROOM_NOT_FOUND.getData(),HttpStatus.NOT_FOUND);            
        }
        
        Long roomID = chatroomEntitesService.getRoomByName(banDTO.getRoom_name()).getRoom_id();
        Long userID = userService.getUserByUsername(banDTO.getMember_name()).getId();
        ChatroomBlacklist CB = chatroomBlacklistService.findByRoomIDAndRoomMember(roomID,userID);
        
        if(CB != null){
            if (banDTO.getExpiration_date().before(new Date())){// if CB is before than Today
                chatroomBlacklistService.delete(CB);
                return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);
            } else {
                CB.setRoom_expiration_time(banDTO.getExpiration_date());
                chatroomBlacklistService.setNewTime(roomID,userID,banDTO.getExpiration_date());
                return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);
            }
        }
        CB = new ChatroomBlacklist(roomID,userID,banDTO);
        
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
        
        if(!userService.checkUsername(whiteDTO.getMember_name())){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);              
        }
        
        if(chatroomEntitesService.validateRoomnameExistance(whiteDTO.getRoom_name())){
            return new ResponseEntity<>(Responses.ROOM_NOT_FOUND.getData(),HttpStatus.NOT_FOUND);            
        }
        
        Long roomID = chatroomEntitesService.getRoomByName(whiteDTO.getRoom_name()).getRoom_id();
        Long userID = userService.getUserByUsername(whiteDTO.getMember_name()).getId();
        
        ChatroomWhitelist CW ;
        
        switch(whiteDTO.getMode()){
            case "ADD":
                CW = new ChatroomWhitelist(roomID,userID);
                if (chatroomWhitelistService.findByRoomIDAndRoomMember(roomID,userID) != null) {
                    return new ResponseEntity<>(Responses.ALREADY_EXISTS.getData(),HttpStatus.FOUND);                      
                }
                chatroomWhitelistService.add(CW);
                return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);      
            case "DELETE":
                CW = chatroomWhitelistService.findByRoomIDAndRoomMember(roomID,userID);
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
        
        if(!userService.checkUsername(memberDTO.getMember_name())){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
        }
        
        if(chatroomEntitesService.getRoomByName(memberDTO.getRoom_name())==null){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);           
        }
        
        Long roomId = chatroomEntitesService.getRoomByName(memberDTO.getRoom_name()).getRoom_id();
        Long userID = userService.getUserByUsername(memberDTO.getMember_name()).getId();
        ChatroomPrivileges CP = chatroomPrivilegesService.findByRoomId(roomId);        
        
        if(!chatroomMembersService.checkIfMemberExistsInChatroom(userID, roomId)) {
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);            
        }
        
        ChatroomMembers CM = new ChatroomMembers(roomId, userID);
        
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
        chatroomLocationService.setNewMaxRange(updateDTO.getMax_range(), roomID);
        
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK); 
    }
    
    /**
     * A function that is meant to be used by the user that wants to quit a chatroom on his own
     * does not require credentials to complete the request except the username and the room name
     * @param quitMember
     * @return Returns success upon leaving the room. it will return NOT AVAILABLE in any other situation
     */
    @RequestMapping(value = "/quitChatroom", method = RequestMethod.POST)
    public HttpEntity<String> quitChatroom(@RequestBody ChatroomQuitMemberDTO quitMember){
        //TODO call sto Validator
        
        if(!chatroomEntitesService.validateRoomnameExistance(quitMember.getRoom_name())){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);            
        }
        
        if(!userService.checkUsername(quitMember.getUser_name())){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);            
        }
        
        Long roomID = chatroomEntitesService.getRoomByName(quitMember.getRoom_name()).getRoom_id();
        Long userID = userService.getUserByUsername(quitMember.getUser_name()).getId();
        
        if(!chatroomMembersService.checkIfMemberExistsInChatroom(userID,roomID)){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);              
        }
        
        ChatroomMembers CM = new ChatroomMembers(roomID, userID);
        
        chatroomMembersService.delete(CM);
        
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK); 
    }
    
    /**
     * This function can only fail if the geoLocation is invalid. the format of the JSON is 
     *              1 - roomname
     *              2 - roomname
     *              size - 2
     *              error - no error
     * for an execution without any errors
     * @param myLocation
     * @return Returns a JSON of all the available chat rooms according the location that was provided
     *         if the location is invalid it will return an error message else it will be a "no error" response
     *         if 0 rooms are found then the size will be 0
     */
    @RequestMapping(value = "/findAvailableChatrooms", method = RequestMethod.POST)
    public HttpEntity<JSONObject> availableChatrooms(@RequestBody ChatroomLocationDTO myLocation){
        //TODO call sto validator
        
        JSONObject json = new JSONObject();
        
        List<ChatroomLocation> CL = chatroomLocationService.findIfNear(myLocation.getLng(),myLocation.getLat());        
        
        if (CL.size()<=0){
            json.put("size",0);
            json.put("error","no errors");
            return new ResponseEntity<>(json,HttpStatus.OK);
        }
        
        int i = 0;
        for(ChatroomLocation vLookUp:CL){
            i++;
            json.put(i, chatroomEntitesService.findByRoomID(vLookUp.getRoom_id()).getRoom_name());
        }
        
        json.put("size", i);
        json.put("error","no errors");
        
        return new ResponseEntity<>(json,HttpStatus.OK);
    }
    
    /**
     * Updates the current chatroom Location. the location should move accordingly to the chatroom creator
     * @param roomLocation
     * @return Returns success on every completed location update. If the room does not exist a NOT AVAILABLE response 
     *         will be send
     */
    @RequestMapping(value = "/updateChatroomLocation", method = RequestMethod.POST)
    public HttpEntity<String> updateLocation(@RequestBody ChatroomLocationUpdateDTO roomLocation){
        //TODO call ston validator
        if(!chatroomEntitesService.validateRoomnameExistance(roomLocation.getRoom_name())){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);              
        }
        
        Long roomID = chatroomEntitesService.getRoomByName(roomLocation.getRoom_name()).getRoom_id();
        
        chatroomLocationService.setNewLngLat(roomLocation.getLng(), roomLocation.getLat(), roomID);
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK); 
    }
    
    /**
     * Checks if the member is still inside the chatroom range.
     * @param myLocation
     * @return Returns Success if the member is still inside the chatroom range. 
     *         Returns Outside Range if the user is outside the chatroom range. It will also check if the 
     *         user was a member of the chatroom and if he was he will be removed from the chatroom member list
     *         Returns a NOT CONNECTED TO THE ROOM / UNAUTHORIZED error if this function is used from a user inside 
     *         the chatroom range but not currently a member of it!
     */
    @RequestMapping(value = "/checkIfStillInside", method = RequestMethod.POST)
    public HttpEntity<String> checkIfStillInside(@RequestBody ChatroomCheckInsideDTO myLocation){
        //TODO call ston validator
        if(!userService.checkUsername(myLocation.getUser_name())){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);                    
        }
        
        if(!chatroomEntitesService.validateRoomnameExistance(myLocation.getRoom_name())){            
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);        
        }
        
        Long userID = userService.getUserByUsername(myLocation.getUser_name()).getId();
        Long roomID = chatroomEntitesService.getRoomByName(myLocation.getRoom_name()).getRoom_id();
        
        if(!chatroomLocationService.checkIfStillInside(roomID,myLocation.getLng(),myLocation.getLat())){
            if(chatroomMembersService.checkIfMemberExistsInChatroom(userID, roomID)){
                ChatroomMembers CE = new ChatroomMembers(roomID, roomID);
                chatroomMembersService.delete(CE);
            }
            return new ResponseEntity<>(Responses.OUTSIDE_RANGE.getData(),HttpStatus.GONE);
        } else {
            if(!chatroomMembersService.checkIfMemberExistsInChatroom(userID, roomID)){
                return new ResponseEntity<>(Responses.NOT_CONNECTED_TO_THE_ROOM.getData(),HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);             
        }
    }    
}
