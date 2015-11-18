package com.tech.controllers;

import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
        RestTemplate restTemplate = new RestTemplate();
        String url="http://localhost:8080/userData/"+username;
        User user = restTemplate.getForObject(url, User.class);
        return new ModelAndView("/WEB-INF/jsp/userProfile.jsp", "user", user); //some bug and when using non-static path it bugs, so lets use static
    }
}