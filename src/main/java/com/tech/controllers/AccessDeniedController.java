//package com.tech.controllers;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//@RequestMapping("/Access_Denied")
//public class AccessDeniedController extends BaseController{
//
//    @RequestMapping(method = RequestMethod.GET)
//    public String accessDenied(ModelMap model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            String name = auth.getName();
//            model.addAttribute("user",name);
//        }
//        return "accessDenied";
//    }
//}


/* DELETE */