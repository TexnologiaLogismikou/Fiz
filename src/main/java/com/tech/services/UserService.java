package com.tech.services;

import com.tech.Models.User;
import com.tech.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    /**
     * Automatically "wires" connects the database with the repository
     */
    @Autowired
    private IUserRepository repository;

    /**
     * initialize a transaction with the repository - database to get an element
     * with its id
     * @param id
     * @return 
     */
    @Override
    @Transactional
    public User getUserById(Long id) {
        return repository.getOne(id);
    }

    /**
     * initialize a transaction with the repository - database to add an element to the database
     * @param user 
     */
    @Override
    @Transactional
    public void addUser(User user) {
        repository.save(user);
    }
    
    /**
     * initialize a transaction with the repository - database to add a list of elements to the database
     * @param users 
     */
    @Override
    @Transactional
    public void addUsers(List<User> users) {
        repository.save(users);
    }
    
    /**
     * initialize a transaction with the repository - database to remove an element from the database
     * @param user 
     */
    @Override
    @Transactional
    public void deleteUser(User user) {
        repository.delete(user);
    }
    
    /**
     * initialize a transaction with the repository - database to get all the elements from the database 
     * @return 
     */
    @Override
    @Transactional
    public List<User> getAllUsers() {
        return repository.findAll();
    }
    
    /**
     * initialize a transaction with the repository - database and checks a specific value with the appropriate 
     * element value in the database
     * @param username
     * @return 
     */
    @Override
    @Transactional
    public boolean checkUsername(String username) {
        for(User vLookUp:repository.findAll()) {
            if(vLookUp.getUsername().equalsIgnoreCase(username)){
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean validateUser(User user) {
        return validateUser(user.getUsername(),user.getPassword());
    }

    @Override
    @Transactional
    public boolean validateUser(String username, String password) {
        User u = repository.findByUsernameAndPassword(username, password);
        return u != null;
    }

    @Override
    public long getCount() {
        return repository.count();
    }

    @Override
    public long getNextID() {
        return repository.count() + 1L ;
    }
    
    
}
