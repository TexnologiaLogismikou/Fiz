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

import java.util.Date;
import java.util.List;

/**
 *
 * @author iwann
 */
@Repository
public interface IUserInfoRepository extends JpaRepository<UserInfo, Long>{
    @Modifying
    @Query("update UserInfo u set u.firstname = ?1, u.last_name = ?2, u.birthday = ?3, "
            + "u.email = ?4, u.status = ?5, u.profile_photo = ?6, u.hometown = ?7 where u.userid = ?8")
    void setUserInfoById(String first_name, String last_name, Date birthday, String email,
                         String status, String profile_photo, String hometown, Long userid );

    UserInfo findByUserid(Long userid);
    List<UserInfo> findByFirstName(String first_name);
    List<UserInfo> findByLastName(String last_name);
    List<UserInfo> findByBirthDay(Date birthday);
    UserInfo findByEmail(String email);
    List<UserInfo> findByHomeTown(String hometown);
}
