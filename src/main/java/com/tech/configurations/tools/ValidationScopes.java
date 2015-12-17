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
public enum ValidationScopes {
/* 1*/ROOM_NAME("room_name"),
/* 2*/USER_NAME("user_name"),
/* 3*/ROOM_PRIVILEGE("room_privilege"), // -> PUBLIC , PRIVATE
/* 4*/ROOM_ACCESS_METHOD("room_access_method"), // -> blacklist , Whitelist
/* 5*/NUMBER("number"),
/* 6*/RANGE("range"),
/* 7*/LATITUDE("latitude"),
/* 8*/LONGITUDE("longitude"),
/* 9*/EMAIL("email"),
/*10*/TTL("ttl"),
/*11*/MODE("mode"), // -> I allios Method , ADD - DELETE - UPDATE
/*12*/PASSWORD("password"),
/*13*/STRING("string"),
/*14*/PROFILE_PHOTO("profile_photo"),
/*15*/STATUS("status");

    
    private final String str;
    ValidationScopes(String str) {
        this.str = str;        
    }
    
    public String getData(){
        return str;
    }
    
}
