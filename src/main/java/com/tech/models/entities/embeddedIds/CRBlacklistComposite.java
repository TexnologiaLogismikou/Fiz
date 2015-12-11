/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.entities.embeddedIds;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author KuroiTenshi
 */
@Embeddable
public class CRBlacklistComposite implements Serializable {
    protected Long room_id; 
    protected Long room_member;

    public CRBlacklistComposite() {
    }

    public CRBlacklistComposite(Long room_id, Long room_member) {
        this.room_id = room_id;
        this.room_member = room_member;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.room_id);
        hash = 59 * hash + Objects.hashCode(this.room_member);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CRBlacklistComposite other = (CRBlacklistComposite) obj;
        if (!Objects.equals(this.room_id, other.room_id)) {
            return false;
        }
        if (!Objects.equals(this.room_member, other.room_member)) {
            return false;
        }
        return true;
    }
    
}
