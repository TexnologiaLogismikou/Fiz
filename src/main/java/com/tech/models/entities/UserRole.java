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
    private Long user_role_userid;
    //TODO : Ama bgalo ta setters, ta primary keys den tha allazoyn. Tha trexei to programma?
    @Column(name = "user_role_role")
    private String user_role_role;

    public UserRole() {

    }

    public UserRole(Long id, String role) {
        this.user_role_userid = id;
        this.user_role_role = role;
    }

    public Long getUserID() {
        return user_role_userid;
    }

    public void setUsername(Long username) {
        this.user_role_userid = username;
    }

    public String getRole() {
        return user_role_role;
    }

    public void setRole(String role) {
        this.user_role_role = role;
    }
}