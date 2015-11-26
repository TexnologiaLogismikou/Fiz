/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author KuroiTenshi
 */
@Controller
public class TemporaryController {
    
    @RequestMapping(value = "/styles/{Something}",method = RequestMethod.GET,produces = "text/css")
    public void handle(HttpServletResponse response,@PathVariable("Something") String str){
        throw new UnsupportedOperationException("asdas");
    }
    
}
