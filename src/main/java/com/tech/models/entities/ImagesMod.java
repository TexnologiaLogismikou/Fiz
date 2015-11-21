/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities;

import com.tech.models.entities.embeddedIds.ImageComposite;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author KuroiTenshi
 */
@Entity
@IdClass(ImageComposite.class)
@NamedQuery(name = "ImagesMod.findByUserid", query = "SELECT p FROM ImagesMod p WHERE p.userid = ?1")//edw vazw oti query thelw
@Table(name = "images")
public class ImagesMod {
    
    @Id 
    private Long userid;

    @Id
    private Timestamp tmstamp;
       
    @Column(name = "images")
    private byte[] images;  
   
    
    public ImagesMod() {
        
    }
    
    public ImagesMod(Long userid,Timestamp tmstamp,byte[] data) {
        this.userid = userid;
        this.tmstamp = tmstamp;
        this.images = data;
    }
    
    public long getUserID(){
        return userid;
    }
    
    public Timestamp getTimestamp(){
        return tmstamp;
    }
    
    public byte[] getImages(){
        return images;
    }
    
    public void setUserid(long userid){
        this.userid = userid;
    }
    
    public void setName(Timestamp tmstamp){
        this.tmstamp = tmstamp;
    }
    
    public void setImages(byte[] data){
        this.images = data;
    }
    
}
