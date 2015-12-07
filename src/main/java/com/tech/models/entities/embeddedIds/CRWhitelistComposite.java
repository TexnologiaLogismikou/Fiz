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
public class CRWhitelistComposite implements Serializable {
    protected Long room_id; 
    protected Long room_member;

    public CRWhitelistComposite() {
    }

    public CRWhitelistComposite(Long room_id, Long room_member) {
        this.room_id = room_id;
        this.room_member = room_member;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.room_id);
        hash = 29 * hash + Objects.hashCode(this.room_member);
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
        final CRWhitelistComposite other = (CRWhitelistComposite) obj;
        if (!Objects.equals(this.room_id, other.room_id)) {
            return false;
        }
        if (!Objects.equals(this.room_member, other.room_member)) {
            return false;
        }
        return true;
    }
    
}
