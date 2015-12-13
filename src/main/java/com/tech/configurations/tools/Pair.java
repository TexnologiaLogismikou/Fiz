/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.configurations.tools;

import java.util.Objects;

/**
 *
 * @author KuroiTenshi
 */
public class Pair<L,R>  implements java.io.Serializable
{
    private final L left;
    private final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L,R>  Pair<L,R>  of(L left, R right){
        return new Pair(left, right);
    }
    
    public L getLeft(){
        return left;
    }
    
    public R getRight(){
        return right;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.left.equals(pairo.getLeft()) && this.right.equals((pairo.getRight()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.left);
        hash = 37 * hash + Objects.hashCode(this.right);
        return hash;
    }
}
