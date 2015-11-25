package com.tech.models.daos;

import com.tech.models.daos.interfaces.IDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class MessageDAOImpl implements IDAO {
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(IDAO messageDAO) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(messageDAO);
        tx.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IDAO> list() {
        Session session = this.sessionFactory.openSession();
        List<IDAO> messageList = session.createQuery("from message").list();
        session.close();
        return messageList;
    }
}
