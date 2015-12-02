package com.tech.controllers;

import com.tech.models.dtos.LoginUserDTO;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LogInAndOutController {

    @Autowired
    IUserService service;

//    @RequestMapping(method = RequestMethod.GET) //TODO use JSON
//    public String loadLoginPage(@RequestParam (value = "error", required=false) String error,
//                                @RequestParam(value = "logout", required = false) String logout,
//                                HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (error != null){
//        }
//        if (logout != null && auth!=null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "login";
//    }
    @RequestMapping(method = RequestMethod.POST)
    public  HttpEntity<String> handleLogin(@RequestBody LoginUserDTO loginUserDTO) {
        if (!service.validateUser(loginUserDTO.getUsername(), loginUserDTO.getPassword())) {
            return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);   
        }
        return new ResponseEntity<>("Login",HttpStatus.ACCEPTED);    
    }
}
