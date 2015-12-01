/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.repositories;

import com.tech.models.entities.User;
import com.tech.models.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author iwann
 */
@Repository
public interface IUserInfoRepository extends JpaRepository<UserInfo, Long>{
    
    @Modifying
    @Query("update UserInfo u set u.email = ?1, u.profile_photo = ?2, u.status = ?3, u.last_name = ?4, u.birthday = ?5, u.hometown = ?6 where u.userid = ?7")
    void setUserInfoById(String email, String profile_photo, String status, String last_name, String birthday, String hometown, Long userid );
    
    UserInfo findByUserid(Long userid);
}
