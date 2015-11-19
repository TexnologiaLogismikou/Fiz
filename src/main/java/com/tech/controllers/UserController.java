package com.tech.controllers;

import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * handles  the requests in the /user/
 * @author Alodapos
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * automatically connects the service with the controller
     */
    @Autowired
    IUserService service;

    /**
     * Handles the GET method in /user/{username}
     * @param username
     * @return ModelAndView object to the view resolver
     */
    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    public ModelAndView loadUserProfile(@PathVariable String username){
        User user = service.getUserByUsername(username.toLowerCase());
        return new ModelAndView("/WEB-INF/views/userProfile.jsp","user", user);
    }
}