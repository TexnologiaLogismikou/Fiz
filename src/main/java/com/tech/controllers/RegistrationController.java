package com.tech.controllers;

import com.tech.models.dtos.RegisteredUserDTO;
import com.tech.models.entities.User;
import com.tech.models.entities.UserInfo;
import com.tech.services.interfaces.IUserInfoService;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@CrossOrigin(origins = "http://83.212.105.54")
@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    IUserService service;
    
    @Autowired 
    IUserInfoService infoService;
    
    /**
     * 
     * @param userDTO
     * @return because it expects the validation on front end the data posted on this function will
     *         be registered without further validation. Returns only "Success" , HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<String> register(@RequestBody RegisteredUserDTO userDTO) {
        User user = new User(service.getNextID(),userDTO);
        UserInfo userInfo = new UserInfo(user.getId(),userDTO);
        
        service.addUser(user);
        infoService.addUserInfo(userInfo);
        
        return new ResponseEntity<>("success",HttpStatus.OK);

    }
    
    /**
     * 
     * @param username
     * @return available if username doesn't exists (HttpStatus -> ACCEPTED ) else Not Available (HttpStatus -> FOUND)
     */
    @RequestMapping(value = "/testUsername", method = RequestMethod.POST)
    public HttpEntity<String> validateUsername (@RequestParam("username") String username) {
        if (!service.checkUsername(username)) {
            return new ResponseEntity<>("available",HttpStatus.ACCEPTED);            
        } 
        return new ResponseEntity<>("Not available", HttpStatus.FOUND);   
    }
}