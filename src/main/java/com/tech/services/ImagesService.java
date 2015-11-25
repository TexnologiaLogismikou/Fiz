/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.models.entities.ImagesMod;
import com.tech.repositories.IImagesRepository;
import java.util.List;

import com.tech.services.interfaces.IImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author KuroiTenshi
 */
@Service
public class ImagesService implements IImagesService {
    
    @Autowired
    private IImagesRepository repository;
    
    @Transactional
    @Override
    public List<ImagesMod> getImageByUserID(long id){
        return repository.findByUserid(id);
    }
    
    @Override
    @Transactional
    public ImagesMod getImageByHashtag(Long tag){
        return repository.findByHashtag(tag);
    }
    
    @Transactional
    @Override
    public void addImage(ImagesMod newImg){
        repository.save(newImg);
    }
   

    @Override
    @Transactional
    public List<ImagesMod> getAllImages() {
        return repository.findAll();
    }
    
       
    @Override
    @Transactional
    public void deleteImage(ImagesMod images) {
        repository.delete(images);
    }
    
    /**
     * 
     * @param name
     * @return true if the image exists else false
     */
    @Override
    public boolean checkImagesByHashtag(Long hashtag) {
        ImagesMod tstImg = repository.findByHashtag(hashtag);
        return tstImg!=null;
    }
    
    @Override
    public long getCount() {
        return repository.count();
    }
}
