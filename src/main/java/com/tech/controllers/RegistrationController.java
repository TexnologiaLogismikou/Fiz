package com.tech.controllers;

import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    IUserService service;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> saveUser(@RequestBody User user) {
        if (!service.checkUsername(user.getUsername())) {
            service.addUser(new User(service.getNextID(),user.getUsername(),user.getPassword()));
            return new ResponseEntity<>("complete", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("already exists",HttpStatus.FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadRegistrationPage() {
        return "register";
    }
}
