/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities;

import com.tech.models.entities.embeddedIds.FriendComposite;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author milena
 */
@Entity
@IdClass(FriendComposite.class)
@NamedQuery(name = "Friend.findByUserid", query = "SELECT p FROM Friend p WHERE p.userid = ?1")//edw vazw oti query thelw
@Table (name = "friendlist")
public class Friend implements Serializable
{
    @Id
    private Long userid;
     
    @Id
    private Long friendid;
    
    public Friend() {}

    public Friend(Long userid, Long friendid)
    {
        this.userid = userid;
        this.friendid = friendid;
    }

    @JsonProperty
    public Long getUserid()
    {
        return userid;
    }
    
    @JsonProperty
    public Long getFriendid()
    {
       return friendid;
    }

    public void setUserid(Long userid)
    {
       this.userid = userid;
    }

    public void setFriendid(Long friendid)
    {
       this.friendid = friendid;
    }
  
}
