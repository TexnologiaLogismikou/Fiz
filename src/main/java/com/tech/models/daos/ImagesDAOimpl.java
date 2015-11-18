/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.daos;

import java.util.List;

import com.tech.models.daos.interfaces.ImagesDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author KuroiTenshi
 */
public class ImagesDAOimpl implements ImagesDAO {
    private SessionFactory sessionFactory;
    
    /**
     * creates the hibernate SessionFactory to initialize transactions
     * @param sessionFactory 
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void save(ImagesDAO imagesDAO) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(imagesDAO);
        tx.commit();
        session.close(); 
    }

    @Override
    public List<ImagesDAO> list() {
        Session session = this.sessionFactory.openSession();
        List<ImagesDAO> imagesList = session.createQuery("from images").list();//takes a list of UserDAO's from the session
        session.close();
        return imagesList;
    }
    
}
