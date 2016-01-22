package com.tech.controllers;

import com.tech.configurations.tools.Host;
import com.tech.controllers.superclass.BaseController;
import com.tech.models.dtos.user.LoginUserDTO;
import com.tech.models.entities.user.User;
import com.tech.models.entities.user.UserInfo;
import com.tech.services.interfaces.IUserInfoService;
import com.tech.services.interfaces.IUserRoleService;
import com.tech.services.interfaces.IUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseController{
    @Autowired
    IUserService service;

    @Autowired
    IUserInfoService infoService;

    @Autowired
    IUserRoleService roleService;

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<JSONObject> login(@RequestBody LoginUserDTO userDTO) {
        JSONObject user = new JSONObject();
        if(service.validateUser(userDTO.getUsername(), userDTO.getPassword())) {
            User userIdentity = service.getUserByUsername(userDTO.getUsername());
            UserInfo userInfo = infoService.getUserInfoByUserId(userIdentity.getId());

            user.put("username", userIdentity.getUsername());
            user.put("fname", userInfo.getFirstName());
            user.put("lname", userInfo.getLastName());
            user.put("birthday", userInfo.getBirthday());
            user.put("email", userInfo.getEmail());
            user.put("photo", userInfo.getProfilePhoto());
            user.put("town", userInfo.getHometown());
            user.put("role", roleService.getRoleByUserID(userIdentity.getId()));

            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        user.put("error", "unauthorized");

        return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
    }
}
