/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities;

import com.tech.configurations.tools.Attr;
import com.tech.models.dtos.RegisteredUserDTO;
import com.tech.models.dtos.UserDTO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author iwann
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "UserInfo.findByUserid", query = "SELECT p FROM UserInfo p WHERE p.userid = ?1"),
        @NamedQuery(name = "UserInfo.findByFirstName", query = "SELECT p FROM UserInfo p WHERE p.first_name = ?1"),
        @NamedQuery(name = "UserInfo.findByLastName", query = "SELECT p FROM UserInfo p WHERE p.last_name = ?1"),
        @NamedQuery(name = "UserInfo.findByBirthDay", query = "SELECT p FROM UserInfo p WHERE p.birthday = ?1"),
        @NamedQuery(name = "UserInfo.findByEmail", query = "SELECT p FROM UserInfo p WHERE p.email = ?1"),
        @NamedQuery(name = "UserInfo.findByHomeTown", query = "SELECT p FROM UserInfo p WHERE p.hometown = ?1")
})
@Table (name = "user_info")
public class UserInfo implements Serializable {
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
    private Date birthday;

    @Column(name = "hometown")
    private String hometown;

    @Column(name = "first_name")
    private String first_name;

    public UserInfo (){

    }

    public UserInfo(Long userid,UserDTO userDTO){
        this(userid,userDTO.getEmail(),userDTO.getProfile_photo(),userDTO.getStatus(),
                userDTO.getLast_name(),userDTO.getBirthday(),userDTO.getHometown(),userDTO.getFirstName());
    }

    public UserInfo(Long userid,RegisteredUserDTO userDTO){
        this(userid,userDTO.getEmail(),Attr.NO_IMAGE_ASSIGNED.getData(),null,
                userDTO.getLast_name(),userDTO.getBirthday(),null,userDTO.getFirstName());

    }

    public UserInfo(Long userid, String email, String profile_photo,
                    String status, String last_name, Date birthday, String hometown, String first_name){
        this.userid = userid;
        this.email = email;
        this.profile_photo = profile_photo;
        this.status = status;
        this.last_name = last_name;
        this.birthday = birthday;
        this.hometown = hometown;
        this.first_name = first_name;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_Name) {
        this.first_name = first_Name;
    }
}
