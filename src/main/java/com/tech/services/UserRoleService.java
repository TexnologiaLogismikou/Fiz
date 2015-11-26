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
    public List<UserRole> getRoleByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<UserRole> getUsernameByRole(String role) {
        return repository.findByRole(role);
    }

    @Override
    public void addUserRole(UserRole userRole) {
        repository.save(userRole);
    }

    @Override
    public void addUserRoles(List<UserRole> userRoles) {
        repository.save(userRoles);
    }

    @Override
    public void deleteUserRole(UserRole userRole) {
        repository.delete(userRole);
    }

    @Override
    public List<UserRole> getAllUserRoles() {
        return repository.findAll();
    }

}
