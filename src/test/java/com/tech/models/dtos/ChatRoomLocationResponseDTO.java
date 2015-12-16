/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

/**
 *
 * @author bill5_000
 */
public class ChatRoomLocationResponseDTO {
    private float lng;
    private float lat;
    private String response;

    public String getResponse() {
        return response;
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }
}
