package com.tech.Controllers;

import com.tech.Models.User;
import com.tech.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * handles  the requests in the /user/
 * @author Alodapos
 */
@RestController
@RequestMapping("/userData")
public class UserDataController {

    @Autowired
    IUserService service;

    /**
     * Handles the GET method in /userData/{username}
     * @param username User name to search
     * @return User object
     */
    @RequestMapping(produces = "Application/json",value = "/{username}",method = RequestMethod.GET)
    public User loadUser(@PathVariable String username){
        return service.getUserByUsername(username.toLowerCase());
    }
}