package com.tech.Models;

import javax.persistence.*;

/**
 * Model for database Table "Usersdata"
 * with separate fields for each database element
 * @author KuroiTenshi
 */
@Entity
@Table (name = "Usersdata")
public class User {
    
    @Id //id = primary key
    @Column(name = "id") //column that the variable belongs
    private Long id;

    @Column(name = "username") //column that the variable belongs
    private String username;

    @Column(name = "password") //column that the variable belongs
    private String password;

    protected User() {}

    public User(Long id, String username, String password) {

        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
