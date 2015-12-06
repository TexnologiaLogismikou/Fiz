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
    public List<UserRole> getAllUserRoles() {
        return repository.findAll();
    }

    @Override
    public List<UserRole> getRoleByUserID(Long userid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserRole> getUserRolesByRoles(String role) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
