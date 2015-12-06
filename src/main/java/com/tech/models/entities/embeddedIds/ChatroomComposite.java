package com.tech.models.entities.embeddedIds;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by KuroiTenshi on 12/6/2015.
 */
@Embeddable
public class ChatroomComposite implements Serializable{
    protected Long room_id;
    protected Long room_creator;
    protected Long room_member;

    public ChatroomComposite() {

    }

    public ChatroomComposite(Long room_id,Long room_creator, Long room_member){
        this.room_id = room_id;
        this.room_creator = room_creator;
        this.room_member = room_member;
    }

    @Override
    public int hashCode() {
        int result = 3;

        result = 89 * result + (int)(this.room_id ^ (this.room_id>>>32));
        result = 89 * result + (int)(this.room_creator ^ (this.room_creator>>>32));
        result = 89 * result + (int)(this.room_member ^ (this.room_member>>>32));

        return result;
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
        final ChatroomComposite other = (ChatroomComposite) obj;
        if (!this.room_id.equals(other.room_id)) {
            return false;
        }
        if (!this.room_creator.equals(other.room_creator)) {
            return false;
        }
        if (!this.room_member.equals(other.room_member)) {
            return false;
        }
        return true;
    }
}
