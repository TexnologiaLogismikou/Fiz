/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.dtos;

import com.tech.models.dtos.superclass.BaseDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author KuroiTenshi
 */
public class ImageDTO extends BaseDTO {
    private MultipartFile newFile;
    private String userid;
    
    @Override
    public String getDTOName() {
        return "ImageDTO";
    }
    
    public String getUserID(){
        return userid;
    }
    
    public MultipartFile getFile(){
        return newFile;
    }
    
}
