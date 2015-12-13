package com.tech.models.entities.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech.models.dtos.user.RegisteredUserDTO;
import com.tech.models.dtos.user.UserDTO;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Model for database Table "Usersdata"
 * with separate fields for each database element
 * @author KuroiTenshi
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByUserid", query = "SELECT p FROM User p WHERE p.id = ?1"),
        @NamedQuery(name = "User.findByUsername", query = "SELECT p FROM User p WHERE p.username = ?1"),
        @NamedQuery(name = "User.findByUsernameAndPassword", query = "SELECT p FROM User p WHERE p.username = ?1 AND p.password = ?2")
})
@Table (name = "Usersdata")
public class User implements Serializable{

    @Id //id = primary key
    @Column(name = "id") //column that the variable belongs
    private Long id;

    @Column(name = "username") //column that the variable belongs
    private String username;

    @Column(name = "password") //column that the variable belongs
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "hasroom")
    private boolean hasRoom;
    
    public User() {
    }
    
    public User(Long id,UserDTO userDTO){
        this(id,userDTO.getUsername(),
                userDTO.getPassword(),userDTO.getEnabled(),userDTO.getHasRoom());
    }
    
    public User(Long id,RegisteredUserDTO userDTO) {
        this(id,userDTO.getUsername(),userDTO.getPassword(),true,false);
    }
    
    public User(Long id, String username, String password, boolean enabled) {
        this(id,username,password,enabled,false);
    }
    
    public User(Long id, String username, String password, boolean enabled,boolean hasRoom) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.hasRoom = hasRoom;
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

    @JsonProperty
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isHasRoom() {
        return hasRoom;
    }

    public void setHasRoom(boolean hasRoom) {
        this.hasRoom = hasRoom;
    }

    @Override
    public String toString(){
        return " id : " + this.id + " username : " + this.username + " password : " 
                + this.password + " isEnabled : " + this.enabled + " hasRoom : " + this.hasRoom;
    }
}
