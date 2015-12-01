package com.tech.controllers;

import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/*
@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    IUserService service;

    @RequestMapping(method = RequestMethod.POST) // TODO use JSON
    public String saveUser(@RequestParam("username") String username, @RequestParam("password") String password, ModelMap model) {
        if (!service.checkUsername(username)) {
            service.addUser(new User(service.getNextID(), username, password, true));
            //TODO add user roles ref
            model.addAttribute("response", "OK");
        } else {
            model.addAttribute("response", "CONFLICT");
        }
        return "register";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadRegistrationPage() {
        return "register";
    }
}*/