/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.Controllers;

import com.tech.Models.User;
import com.tech.Repositories.IUserRepository;
import com.tech.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * handles ALL the requests in the /register/rd url 
 * @author KuroiTenshi
 */
@RestController
@RequestMapping("/register/rd")
public class RegistrationController {
    /**
     * automatically connects the service with the controller
     */
    @Autowired
    IUserService service;
    
    /**
     * automatically connects the repository with the controller
     * TODO repository will be removed from this place.. every transaction with the database
     * will be made with the service. We need more service methods to support those commands
     */
    @Autowired
    IUserRepository repository;

    /**
     * Handles the POST method on the "/register/rd" url and returns an answer 
     * @param username
     * @param password
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST) //TODO controllers need to be more general and will require JSON , Command as parameters -> not fields
    public HttpEntity<String> saveUser(@RequestParam("username") String username, /* requires a parameter "username" and stores the data in the String username*/
                                                @RequestParam("password") String password) { /* requires a parameter "password" and stores the data in the String password*/
        if (service.checkUsername(username)) { //checks from the service if the username exists
            service.addUser(new User(repository.count()+1,username,password)); //if the username doesnt exist it adds it in the database with an incementable number
            return new ResponseEntity<>("complete", null, HttpStatus.OK); //returns to the site an OK enum           
        } else {
            return new ResponseEntity<>("already exists", null,HttpStatus.FOUND); // if the username exists it returns the FOUND enum to inticate its existance
        }
    }
}
