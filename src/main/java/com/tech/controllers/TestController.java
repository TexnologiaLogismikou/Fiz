/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.models.dtos.auth.LoginDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author KuroiTenshi
 */
@RestController
public class TestController {
    
    @RequestMapping("/secured/test")
    @ResponseBody
    public HttpEntity aController(LoginDTO LDTO){
        System.out.println("Username : " + LDTO.getUsername());
        System.out.println("Password : " + LDTO.getPassword());
        return new ResponseEntity(HttpStatus.OK);
    }
}
