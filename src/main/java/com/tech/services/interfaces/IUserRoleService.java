/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.services.interfaces;

import com.tech.models.entities.user.UserRole;
import java.util.List;

/**
 *
 * @author KuroiTenshi
 */
public interface IUserRoleService {
    void addUserRole(UserRole userRole);

    List<UserRole> getAllUserRoles();

    public String getRoleByUserID(Long userid);

    public List<UserRole> getUserRolesByRoles(String role);
    
    public void modifyUserRole(UserRole newRole);
    
    public void deleteUserRole(UserRole userRole);
    
}
