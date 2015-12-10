/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations;

import com.tech.configurations.tools.customvalidators.ChatroomCreationDTOValidator;

/**
 *
 * @author KuroiTenshi
 */
public class InitializeValidators {
    public static void InitializeCustomValidators(){
        new ChatroomCreationDTOValidator();
    }
}
