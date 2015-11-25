package com.tech.models.daos;

import com.tech.models.daos.interfaces.IDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Aenaos on 25/11/2015.
 */
public class UserRolesDAOimpl implements IDAO {


    private SessionFactory sessionFactory;


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(IDAO userRolesDAO) {
        {
            Session session = this.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.persist(userRolesDAO);
            tx.commit();
            session.close();
        }

    }

    @Override
    public List<IDAO> list() {
        Session session = this.sessionFactory.openSession();
        List<IDAO> userRolesDAO = session.createQuery("from user_roles").list();//takes a list of Image IDAOs from the session
        session.close();
        return userRolesDAO;
    }
}
