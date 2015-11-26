/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities.embeddedIds;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable //tou vazei mia klasi mesa
public class FriendComposite implements Serializable
{
    protected Long userid; //gia na mi vazoume getters kai setters..
    protected Long friendid;
  
  public FriendComposite()
  {
      
  }
  
  public FriendComposite(Long userid,Long friendid) {
      this.userid = userid;
      this.friendid = friendid;
  }
   @Override
    public boolean equals(Object obj) {
        if(obj instanceof FriendComposite){
            FriendComposite friendPK = (FriendComposite) obj;
 
            if(!friendPK.userid.equals(userid)){
                return false;
            }
 
            if(!friendPK.friendid.equals(friendid)){
                return false;
            }
 
            return true;
        }
 
        return false;
    }
    
    @Override
    public int hashCode() 
    {
       int result = (int)(userid ^ ( userid>>>32));
       
       result = 31 * result + (int)(friendid ^ (friendid>>>32));
       
        return result;
    }
 
}
