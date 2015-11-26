/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.daos;

import java.util.List;

import com.tech.models.daos.interfaces.IDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author KuroiTenshi
 */
public class ImagesDAOimpl implements IDAO {
    private SessionFactory sessionFactory;
    
    /**
     * creates the hibernate SessionFactory to initialize transactions
     * @param sessionFactory 
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void save(IDAO imagesDAO) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(imagesDAO);
        tx.commit();
        session.close(); 
    }

    @Override
    public List<IDAO> list() {
        Session session = this.sessionFactory.openSession();
        List<IDAO> imagesList = session.createQuery("from images").list();//takes a list of Image IDAOs from the session
        session.close();
        return imagesList;
    }
    
}
