/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author KuroiTenshi
 */
public class ImageDTO {
    private MultipartFile newFile;
    private String userid;
    
    public String getUserID(){
        return userid;
    }
    
    public MultipartFile getFile(){
        return newFile;
    }
    
}
