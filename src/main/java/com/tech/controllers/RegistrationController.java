package com.tech.controllers;

import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    IUserService service;

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<String> register(@RequestParam("username") String username,
                               @RequestParam("password") String password) {

        System.out.println(username);
        System.out.println(password);

        return new ResponseEntity<>("success",HttpStatus.OK);

    }
}