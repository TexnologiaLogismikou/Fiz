/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import java.util.Objects;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author KuroiTenshi
 */
public class Pair  implements java.io.Serializable
{
    private final Boolean left;
    private final ResponseEntity right;

    public Pair(Boolean left, ResponseEntity right) {
        this.left = left;
        this.right = right;
    }

    static Pair of(Boolean left, ResponseEntity right){
        return new Pair(left, right);
    }
    
    public Boolean getBoolean(){
        return left;
    }
    
    public ResponseEntity getResponse(){
        return right;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.left.equals(pairo.getBoolean());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.left);
        return hash;
    }
}
