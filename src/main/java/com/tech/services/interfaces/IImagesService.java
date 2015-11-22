/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.ImagesMod;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author KuroiTenshi
 */
public interface IImagesService {
    
    @Transactional
    public void addImage(ImagesMod newImg);
    
    @Transactional
    public List<ImagesMod> getImageByUserID(long id);
    
    @Transactional
    public ImagesMod getImageByHashtag(Long tag);
    
    @Transactional
    long getNextID();
    
    @Transactional
    public List<ImagesMod> getAllImages();
    
    @Transactional
    public void deleteImage(ImagesMod images);
    
    @Transactional
    public boolean checkImagesByHashtag(Long hashtag);
    
    @Transactional
    public long getCount();
}
