package com.tech.Repositories;

import com.tech.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Connect to database with Spring JPA database connection
 * -auto-
 * @author KuroiTenshi
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username,String password);
}
