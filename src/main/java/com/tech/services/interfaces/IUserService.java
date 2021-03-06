package com.tech.services.interfaces;

import com.tech.models.entities.user.User;

import javax.transaction.Transactional;
import java.util.List;

public interface IUserService {
    @Transactional
    User getUserById(Long id);

    @Transactional
    User getUserByUsername(String username);

    @Transactional
    void addUser(User user);

    @Transactional
    List<User> getAllUsers();
    
    @Transactional
    boolean checkUsername(String username);
    
    @Transactional
    boolean validateUser(User user);
    
    @Transactional
    boolean validateUser(String username,String password);
    
    @Transactional
    long getCount();
    
    @Transactional
    long getNextID();
    
    @Transactional
    User getLastRecord();
    
    @Transactional
    void modifyUser(User modifiedUser);
    
    @Transactional
    public void updateUserRoom(boolean hasRoom, Long userid);
}
