/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities;

import com.tech.models.dtos.UserInfoDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author iwann
 */
@Entity
@NamedQuery(name = "UserInfo.findByUserid", query = "SELECT p FROM UserInfo p WHERE p.userid = ?1")
@Table (name = "user_info")
public class UserInfo {
    @Id
    @Column(name = "userid")
    private Long userid;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "profile_photo")
    private String profile_photo;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "last_name")
    private String last_name;
    
    @Column(name = "birthday")
    private String birthday;
    
    @Column(name = "hometown")
    private String hometown;

    public UserInfo(Long userid,UserInfoDTO userInfoDTO){
        this(userid,userInfoDTO.getEmail(),userInfoDTO.getProfile_photo(),userInfoDTO.getStatus(),
                userInfoDTO.getLast_name(),userInfoDTO.getBirthday(),userInfoDTO.getHometown());
    }
    
    public UserInfo(Long userid, String email, String profile_photo, 
            String status, String last_name, String birthday, String hometown){
        this.userid = userid;
        this.email = email;
        this.profile_photo = profile_photo;
        this.status = status;
        this.last_name = last_name;
        this.birthday = birthday;
        this.hometown = hometown;
    }
    
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhoto() {
        return profile_photo;
    }

    public void setProfilePhoto(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }
    
}
