/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.configurations.tools.Host;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.Validator;
import com.tech.models.dtos.FriendDTO;
import com.tech.controllers.superclass.BaseController;
import com.tech.models.entities.Friend;
import com.tech.services.interfaces.IFriendService;
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
 * @author milena 
 */
@CrossOrigin(origins = Host.apache)
@RestController
@RequestMapping("/friendservice")
public class FriendController extends BaseController
{
   @Autowired 
   IFriendService friendService;
   
   @Autowired 
   IUserService userService;

   /**
    * 
    * @param friendDTO
    * @return 
    */
   @RequestMapping(value = "/addfriend",method = RequestMethod.POST)
   public HttpEntity<String> addFriend(@RequestBody FriendDTO friendDTO)
   {
      
        if (!Validator.nameValidation(friendDTO.getFriendname()))
        {
            return new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT.getData(),HttpStatus.NOT_ACCEPTABLE);
        }
         
       if(!userService.checkUsername(friendDTO.getFriendname()))
       {
          return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
       }
       
       Friend friend = new Friend(userService.getUserByUsername(friendDTO.getUsername()).getId(),
               userService.getUserByUsername(friendDTO.getFriendname()).getId());
    
       if(friendService.checkFriendIfExists(friend.getUserid(),friend.getFriendid()))
       {
          return new ResponseEntity<>(Responses.FRIEND_ALREADY_EXIST.getData(),HttpStatus.FOUND);
       }    
       
       friendService.addFriend(friend);
       
       return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);
    }

 
   @RequestMapping(value = "/deletefriend",method = RequestMethod.POST)
   public HttpEntity<String> deleteFriend(@RequestBody FriendDTO friendDTO)
   {
       if(!Validator.nameValidation(friendDTO.getFriendname())){
           return new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT.getData(),HttpStatus.NOT_ACCEPTABLE);
       }
       
       if(!userService.checkUsername(friendDTO.getFriendname())){
           return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
       }
       
       Friend friend = new Friend(userService.getUserByUsername(friendDTO.getUsername()).getId(),
               userService.getUserByUsername(friendDTO.getFriendname()).getId());
       
       if(!friendService.checkFriendIfExists(friend.getUserid(), friend.getFriendid())){
           return new ResponseEntity<>(Responses.FRIEND_DOES_NOT_EXIST.getData(),HttpStatus.NOT_FOUND);
       }

        friendService.deleteFriend(friend);
        return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);

   }   
}
