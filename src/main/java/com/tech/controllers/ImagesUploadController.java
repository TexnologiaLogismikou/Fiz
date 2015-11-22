/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.models.entities.ImagesMod;
import com.tech.services.interfaces.IImagesService;
import com.tech.services.interfaces.IUserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author KuroiTenshi
 */
@RestController
@RequestMapping("/upload")
public class ImagesUploadController {
    ClassLoader cl = getClass().getClassLoader();
    
    @Autowired
    IImagesService service;
    
    @Autowired
    IUserService userService;

    /**
     * 
     * @param userid
     * @param file
     * @return http status depending on the validations
     */
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<String> loadImages(@RequestParam("userid") Long userid, @RequestParam("file") MultipartFile file){
        Timestamp tm = new Timestamp(Calendar.getInstance(Locale.getDefault()).getTime().getTime());
 
        if (userService.getUserById(userid) == null){
            return new ResponseEntity<>("User doesnt exist",null,HttpStatus.NOT_FOUND);
        }
        if(!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                ImagesMod img = new ImagesMod(userid,tm
                        ,bytes,tm.hashCode()+userid.hashCode());//TODO
                service.addImage(img);
                return new ResponseEntity<>("G00D", null, HttpStatus.OK);
            }catch (Exception e) {
                return new ResponseEntity<>("bad", null, HttpStatus.NOT_FOUND);
            } 
        } else {
            return new ResponseEntity<>("empty", null, HttpStatus.NOT_FOUND);
        }   
    }
        
    /**
     * 
     * @param arithName
     * @param extension
     * @return null if the extension was wrong else a picture either the selected or an error picture
     * @throws IOException 
     */
    @RequestMapping("/get/{arithName:^[1-9][0-9]+\\.}{extension}")
    public @ResponseBody byte[] handle(@PathVariable String arithName, @PathVariable String extension) throws IOException {
        if (extension.equalsIgnoreCase("jpg")){
            Long num = Long.parseLong(arithName.substring(0,arithName.length()-1));
            if (service.checkImagesByHashtag(num)){
                return service.getImageByHashtag(num).getImages();                
            } else {
                return Files.readAllBytes(new File(cl.getResource("images/ntf.jpg").getFile()).toPath());
            }
        } 
        return null;
    }

}
