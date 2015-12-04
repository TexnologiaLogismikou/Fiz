/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.configurations.tools.Attr;
import com.tech.controllers.superclass.BaseController;
import com.tech.models.entities.ImagesMod;
import com.tech.services.interfaces.IImagesService;
import com.tech.services.interfaces.IUserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author KuroiTenshi
 */
@RequestMapping("/images")
public class ImagesController extends BaseController{

    @Autowired
    IImagesService service;

    @Autowired
    IUserService userService;
    
    /**
     *
     * @param name
     * @param file
     * @return http status depending on the validations
     */
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<String> loadImages(@RequestParam("file") MultipartFile file,@RequestParam("username") String name){
        Long sm = userService.getUserByUsername(name).getId();

        if(!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                ImagesMod img = new ImagesMod(sm);

                File newFile = new File(img.getImagePath());
                if (!newFile.getParentFile().exists()){
                    newFile.getParentFile().mkdirs(); //need check if creation fails?
                }
                Files.write(newFile.toPath(), bytes, StandardOpenOption.CREATE);//if file exists?

                service.addImage(img);

                return new ResponseEntity<>("Success", HttpStatus.OK); 
            }catch (Exception e) {                
                return new ResponseEntity<>("File Error", HttpStatus.NOT_MODIFIED); 
            }
        } else {
            return new ResponseEntity<>("Empty file", HttpStatus.NO_CONTENT); 
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
                String path = service.getImageByHashtag(num).getImagePath();
                return Files.readAllBytes(new File(path).toPath()); 
            } else {
                return Files.readAllBytes(new File(Attr.NO_IMAGE_FOUND.getData()).toPath());
            }
        }
        return null;
    }
}