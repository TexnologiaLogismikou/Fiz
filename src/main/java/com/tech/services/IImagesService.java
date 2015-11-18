/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.Models.ImagesMod;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author KuroiTenshi
 */
public interface IImagesService {
    
    @Transactional
    public void addImage(ImagesMod newImg);
    
    @Transactional
    public ImagesMod getImageByID(long id);
    
    @Transactional
    long getNextID();
}
