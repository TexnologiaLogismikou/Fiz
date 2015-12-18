/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.user.UserInfo;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author iwann
 */
public interface IUserInfoService {

    @Transactional
    void addUserInfo(UserInfo userInfo);

    @Transactional
    UserInfo getUserInfoByUserId(Long userId);

    @Transactional
    void modifyUserInfo(UserInfo modifiedUser);
    
    @Transactional
    public UserInfo getUserInfoByEmail(String mail);
    
    @Transactional
    public boolean checkMail(String mail);
    
    @Transactional
    public List<UserInfo> findByFirstName(String first_name);
            
    @Transactional
    public List<UserInfo> findByLastName(String last_name);
    
    @Transactional
    public List<UserInfo> findByBirthDay(Date birthday);
    
    @Transactional
    public List<UserInfo> findByHomeTown(String hometown);
    
    
    
}
