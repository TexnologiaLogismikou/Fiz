package com.tech.Models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOimpl implements UserDAO{
    private SessionFactory sessionFactory;
    
    /**
     * creates the hibernate SessionFactory to initialize transactions
     * @param sessionFactory 
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Creates a Session from the Session Factory and commits changes to the database
     * @param userDAO 
     */
    @Override
    public void save(UserDAO userDAO) {
        Session session = this.sessionFactory.openSession();//Initialize the Session 
        Transaction tx = session.beginTransaction();//Starts a transaction with the database to add data (using the Created Session)
        session.persist(userDAO);//adds the DAO in the session
        tx.commit(); // Commits the changes with the Transaction in the database
        session.close(); //Close the session
    }

    /**
     * Creates a query for session to make it accessible
     * @return 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserDAO> list() {
        Session session = this.sessionFactory.openSession();
        List<UserDAO> userList = session.createQuery("from user").list();//takes a list of UserDAO's from the session
        session.close();
        return userList;
    }
}


