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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author KuroiTenshi
 */
@Entity
@IdClass(ImageComposite.class)
@NamedQueries({
    @NamedQuery(name = "ImagesMod.findByUserid", query = "SELECT p FROM ImagesMod p WHERE p.userid = ?1"),
    @NamedQuery(name = "ImagesMod.findByHashtag",query = "SELECT p FROM ImagesMod p WHERE p.hashtag = ?1")
})
@Table(name = "images")
public class ImagesMod {
    
    @Id 
    @NotNull
    @Min(1)
    private Long userid;

    @Id
    @NotNull
    private Timestamp tmstamp;
       
    @Column(name = "images")
    @NotNull
    private byte[] images;  
   
    @Column(name = "hashtag")
    @NotNull
    private long hashtag;
    
    public ImagesMod() {
        
    }
    
    public ImagesMod(Long userid,Timestamp tmstamp,byte[] data,int hashtag) {
        this.userid = userid;
        this.tmstamp = tmstamp;
        this.images = data;
        this.hashtag = hashtag;
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
    
    public long getHashtag(){
        return hashtag;
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
    
    public void setHashtag(long hashtag){
        this.hashtag = hashtag;
    }
    
}
