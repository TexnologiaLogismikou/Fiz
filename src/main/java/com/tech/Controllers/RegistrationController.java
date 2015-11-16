/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.Controllers;

import com.tech.Models.User;
import com.tech.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * handles ALL the requests in the /register/rd url 
 * @author KuroiTenshi
 */
@RestController
@RequestMapping("/register")
public class RegistrationController {
    /**
     * automatically connects the service with the controller
     */
    @Autowired
    IUserService service;

    /**
     * Handles the POST method on the "/register" url and returns an answer 
     * @param user user object to register
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<String> saveUser(@RequestBody User user) {
        if (!service.checkUsername(user.getUsername())) {
            service.addUser(new User(service.getNextID(),user.getUsername(),user.getPassword())); //if the username doesn't exist it adds it in the database with an incementable number
            return new ResponseEntity<>("complete", null, HttpStatus.OK); //returns to the site an OK enum           
        } else {
            return new ResponseEntity<>("already exists", null,HttpStatus.FOUND); // if the username exists it returns the FOUND enum to indicate its existence
        }
    }

    /**
     * Create the register page with HTML
     * @return the html code via HttpEntity
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<String> loadHtml() {
        String HTML;
        HTML = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <script type=\"text/javascript\" src=\"scripts/RegScript.js\"> </script>\n" +
                "        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>\n" +
                "        <title>Registration</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div id=\"body-wrapper\">\n" +
                "            <form id=\"form\" name=\"form\" method=\"post\" action=\"register.html\">\n" +
                "                <table id=\"table\">\n" +
                "                    <tr>\n" +
                "                        <td>Username: </td>\n" +
                "                        <td><input type=\"text\" name=\"username\" id=\"username\" required/></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>Password: </td>\n" +
                "                        <td><input type=\"password\" name=\"password\" id=\"password\" required/></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td></td>\n" +
                "                        <td><button type=\"button\" onclick=\"initializeRegister()\">Submit</button></td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";

        return new ResponseEntity<>(HTML, null, HttpStatus.OK);
    }
}
