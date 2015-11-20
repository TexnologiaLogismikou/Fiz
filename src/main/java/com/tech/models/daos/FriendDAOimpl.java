/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.daos;

import com.tech.models.daos.interfaces.FriendDAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author milena
 */
public class FriendDAOimpl implements FriendDAO
{
    private SessionFactory sessionFactory;
    
    /**
     * creates the hibernate SessionFactory to initialize transactions
     * @param sessionFactory 
     */
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(FriendDAO friendDAO)
    {
        Session session = this.sessionFactory.openSession();//Initialize the Session 
        Transaction tx = session.beginTransaction();//Starts a transaction with the database to add data (using the Created Session)
        session.persist(friendDAO);//adds the DAO in the session
        tx.commit(); // Commits the changes with the Transaction in the database
        session.close(); //Close the session
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<FriendDAO> list()
    {
        Session session = this.sessionFactory.openSession();
        List<FriendDAO> friendList = session.createQuery("from friendlist").list();//takes a list of UserDAO's from the session
        session.close();
        return friendList;
    }
    
}
