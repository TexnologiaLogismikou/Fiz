/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations;

import com.tech.configurations.tools.ValidationScopes;
import com.tech.configurations.tools.customvalidators.elements.numbervalidators.NotEmptyValidatorN;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NoSpacesValidator;
import com.tech.models.dtos.chatroom.ChatroomUpdateDTO;
import com.tech.configurations.tools.customvalidators.elements.stringvalidators.NotEmptyValidatorS;
import com.tech.exceptions.customexceptions.InappropriateValidatorException;
import com.tech.exceptions.customexceptions.ValidatorNotListedException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KuroiTenshi
 */
public class InitializeValidators {
    public static void InitializeCustomValidators(){
        try {
            ChatroomUpdateDTO.registerValidator(new NotEmptyValidatorN(),ValidationScopes.NUMBER);
            ChatroomUpdateDTO.registerValidator(new NotEmptyValidatorS(),ValidationScopes.STRING);
            ChatroomUpdateDTO.registerValidator(new NoSpacesValidator(),ValidationScopes.STRING);
        } catch (InappropriateValidatorException | ValidatorNotListedException ex) {
            Logger.getLogger(InitializeValidators.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
