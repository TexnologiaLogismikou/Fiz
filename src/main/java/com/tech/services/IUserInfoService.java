/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.models.entities.UserInfo;
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
    
}
