/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.ImagesMod;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KuroiTenshi
 */
@Repository
public interface IImagesRepository extends JpaRepository<ImagesMod,Long>{
    List<ImagesMod> findByUserid(Long id);
    ImagesMod findByHashtag(Long hashtag);
    
}
