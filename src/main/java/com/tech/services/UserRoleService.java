package com.tech.services;

import com.tech.models.entities.UserRole;
import com.tech.repositories.IUserRolesRepository;
import com.tech.services.interfaces.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aenaos on 25/11/2015.
 */
@Service
public class UserRoleService implements IUserRoleService{
    @Autowired
    private IUserRolesRepository repository;

    @Override
    public void addUserRole(UserRole userRole) {
        repository.save(userRole);
    }
    
    @Override
    public void deleteUserRole(UserRole userRole) 
    {
        repository.delete(userRole);
    }

    @Override
    public List<UserRole> getAllUserRoles() {
        return repository.findAll();
    }

    /**
     * Preferably don't return the whole userRole model. this value is not something that 
     * should be modified easy
     * @param userid
     * @return Returns user role as string
     */
    @Override
    public String getRoleByUserID(Long userid) {
        return repository.findByUserID(userid).getRole();
    }

    /**
     * Something like an admin command. "Lets see all the users with that role"
     * @param role
     * @return Returns all the users roles which their role match the param
     */
    @Override
    public List<UserRole> getUserRolesByRoles(String role) {
        return repository.findByRole(role);
    }

    @Override
    public void modifyUserRole(UserRole newRole) {
        repository.setUserRoleById(newRole.getRole(),newRole.getUserID());
    }

}
