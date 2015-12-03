/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.models.entities.Friend;
import com.tech.repositories.IFriendRepository;
import com.tech.services.interfaces.IFriendService;
import java.util.Date;
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
    
    /**
     * 
     * @param userid
     * @param friendid
     * @return returns True if Friend exists
     */
    @Transactional
    @Override
    public boolean checkFriendIfExists(Long userid, Long friendid) 
    {        
       return repository.findByUseridAndFriendid(userid,friendid) != null;
    }
    
    @Transactional
    @Override
    public List<Friend> getAllFriends()
    { 
        return repository.findAll();
    }
    
    @Transactional
    @Override
    public List<Friend> getFriendsByMonth(Date date)
    { 
//        throw new UnsupportedOperationException("not supported yet");
        return repository.findByDate(date); //TODO
    }
    
}
