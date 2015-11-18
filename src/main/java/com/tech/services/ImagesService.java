/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.Models.ImagesMod;
import com.tech.Repositories.IImagesRepository;
import java.util.List;
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
    @Override
    public ImagesMod getImageByID(long id){
        return repository.getOne(id);
    }
    
    @Override
    @Transactional
    public ImagesMod getImageByName(String name){
        return repository.findByName(name);
    }
    
    @Transactional
    @Override
    public void addImage(ImagesMod newImg){
        repository.save(newImg);
    }
   

    @Override
    @Transactional
    public long getNextID() {
        return repository.count();
        
    }
    
    @Override
    @Transactional
    public List<ImagesMod> getAllUsers() {
        return repository.findAll();
    }
    
    @Override
    @Transactional
    public List<ImagesMod> getAllUsers(Long id) {
        return repository.findById(id);
    }

    
    @Override
    @Transactional
    public void deleteImage(ImagesMod images) {
        repository.delete(images);
    }
    
}
