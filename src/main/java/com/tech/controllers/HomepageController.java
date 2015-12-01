//package com.tech.controllers;
//
//        import org.springframework.security.core.Authentication;
//        import org.springframework.security.core.context.SecurityContextHolder;
//        import org.springframework.stereotype.Controller;
//        import org.springframework.ui.ModelMap;
//        import org.springframework.web.bind.annotation.RequestMapping;
//        import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//public class HomepageController {
//
//    @RequestMapping(value={"/","/home"},method = RequestMethod.GET)
//    public String loadHomepage(ModelMap model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            if (!auth.getName().equals("anonymousUser")){
//                model.addAttribute("user",auth.getName());
//            }
//        }
//        return "home";
//    }
//}


/* DELETE */