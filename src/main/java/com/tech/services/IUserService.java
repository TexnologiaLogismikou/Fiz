package com.tech.services;

import com.tech.Models.User;

import javax.transaction.Transactional;
import java.util.List;

public interface IUserService {
    @Transactional
    User getUserById(Long id);

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
}
