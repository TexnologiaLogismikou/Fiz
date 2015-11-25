package com.tech.controllers;

import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LogInAndOutController {

    @Autowired
    IUserService service;

    @RequestMapping(method = RequestMethod.GET)
    public String loadLoginPage(@RequestParam (value = "error", required=false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (error != null){
            model.addAttribute("error"," ");
        }
        if (logout != null && auth!=null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
            model.addAttribute("logout"," ");
        }
        return "login";
    }
}
