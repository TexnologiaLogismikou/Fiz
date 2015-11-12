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
 * handles ALL the requests in the /log-in/rd url 
 * @author KuroiTenshi
 */
@RestController
@RequestMapping("/log-in/rd")
public class LogInController {   
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
     * Handles the POST method on the "/log-in/rd " url and returns an answer 
     * @param username
     * @param password
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST) //TODO controllers need to be more general and will require JSON , Command as parameters -> not fields
    public HttpEntity<String> loadUser(@RequestParam("username") String username,/* requires a parameter "username" and stores the data in the String username*/
            @RequestParam("password") String password) {/* requires a parameter "password" and stores the data in the String password*/
        for(User vLookUp:service.getAllUsers()) { //examines the whole database to find the username
            if (vLookUp.getUsername().equalsIgnoreCase(username)) { //compares all the usernames with the one that it took from the site
                if (vLookUp.getPassword().equalsIgnoreCase(password)) { //if it founds the correct username then it tries to verify the password
                    return new ResponseEntity<>("compete", null, HttpStatus.OK); //on password success it will return an OK enum
                } else  {         //TODO 8a alla3i gt ets exei security prob           
                    return new ResponseEntity<>("Error", null, HttpStatus.UNAUTHORIZED); //on password failure it will return an UNAUTHORIZED enum
                }
            } 
        }
        return new ResponseEntity<>("Error", null, HttpStatus.NOT_FOUND); //on username failure (to find it) it will return a NOT_FOUND enum      
    }
}
