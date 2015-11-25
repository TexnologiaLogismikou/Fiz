package com.tech.controllers;

import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService service;

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    public String loadUserProfile(@PathVariable String username, ModelMap model){
        User user = service.getUserByUsername(username.toLowerCase());
        if (user==null){
            return "404NotFound";
        }else{
            model.addAttribute("username",user.getUsername());
            model.addAttribute("id",user.getId());
            return "userProfile";
        }
    }
}