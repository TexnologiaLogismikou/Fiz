/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.controllers;

import com.tech.models.entities.ImagesMod;
import com.tech.services.interfaces.IImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @Autowired
    IImagesService service;
    
//    @RequestMapping(value = "/a",method = RequestMethod.POST/*,produces = "image/jpg"*/ )//value = "/a",
//    public HttpEntity<String> loadImages(@RequestParam("name") String name, @RequestParam("file") byte[] file){
//        //TODO kapos 8a pei kai pios user anebase tin eikona stin kataxwrisi 
//        if (!service.checkImagesByName(name)) {
//            service.addImage(new ImagesMod(service.getNextID(),name,file));    
//            return new ResponseEntity<>("G00D", null, HttpStatus.OK);         
//        }
//        return new ResponseEntity<>("image name already exists", null, HttpStatus.FOUND); 
//                        
//    }
    
    @RequestMapping(value = "/c",method = RequestMethod.POST,produces = "image/jpg")//value = "/a",
    public HttpEntity<String> loadImages(@RequestParam("userid") String name, @RequestParam("file") MultipartFile file){
       if(!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                ImagesMod img = new ImagesMod(service.getNextID(),name,bytes);
                service.addImage(img);
                return new ResponseEntity<>("G00D", null, HttpStatus.OK);
            }catch (Exception e) {
                return new ResponseEntity<>("bad", null, HttpStatus.NOT_FOUND);
            } 
        } else {
            return new ResponseEntity<>("empty", null, HttpStatus.NOT_FOUND);
        }
//        return new ResponseEntity<>("bad", null, HttpStatus.NOT_FOUND); //on username failure (to find it) it will return a NOT_FOUND enum      
    }
    
    
    /**
     * to decode me to get egine amesos.. alla dn 8eloume get . 8eloume na paei se kapio pedio 
     * @param id
     * @return 
     */
    @RequestMapping(value = "/b", 
            method = RequestMethod.GET, 
            produces = "image/jpg")
    public @ResponseBody byte[]  downloadImages(@RequestParam("id") Long id) {
        ImagesMod img = service.getImageByID(id);
       return img.getImages();
    }
    
    @RequestMapping(method = RequestMethod.POST, 
            produces = "image/jpg")
    public @ResponseBody byte[]  downloadImagesWithPost(@RequestParam("id") Long id) {
        ImagesMod img = service.getImageByID(id);//kanei return men .. alla sto script kanw get apo tin alli sinartisi. dn douleuei 
        return img.getImages();
       
    }
}
