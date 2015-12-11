package com.tech.repositories;

import com.tech.models.entities.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Aenaos on 25/11/2015.
 */

@Repository
public interface IUserRolesRepository extends JpaRepository<UserRole, Long> {
    UserRole findByUserID(Long userid);
    List<UserRole> findByRole(String role);
    @Modifying
    @Query("update UserRole u set u.user_role_role = ?1 where u.user_role_userid = ?2")
    void setUserRoleById(String role,Long userId);
}
