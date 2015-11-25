package com.tech.repositories;

import com.tech.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Aenaos on 25/11/2015.
 */

@Repository
public interface IUserRolesRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUsername(String username);
    List<UserRole> findByRole(String role);
}
