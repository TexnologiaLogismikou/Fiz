/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.daos.interfaces;

import java.util.List;

/**
 *
 * @author iwann
 */
public interface UserInfoDAO {
    void save(UserInfoDAO userInfoDAO);
    List<UserInfoDAO> list();
    
}
