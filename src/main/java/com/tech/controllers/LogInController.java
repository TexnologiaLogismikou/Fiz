package com.tech.controllers;

import com.tech.configurations.tools.Host;
import com.tech.controllers.superclass.BaseController;
import com.tech.models.dtos.LoginUserDTO;
import com.tech.models.entities.User;
import com.tech.services.interfaces.IUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = Host.apache)
@RestController
@RequestMapping("/login")
public class LogInController extends BaseController {

    @Autowired
    IUserService service;

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<JSONObject> login(@RequestBody LoginUserDTO loginUserDTO) {
        JSONObject object = new JSONObject();
        
        if (service.validateUser(loginUserDTO.getUsername(), loginUserDTO.getPassword())) {
            User user = service.getUserByUsername(loginUserDTO.getUsername());

            object.put("username", user.getUsername());
            object.put("role", "ROLE_USER");// na pernei to role apo ton pinaka roles.
            object.put("error","success");
            return new ResponseEntity<>(object,HttpStatus.OK);
        }
        object.put("username","NOT_AUTHORIZED");
        object.put("role","NOT_AUTHORIZED");
        object.put("error","error");
        return new ResponseEntity<>(object,HttpStatus.OK);
    }
}
