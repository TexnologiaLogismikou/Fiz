/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.Friend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author milena
 */
@Repository
public interface IFriendRepository extends JpaRepository<Friend, Long> 
{
    List<Friend> findByUserid(Long userid);
}
