package com.tech.models.daos;

import com.tech.models.daos.interfaces.ChatroomDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by KuroiTenshi on 12/6/2015.
 */
public class ChatroomDAOimp implements ChatroomDAO {
    private SessionFactory sessionFactory;
    /**
     * creates the hibernate SessionFactory to initialize transactions
     *
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(ChatroomDAO chatroomDAO) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(chatroomDAO);
        tx.commit();
        session.close();
    }

    @Override
    public List<ChatroomDAO> list() {
        Session session = this.sessionFactory.openSession();
        List<ChatroomDAO> chatroomList = session.createQuery("from chatrooms").list();
        session.close();
        return chatroomList;
    }
}
