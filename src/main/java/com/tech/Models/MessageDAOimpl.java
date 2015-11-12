package com.tech.Models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MessageDAOimpl implements MessageDAO{
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(MessageDAO messageDAO) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(messageDAO);
        tx.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MessageDAO> list() {
        Session session = this.sessionFactory.openSession();
        List<MessageDAO> messageList = session.createQuery("from message").list();
        session.close();
        return messageList;
    }
}
