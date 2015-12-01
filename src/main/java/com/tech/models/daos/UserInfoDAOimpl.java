/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.daos;

import com.tech.models.daos.interfaces.IDAO;
import com.tech.models.daos.interfaces.UserInfoDAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author iwann
 */
public class UserInfoDAOimpl implements UserInfoDAO{
      private SessionFactory sessionFactory;
      
      public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
      
      
    @Override
    public void save(UserInfoDAO userInfoDAO) {
        Session session = this.sessionFactory.openSession();//Initialize the Session 
        Transaction tx = session.beginTransaction();//Starts a transaction with the database to add data (using the Created Session)
        session.persist(userInfoDAO);//adds the DAO in the session
        tx.commit(); // Commits the changes with the Transaction in the database
        session.close(); //Close the session
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<UserInfoDAO> list() {
        Session session = this.sessionFactory.openSession();
        List<UserInfoDAO> userList = session.createQuery("from user_info").list();//takes a list of User IDAOs from the session
        session.close();
        return userList;
    }
    
}
