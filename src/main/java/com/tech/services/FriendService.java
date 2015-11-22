/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.models.entities.Friend;
import com.tech.repositories.IFriendRepository;
import com.tech.services.interfaces.IFriendService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author milena
 */

@Service
public class FriendService implements IFriendService
{
     @Autowired
    private IFriendRepository repository;

    @Override
    @Transactional
    public void addFriend(Friend friend)
    {
        repository.save(friend);
    }
    
    @Override
    @Transactional
    public void deleteFriend(Friend friend)
    {
        repository.delete(friend);
    }
    
    /**
     * 
     * @param userid
     * @return 
     */
    @Transactional
    @Override
    public List<Friend> getFriendsByUser(Long userid)
    {  
        return repository.findByUserid(userid);
    }  
    
    @Override
    @Transactional
    //Checking if the friend give as parameter, exists in the friendRepository
    public boolean checkExistence(Friend someFriend)
    {
        boolean found = false;
        for (Friend friend: repository.findAll())
        {
            if (someFriend.getUserid().compareTo(friend.getUserid())==0 && someFriend.getFriendid().compareTo(friend.getFriendid())==0)
            {
                found = true;
            }   
        }
        return found;
    }
}
