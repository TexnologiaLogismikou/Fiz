/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.Repositories;

import com.tech.Models.ImagesMod;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KuroiTenshi
 */
@Repository
public interface IImagesRepository extends JpaRepository<ImagesMod,Long>{
    ImagesMod findByName(String name);
    List<ImagesMod> findById(Long id);
}
