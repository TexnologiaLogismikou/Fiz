/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.services.interfaces.IUserInfoService;
import com.tech.models.entities.UserInfo;
import com.tech.repositories.IUserInfoRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author iwann
 */
@Service
public class UserInfoService implements IUserInfoService {

    @Autowired
    private IUserInfoRepository repository;

    @Override
    @Transactional
    public void modifyUserInfo(UserInfo modifiedUser) {
        repository.setUserInfoById(modifiedUser.getFirstName(),modifiedUser.getLastName(),modifiedUser.getBirthday(),
                modifiedUser.getEmail(),modifiedUser.getStatus(),modifiedUser.getProfilePhoto(),
                modifiedUser.getHometown(), modifiedUser.getUserid());
    }

    @Override
    @Transactional
    public void addUserInfo(UserInfo userInfo) {
        repository.save(userInfo);
    }

    @Override
    @Transactional
    public UserInfo getUserInfoByUserId(Long userId){
        return repository.findByUserid(userId);
    }
   
}
