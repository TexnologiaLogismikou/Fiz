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
    ALREADY_HAS_A_ROOM("user already has a room"),
    ALREADY_EXISTS("already exists"),
    FILE_ERROR("file error"),
    FILE_WAS_EMPTY("empty file"),
    FRIEND_ALREADY_EXIST("friend already exists"),
    FRIEND_DOES_NOT_EXIST("friend does not exist"),
    SUCCESS("success"),
    OUTSIDE_RANGE("outside range"),
    VALIDATOR_NOT_FOUND("validator not found"),
    ACCESS_METHOD_NOT_ALLOWED("access method is not allowed"),
    BAD_COORDINATES("bad coordinates"),
    ACCESS_METHOD_NOT_FOUND("access method was not found"),
    STRING_INAPPROPRIATE_FORMAT("string format was wrong"),
    NOT_CONNECTED_TO_THE_ROOM("not connected to the room"),
    FIRST_NAME_INAPPROPRIATE_FORMAT("first name format was wrong"),
    ROOM_NOT_FOUND("room was not found"),
    LAST_NAME_INAPPROPRIATE_FORMAT("last name format was wrong"),
    EMAIL_INAPPROPRIATE_FORMAT("emal format was wrong"),
    ID_INAPPROPRIATE_FORMAT("ID format was wrong"),
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
