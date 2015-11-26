/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.UserRole;
import java.util.List;

/**
 *
 * @author KuroiTenshi
 */
public interface IUserRoleService {

    void addUserRole(UserRole userRole);

    void addUserRoles(List<UserRole> userRoles);

    void deleteUserRole(UserRole userRole);

    List<UserRole> getAllUserRoles();

    List<UserRole> getRoleByUsername(String username);

    List<UserRole> getUsernameByRole(String role);
    
}
