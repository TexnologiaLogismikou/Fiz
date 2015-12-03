package com.tech.controllers;

import com.tech.configurations.tools.Host;
import com.tech.controllers.superclass.BaseController;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = Host.apache)
@RestController
@RequestMapping("/generalController")
public class GeneralController extends BaseController{
    @Autowired
    IUserService service;
    
    /**
     * Ending the current session of the program manually
     */
    @RequestMapping(value = "/end-session",method = RequestMethod.GET)
    public void terminateSession() {
        System.out.println("Session terminated by the user");
        System.exit(0);
    }
    
    /**
     * 
     * @param username
     * @return available if username doesn't exists (HttpStatus -> ACCEPTED ) else Not Available (HttpStatus -> FOUND)
     */
    @RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
    public HttpEntity<String> validateUsername (@RequestParam("username") String username) {
        if (!service.checkUsername(username)) {
            return new ResponseEntity<>("available",HttpStatus.OK);            
        } 
        return new ResponseEntity<>("Not available", HttpStatus.FOUND);   
    }
    
}
