/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.user;

import com.tech.services.interfaces.IUserInfoService;
import com.tech.models.entities.user.UserInfo;
import com.tech.repositories.IUserInfoRepository;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
    
    @Override
    @Transactional
    public UserInfo getUserInfoByEmail(String mail){
        return repository.findByEmail(mail);
    }
    
    @Override
    @Transactional
    public boolean checkMail(String mail){
        return repository.findByEmail(mail)!=null;
    }
   
    @Override
    @Transactional
    public List<UserInfo> findByFirstName(String first_name){
        return repository.findByFirstName(first_name);
    }
    
    @Override
    @Transactional
    public List<UserInfo> findByLastName(String last_name){
        return repository.findByLastName(last_name);
    }
    
    @Override
    @Transactional
    public List<UserInfo> findByBirthDay(Date birthday){
        return repository.findByBirthDay(birthday);
    }
    
    @Override
    @Transactional
    public List<UserInfo> findByHomeTown(String hometown){
        return repository.findByHomeTown(hometown);
    }
    
}
