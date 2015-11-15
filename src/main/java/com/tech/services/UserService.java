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
                return true;
            }
        }
        return false;
    }

    /**
     * calls the overloaded function
     * @param user
     * @return 
     */
    @Override
    @Transactional
    public boolean validateUser(User user) {
        return validateUser(user.getUsername(),user.getPassword());
    }

    /**
     * validates the users existance with a query on the repository
     * @param username
     * @param password
     * @return 
     */
    @Override
    @Transactional
    public boolean validateUser(String username, String password) {
        User u = repository.findByUsernameAndPassword(username, password);
        return u != null;
    }

    /**
     * counts the records in the repository and returns the exact number
     * @return 
     */
    @Override
    @Transactional
    public long getCount() {
        return repository.count();
    }

    /**
     * counts the records in the repository and returns the number increased by 1 . 
     * that number is a "free slot" for a new user
     * @return 
     */
    @Override
    @Transactional
    public long getNextID() {
        return getCount() + 1L ;
    }
    
    @Override    
    @Transactional
    public User getLastRecord(){
        throw new UnsupportedOperationException("Not working yet");
    }
    
}
