package com.tech.controllers;

import com.tech.models.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomepageController {

    @RequestMapping(value="/",method = RequestMethod.GET)
    public String loadHomepage(){
        return "home";
    }

    @RequestMapping(value="/home",method = RequestMethod.GET)
    public String loadHome(){
        return "home";
    }
}
