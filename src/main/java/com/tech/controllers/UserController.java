package com.tech.controllers;

import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.Validator;
import com.tech.controllers.superclass.BaseController;
import com.tech.models.dtos.UserDTO;
import com.tech.models.entities.User;
import com.tech.models.entities.UserInfo;
import com.tech.services.interfaces.IUserInfoService;
import com.tech.services.interfaces.IUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    IUserService service;
    @Autowired
    IUserInfoService infoService;

    /**
     * not tested
     * @param username
     * @return 
     */
    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    public HttpEntity<JSONObject> loadUserProfile(@PathVariable String username){
        JSONObject json = new JSONObject();
        if(!Validator.nameValidation(username)) {
            json.put("response",Responses.NOT_AVAILABLE.getData());
            return new ResponseEntity<>(json,HttpStatus.NOT_FOUND);
        }
        if(!service.checkUsername(username)){
            json.put("response",Responses.NOT_AVAILABLE.getData());
            return new ResponseEntity<>(json,HttpStatus.NOT_FOUND);
        }
        
        User user = service.getUserByUsername(username.toLowerCase());
        UserInfo userInfo = infoService.getUserInfoByUserId(user.getId());
        
        json.put("username", user.getUsername());
        json.put("firstname",userInfo.getFirstName());
        json.put("last_name",userInfo.getLastName());
        json.put("profile_photo",userInfo.getProfilePhoto());
        json.put("email",userInfo.getEmail());
        json.put("birthday",userInfo.getBirthday());
        json.put("hometown",userInfo.getHometown());
        json.put("status",userInfo.getStatus());
        json.put("response",Responses.SUCCESS.getData());
        
        return new ResponseEntity<>(json,HttpStatus.OK);
        
    }
    /**
     * not tested
     * @param username
     * @param userDTO
     * @return 
     */
   @RequestMapping(value="/{username}/modify",method = RequestMethod.POST)
    public HttpEntity<String> deactivateUser(@PathVariable String username,@RequestBody UserDTO userDTO){
        if(!Validator.nameValidation(username)) {
            return new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT.getData(),HttpStatus.NOT_ACCEPTABLE);
        }
        if(!service.checkUsername(username)){
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.NOT_FOUND);
        }
        
        Long userId = service.getUserByUsername(username).getId();
        
        User user = new User(userId,userDTO);
        UserInfo userInfo = new UserInfo(userId,userDTO);
                
        service.modifyUser(user);
        infoService.modifyUserInfo(userInfo);
        
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);
    }

}