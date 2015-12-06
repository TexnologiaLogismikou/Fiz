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
public class ChatroomEntitiesComposite implements Serializable {
    protected Long room_id; 
    protected Long room_creator;
    
    public ChatroomEntitiesComposite() {
    }    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.room_id);
        hash = 79 * hash + Objects.hashCode(this.room_creator);
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
        final ChatroomEntitiesComposite other = (ChatroomEntitiesComposite) obj;
        if (!Objects.equals(this.room_id, other.room_id)) {
            return false;
        }
        if (!Objects.equals(this.room_creator, other.room_creator)) {
            return false;
        }
        return true;
    }
}
