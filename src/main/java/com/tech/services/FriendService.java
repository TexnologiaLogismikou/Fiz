/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services;

import com.tech.models.entities.Friend;
import com.tech.repositories.IFriendRepository;
import com.tech.services.interfaces.IFriendService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public List<Friend> getFriendsByMonth(Long userid)
    { 
        List<Friend> friend = repository.findByUserid(userid);
        List<Friend> tmstampFriends = new ArrayList();
        
        Date currentDate = new Date(); 
        SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
        String month = dateFormatMonth.format(currentDate);
        System.out.println(month);
        String year = dateFormatYear.format(currentDate);
        System.out.println(year);
        for(Friend vLookUp:friend)
        {
            String tmstampMonth = dateFormatMonth.format(vLookUp.getTimestamp());
            String tmstampYear = dateFormatYear.format(vLookUp.getTimestamp());
            if(Integer.parseInt(year)==Integer.parseInt(tmstampYear))
            {
                if(Integer.parseInt(month)==Integer.parseInt(tmstampMonth))
                {
                    tmstampFriends.add(vLookUp);
                }
            }
        }
        return tmstampFriends;
    }
    
    @Transactional
    @Override
    public List<Friend> getFriendsByYear(Long userid){
        
        List<Friend> friend = repository.findByUserid(userid);
        List<Friend> timestampFriends = new ArrayList();
        
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String year = dateFormat.format(currentDate);
        
        for(Friend vLookUp:friend){
            String timestampYear = dateFormat.format(vLookUp.getTimestamp());
            if(Integer.parseInt(year)==Integer.parseInt(timestampYear)){
                timestampFriends.add(vLookUp);
            }
        }
        return timestampFriends;
    }
}
