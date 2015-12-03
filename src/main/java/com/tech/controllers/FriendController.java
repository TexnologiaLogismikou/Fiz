/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.controllers.superclass.BaseController;
import com.tech.models.entities.Friend;
import com.tech.services.interfaces.IFriendService;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.HtmlEscapeTag;

/**
 *
 * @author milena 
 */
@Controller
@RequestMapping("/friendservice")
public class FriendController extends BaseController
{
   @Autowired 
   IFriendService friendService;
   
   @Autowired 
   IUserService userService;

   /**
    * 
    * @param username
    * @param friendname
    * @return 
    */
   @RequestMapping(value = "/addfriend",method = RequestMethod.POST)
   public HttpEntity<String> addFriend(@RequestParam("username") String username, //TODO use JSON
           @RequestParam("friendname") String friendname)
   {
       if(!userService.checkUsername(username))
       {
           return new ResponseEntity<>("User does not exists", HttpStatus.NOT_FOUND);    
       } 
       if(!userService.checkUsername(friendname))
       {
           return new ResponseEntity<>("Friend does not exists", HttpStatus.NOT_FOUND);      
       }
       Friend f = new Friend(userService.getUserByUsername(username).getId(),
               userService.getUserByUsername(friendname).getId());

       if(friendService.checkFriendIfExists(f))
       {
          return new ResponseEntity<>("Friend already exists",HttpStatus.FOUND);
       }
 
       friendService.addFriend(f);
       
       return new ResponseEntity<>("complete", HttpStatus.OK);       
    }   
   
   
   @RequestMapping(value = "/deletefriend",method = RequestMethod.POST) //arxa
   public HttpEntity<String> deleteFriend(@RequestParam("username") String username, @RequestParam("friendname") String friendname)
   {
       if(!userService.checkUsername(username))
       {
           return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);    
       }
       else if (!userService.checkUsername(friendname))
       {
           return new ResponseEntity<>("Friend does not exist", HttpStatus.NOT_FOUND);      
       }
       else
       {
           Friend f = new Friend(userService.getUserByUsername(username).getId(), userService.getUserByUsername(friendname).getId());
           if (!friendService.checkFriendIfExists(f))
           {
                return new ResponseEntity<>("This user is not in your friend list anyway", HttpStatus.FOUND);
           }
           else
           {
               friendService.deleteFriend(f);
               return new ResponseEntity<>("Friend successfully deleted", HttpStatus.OK);
           }
       }  
   }   
}
