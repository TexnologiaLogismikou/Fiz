/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.Friend;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author milena
 */
public interface IFriendService
{
    @Transactional
    void addFriend(Friend friend);
    
    @Transactional
    void deleteFriend(Friend friend);
    
    @Transactional
    List<Friend> getFriendsByUser(Long userid);
    
    @Transactional
    boolean checkFriendIfExists(Long userid,Long friendid);
    
    @Transactional
    List<Friend> getAllFriends();
    
    @Transactional
    public List<Friend> getFriendsByMonth(Long userid);
    
    @Transactional
    public List<Friend> getFriendsByYear(Long userid);
    
}
