/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations;

import com.tech.configurations.tools.Validator;
import com.tech.configurations.tools.customvalidators.ChatroomCreationDTOValidator;
import com.tech.configurations.tools.customvalidators.FriendDTOValidator;
import com.tech.configurations.tools.customvalidators.RegisteredUserDTOValidator;

/**
 *
 * @author KuroiTenshi
 */
public class InitializeValidators {
    public static void InitializeCustomValidators(){
        new ChatroomCreationDTOValidator();
        new FriendDTOValidator();
        new RegisteredUserDTOValidator();
        
        Validator.registerMethod("blacklist");
        Validator.registerMethod("whitelist");
        
        Validator.registerPrivilege("PUBLIC");
        Validator.registerPrivilege("PRIVATE");
    }
}
