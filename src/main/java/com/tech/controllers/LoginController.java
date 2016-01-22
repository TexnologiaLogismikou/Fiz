package com.tech.controllers;

import com.tech.configurations.tools.Host;
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
public class LoginController {
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

//        Pair<Boolean,ResponseEntity> response = userDTO.validate();
//        if(!response.getLeft()){
//            System.out.println(userDTO.getLast_name());
//            return response.getRight();
//        }
//
//        if(service.checkUsername(userDTO.getUsername())) {
//            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(), HttpStatus.FOUND);
//        }
//
//        if(infoService.checkMail(userDTO.getEmail())){
//            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.FOUND);
//        }
//
//        User user = new User(service.getNextID(),userDTO);
//        UserInfo userInfo = new UserInfo(user.getId(),userDTO);
//        UserRole userRole = new UserRole(user.getId(), AvailableRoles.ROLE_USER.getData());
//
//        service.addUser(user);
//        infoService.addUserInfo(userInfo);
//        roleService.addUserRole(userRole);

        return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
    }
}
