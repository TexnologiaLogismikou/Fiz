package com.tech.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Model for database Table "Usersdata"
 * with separate fields for each database element
 * @author KuroiTenshi
 */
@Entity
@NamedQuery(name = "User.findByUserid", query = "SELECT p FROM User p WHERE p.id = ?1")
@Table (name = "Usersdata")
public class User {
    
    @Id //id = primary key
    @Column(name = "id") //column that the variable belongs
    private Long id;

    @Column(name = "username") //column that the variable belongs
    private String username;

    @Column(name = "password") //column that the variable belongs
    private String password;

    public User() {}

    public User(Long id, String username, String password) {

        this.id = id;
        this.username = username;
        this.password = password;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return " id : " + this.id + " username : " + this.username + " password : " + this.password;
    }
}
