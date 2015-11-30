package com.tech.repositories;

import com.tech.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    User findByUsername(String username);
    User findByUserid(Long id);
    @Modifying
    @Query("update User u set u.username = ?1, u.password = ?2, u.enabled = ?3 where u.id = ?4")
    void setUserInfoById(String username, String password, boolean enabled, Long userId);
}
