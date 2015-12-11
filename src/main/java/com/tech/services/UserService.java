package com.tech.services;

import com.tech.services.interfaces.IUserService;
import com.tech.models.entities.User;
import com.tech.repositories.IUserRepository;
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
        return repository.findByUserid(id);
    }

    /**
     * initialize a transaction with the repository - database to get an element
     * with its username
     * @param username
     * @return
     */
    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return repository.findByUsername(username);
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
     * initialize a transaction with the repository - database to get all the elements from the database 
     * @return List\<Use\>
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
        User user = repository.findByUsername(username);
        return user != null;
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
     * validates the users existence with a query on the repository
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
        Long i = getCount();
        Long x = repository.getOne(i).getId();
        return x + 1L ;
    }
    
    @Override    
    @Transactional
    public User getLastRecord(){
        Long i = getCount();
        return repository.getOne(i);
    }

    @Override
    @Transactional
    public void modifyUser(User modifiedUser) {
        repository.setUserInfoById(modifiedUser.getUsername(), modifiedUser.getPassword(),
                modifiedUser.isEnabled(), modifiedUser.getId());  
    }

    
}
