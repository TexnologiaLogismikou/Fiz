package com.tech.controllers;

import com.tech.controllers.superclass.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/end-session")
public class GeneralController extends BaseController{
    /**
     * Ending the current session of the program manually
     */
    @RequestMapping(method = RequestMethod.GET)
    public void terminateSession() {
        System.out.println("Session terminated by the user");
        System.exit(0);
    }
}
