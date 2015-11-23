package com.tech.repositories;

import com.tech.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Connect to database with Spring JPA database connection
 * -auto-
 * @author KuroiTenshi
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username,String password);
    User findByUsername(String username);
    User findByUserid(Long id);
}
