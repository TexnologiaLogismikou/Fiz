/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.models.daos;

import com.tech.models.daos.interfaces.IDAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author KuroiTenshi
 */
public class ChatroomPrivilegesDAOimpl implements IDAO{
    
    private SessionFactory sessionFactory;


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(IDAO DAO) {
        {
            Session session = this.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.persist(DAO);
            tx.commit();
            session.close();
        }

    }

    @Override
    public List<IDAO> list() {
        Session session = this.sessionFactory.openSession();
        List<IDAO> userRolesDAO = session.createQuery("from chatroom_privileges").list();//takes a list of Image IDAOs from the session
        session.close();
        return userRolesDAO;
    }
}