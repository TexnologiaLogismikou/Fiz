package com.tech.controllers;

import com.tech.configurations.tools.AvailableRoles;
import com.tech.configurations.tools.Host;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.Validator;
import com.tech.controllers.superclass.BaseController;
import com.tech.models.dtos.RegisteredUserDTO;
import com.tech.models.entities.User;
import com.tech.models.entities.UserInfo;
import com.tech.models.entities.UserRole;
import com.tech.services.interfaces.IUserInfoService;
import com.tech.services.interfaces.IUserRoleService;
import com.tech.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = Host.apache)
@RestController
@RequestMapping("/register")
public class RegistrationController extends BaseController{

    @Autowired
    IUserService service;
    
    @Autowired 
    IUserInfoService infoService;
    
    @Autowired
    IUserRoleService roleService;
    
    /**
     * 
     * @param userDTO
     * @return because it expects the validation on front end the data posted on this function will
     *         be registered without further validation. Returns only "Success" , HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<String> register(@RequestBody RegisteredUserDTO userDTO) {
        if(!Validator.nameValidation(userDTO.getUsername())){
            return new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT.getData(),HttpStatus.NOT_ACCEPTABLE);
        }
        if(!Validator.passwordValidator(userDTO.getPassword())){
            return new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT.getData(),HttpStatus.NOT_ACCEPTABLE);            
        }
        if(userDTO.getFirstName().isEmpty() || userDTO.getLast_name().isEmpty() || userDTO.getEmail().isEmpty()){
            return new ResponseEntity<>(Responses.STRING_INAPPROPRIATE_FORMAT.getData(),HttpStatus.NOT_ACCEPTABLE);
        }
        
        if(service.checkUsername(userDTO.getUsername())) {
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(),HttpStatus.FOUND);            
        }
        
        User user = new User(service.getNextID(),userDTO);
        UserInfo userInfo = new UserInfo(user.getId(),userDTO);
        UserRole userRole = new UserRole(user.getId(),AvailableRoles.ROLE_USER.getData());        
        
        service.addUser(user);
        infoService.addUserInfo(userInfo);
        roleService.addUserRole(userRole);        
        
        return new ResponseEntity<>(Responses.SUCCESS.getData(),HttpStatus.OK);
    }
}