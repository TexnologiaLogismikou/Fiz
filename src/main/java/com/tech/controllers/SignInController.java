package com.tech.controllers;

import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class SignInController {

    @Autowired
    IUserService service;

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<String> loadUser(@RequestBody User user){
        if (service.validateUser(user.getUsername(),user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Error", null, HttpStatus.NOT_FOUND); //on username failure (to find it) it will return a NOT_FOUND enum      
    }

    @RequestMapping(method = RequestMethod.GET)
    public void loadHtml() {

    }
}
