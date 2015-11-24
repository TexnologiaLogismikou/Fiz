/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.Friend;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author milena
 */
public interface IFriendService
{
    @Transactional
    public void addFriend(Friend friend);
    
    @Transactional
    public List<Friend> getFriendsByUser(Long userid);
    
    @Transactional
    public Boolean checkFriendIfExists(Friend friend);
}
