package com.tech.controllers;

import com.tech.configurations.tools.Pair;
import com.tech.configurations.tools.Responses;
import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.EmptyStringValidator;
import com.tech.configurations.tools.customvalidators.interfaces.ICustomValidator;
import com.tech.configurations.tools.customvalidators.interfaces.IStringValidator;
import com.tech.controllers.superclass.BaseController;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import com.tech.models.dtos.user.UserDTO;
import com.tech.models.entities.user.User;
import com.tech.models.entities.user.UserInfo;
import com.tech.services.interfaces.IUserInfoService;
import com.tech.services.interfaces.IUserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    IUserService service;
    @Autowired
    IUserInfoService infoService;

    /**
     * not tested
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public HttpEntity<JSONObject> loadUserProfile(@PathVariable String username) {
        JSONObject json = new JSONObject();
        
        Pair<Boolean,ResponseEntity> response = validate(username);
        if(!response.getLeft()){            
            json.put("response", response.getRight().getBody());
            return new ResponseEntity<>(json, HttpStatus.NOT_ACCEPTABLE);
        }     
        
        if (!service.checkUsername(username)) {
            json.put("response", Responses.NOT_AVAILABLE.getData());
            return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
        }

        User user = service.getUserByUsername(username);
        UserInfo userInfo = infoService.getUserInfoByUserId(user.getId());

        json.put("username", user.getUsername());
        json.put("firstname", userInfo.getFirstName());
        json.put("last_name", userInfo.getLastName());
        json.put("profile_photo", userInfo.getProfilePhoto());
        json.put("email", userInfo.getEmail());
        json.put("birthday", userInfo.getBirthday());
        json.put("hometown", userInfo.getHometown());
        json.put("status", userInfo.getStatus());
        json.put("response", Responses.SUCCESS.getData());

        return new ResponseEntity<>(json, HttpStatus.OK);

    }

    /**
     * not tested
     *
     * @param username
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/{username}/modify", method = RequestMethod.POST)
    public HttpEntity<String> deactivateUser(@PathVariable String username, @RequestBody UserDTO userDTO) {
        Pair<Boolean, ResponseEntity> response = userDTO.validate();
        if (!response.getLeft()) {
            return response.getRight();
        }

        if (!service.checkUsername(username)) {
            return new ResponseEntity<>(Responses.NOT_AVAILABLE.getData(), HttpStatus.NOT_FOUND);
        }

        Long userId = service.getUserByUsername(username).getId();

        User user = new User(userId, userDTO);
        UserInfo userInfo = new UserInfo(userId, userDTO);

        service.modifyUser(user);
        infoService.modifyUserInfo(userInfo);

        return new ResponseEntity<>(Responses.SUCCESS.getData(), HttpStatus.OK);
    }

    /* Validation Pattern */
    private static final List<IStringValidator> USER_NAME_VALIDATORS = new ArrayList<>(Arrays.asList(new EmptyStringValidator()));
    
    public static void registerValidator(ICustomValidator newValidator,ValidationScopes scope) 
            throws InappropriateValidatorException, ValidatorNotListedException{
        if(newValidator instanceof IStringValidator){
            registerStringValidator((IStringValidator)newValidator, scope);
        } 
        else {
            throw new InappropriateValidatorException();
        }
    }    
    
    public static void cleanValidator(){
        USER_NAME_VALIDATORS.clear();
        
        USER_NAME_VALIDATORS.add(new EmptyStringValidator());     
    }
    
    public static List<String> getValidatorList(ValidationScopes scope) 
            throws ValidatorNotListedException {
        List<String> list = new ArrayList<>();
        int i = 0;
        switch(scope)
        {
            case USER_NAME:
                for(ICustomValidator vLookUp:USER_NAME_VALIDATORS){
                    if(vLookUp.getName().equals("Empty")) { continue; }
                    i++;
                    list.add(i + ": " + vLookUp.getName());
                }
                return list;
            default:
                throw new ValidatorNotListedException();                    
        }
    }  
    public static boolean removeValidator(ValidationScopes scope, int i) 
            throws ValidatorNotListedException, InappropriateValidatorException
    {
        if(i==0)
        {
            return false;
        }
        switch(scope)
        {
            case USER_NAME:
                if(USER_NAME_VALIDATORS.get(i) != null)
                {
                    USER_NAME_VALIDATORS.get(i-1).replaceNext(USER_NAME_VALIDATORS.get(i).getNext());
                    USER_NAME_VALIDATORS.remove(i);
                    return true;
                }
                return false;
            default:
                throw new ValidatorNotListedException();                    
        }
             
    }     
    
    private Pair<Boolean,ResponseEntity> validate(String username)        
    {  
        return USER_NAME_VALIDATORS.get(0).validate(username);
    }
    
    private static void registerStringValidator(IStringValidator strVal , ValidationScopes scope)
            throws ValidatorNotListedException, InappropriateValidatorException
    {  
         switch(scope){
            case USER_NAME:
                USER_NAME_VALIDATORS.add(strVal);
                USER_NAME_VALIDATORS.get(0).setNext(strVal);
                break;
            default: 
                throw new ValidatorNotListedException();                    
        }                  
    }
}