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
    void addImage(ImagesMod newImg);
    
    @Transactional
    List<ImagesMod> getImageByUserID(long id);
    
    @Transactional
    ImagesMod getImageByHashtag(Long tag);
    
    @Transactional
    List<ImagesMod> getAllImages();
    
    @Transactional
    void deleteImage(ImagesMod images);
    
    @Transactional
    boolean checkImagesByHashtag(Long hashtag);
    
    @Transactional
    long getCount();
}
