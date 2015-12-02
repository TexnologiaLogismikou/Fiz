/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

/**
 *
 * @author KuroiTenshi
 */
public enum Attr {
    IMAGES_OUTPUT_FOLDER("C:\\FizData\\Images"),
    NO_IMAGE_ASSIGNED(null);
    
    private final String str;
    Attr(String str) {
        this.str = str;        
    }
    
    public String getData(){
        return str;
    }
    
}
