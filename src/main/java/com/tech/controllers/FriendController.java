/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.configurations.tools.Host;
import com.tech.configurations.tools.Responses;
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
       Friend friend = new Friend(userService.getUserByUsername(friendDTO.getUsername()).getId(),
               userService.getUserByUsername(friendDTO.getFriendname()).getId());

       friendService.addFriend(friend);

       return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);
    }

   /**
    * checks if friendship already exists
    * @param friendDTO
    * @return
    */
   @RequestMapping(value = "/checkfriend",method = RequestMethod.POST)
    public HttpEntity<String> checkFriend(@RequestBody FriendDTO friendDTO)
    {
       Friend friend = new Friend(userService.getUserByUsername(friendDTO.getUsername()).getId(),
               userService.getUserByUsername(friendDTO.getFriendname()).getId());

       if(friendService.checkFriendIfExists(friend.getUserid(),friend.getFriendid()))
       {
          return new ResponseEntity<>(Responses.FRIEND_ALREADY_EXISTS.getData(),HttpStatus.FOUND);
       }
         return new ResponseEntity<>(Responses.AVAILABLE.getData(),HttpStatus.OK);
    }

   @RequestMapping(value = "/deletefriend",method = RequestMethod.POST)
   public HttpEntity<String> deleteFriend(@RequestBody FriendDTO friendDTO)
   {
       Friend friend = new Friend(userService.getUserByUsername(friendDTO.getUsername()).getId(),
               userService.getUserByUsername(friendDTO.getFriendname()).getId());
       
       friendService.deleteFriend(friend);
       return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);

   }   
}
