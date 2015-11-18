/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author KuroiTenshi
 */
@Entity
@Table(name = "images")
public class ImagesMod {
    
    @Id //id = primary key
    @Column(name = "id") //column that the variable belongs
    private Long id;

    @Column(name = "name") //column that the variable belongs
    private String name;
       
    @Column(name = "images")
    private byte[] images;  
   
    
    public ImagesMod() {
        
    }
    
    public ImagesMod(Long id,String name,byte[] data) {
        this.id = id;
        this.name = name;
        this.images = data;
    }
    
    public long getID(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public byte[] getImages(){
        return images;
    }
    
    public void setID(long id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setImages(byte[] name){
        this.images = name;
    }
    
}
