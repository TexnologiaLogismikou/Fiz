package com.tech.controllers;

import com.tech.controllers.superclass.BaseController;
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

@CrossOrigin(origins = "http://83.212.105.54")
@RestController
@RequestMapping("/register")
public class RegistrationController extends BaseController{

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
    
    
}
