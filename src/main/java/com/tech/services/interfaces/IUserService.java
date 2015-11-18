package com.tech.services.interfaces;

import com.tech.models.entities.User;

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
    void addUsers(List<User> users);

    @Transactional
    void deleteUser(User user);

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
}
