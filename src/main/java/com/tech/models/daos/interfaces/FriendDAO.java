/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.daos.interfaces;

import java.util.List;

/**
 *
 * @author milena
 */
public interface FriendDAO 
{
    void save(FriendDAO friendDAO);
    List<FriendDAO> list();
}
