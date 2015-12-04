/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities.embeddedIds;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;

/**
 *
 * @author KuroiTenshi
 */
@Embeddable
public class ImageComposite implements Serializable{
     protected Long userid; //gia na mi vazoume getters kai setters..
    protected Date tmstamp;
  
  public ImageComposite()
  {
      
  }
  
  public ImageComposite(Long userid,Date tmstamp) {
      this.userid = userid;
      this.tmstamp = tmstamp;
  }
   @Override
    public boolean equals(Object obj) {
        if(obj instanceof ImageComposite){
            ImageComposite friendPK = (ImageComposite) obj;
 
            if(!friendPK.userid.equals(userid)){
                return false;
            }
 
            return friendPK.tmstamp.equals(tmstamp);
        }
 
        return false;
    }
    
    @Override
    public int hashCode() 
    {
       int result = (int)(userid ^ ( userid>>>32));
       
       result = 31 * result + tmstamp.hashCode();
       
        return result;
    }
 

}
