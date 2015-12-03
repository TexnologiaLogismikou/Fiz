package com.tech.controllers;
/*
import com.tech.models.dtos.UserDTO;
import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    IUserService service;*/
/*
    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    public String loadUserProfile(@PathVariable String username, ModelMap model){
        User user = service.getUserByUsername(username.toLowerCase());
        if (user==null){
            return "404NotFound";
        }else{
            model.addAttribute("username",user.getUsername());
            return "userProfile";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadMyProfile(ModelMap model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username",username);
        return "userProfile";
    }
    */
   /* @RequestMapping(value="/{username}/modify",method = RequestMethod.POST)
    public String deactivateUser(@PathVariable String username,@RequestParam("user") UserDTO userDTO){
        
        if(!service.checkUsername(username)){
            return "User not found";
        }
        Long userId = service.getUserByUsername(username).getId();
        
        User user = new User(userId,userDTO);
                
        service.modifyUser(user);
        
        return "User modified";
    }

}*/