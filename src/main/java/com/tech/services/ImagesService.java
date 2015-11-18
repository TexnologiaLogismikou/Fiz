/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.Models.ImagesMod;
import com.tech.Repositories.IImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author KuroiTenshi
 */
@Service
public class ImagesService implements IImagesService{
    
    @Autowired
    private IImagesRepository repository;
    
    @Transactional
    public ImagesMod getImageByID(long id){
        return repository.getOne(id);
    }
    
    @Transactional
    public ImagesMod getImageByName(String name){
        return repository.findByName(name);
    }
    
    @Transactional
    public void addImage(ImagesMod newImg){
        repository.save(newImg);
    }
   

    @Override
    @Transactional
    public long getNextID() {
        return repository.count();
        
    }

    
}
