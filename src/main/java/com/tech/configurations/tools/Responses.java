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
public enum Responses {
    ERROR("error"),
    FILE_ERROR("file error"),
    FILE_WAS_EMPTY("empty file"),
    FRIEND_ALREADY_EXIST("friend already exists"),
    FRIEND_DOES_NOT_EXIST("friend does not exist"),
    SUCCESS("success"),
    STRING_INAPPROPRIATE_FORMAT("string format was wrong"),
    NOT_AUTHORIZED("not authorized"),
    NOT_AVAILABLE("not available"),
    AVAILABLE("available");
    
    private final String str;
    Responses(String str) {
        this.str = str;        
    }
    
    public String getData(){
        return str;
    }
}
