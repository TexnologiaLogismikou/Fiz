package com.tech.models.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Aenaos on 25/11/2015.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "UserRole.findByUserID", query = "SELECT p FROM UserRole p WHERE p.user_role_userid = ?1"),
        @NamedQuery(name = "UserRole.findByRole", query = "SELECT p FROM UserRole p WHERE p.user_role_role = ?1")
})
@Table(name = "user_roles")
public class UserRole implements Serializable {
    @Id
    @Column(name = "user_role_userid")
    private String user_role_userid;

    @Column(name = "user_role_role")
    private String user_role_role;

    public UserRole() {

    }

    public UserRole(String username, String role) {
        this.user_role_userid = username;
        this.user_role_role = role;
    }

    public String getUsername() {
        return user_role_userid;
    }

    public void setUsername(String username) {
        this.user_role_userid = username;
    }

    public String getRole() {
        return user_role_role;
    }

    public void setRole(String role) {
        this.user_role_role = role;
    }
}