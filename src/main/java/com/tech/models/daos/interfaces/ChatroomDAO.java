package com.tech.models.daos.interfaces;

import java.util.List;

/**
 * Created by KuroiTenshi on 12/6/2015.
 */
public interface ChatroomDAO {
    List<ChatroomDAO> list();

    void save(ChatroomDAO chatroomDAO);
}
