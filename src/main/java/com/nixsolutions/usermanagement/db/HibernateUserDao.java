package com.nixsolutions.usermanagement.db;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.nixsolutions.usermanagement.User;

public class HibernateUserDao implements UserDao{
    public SessionFactory getSessionFactory() {
                return HibernateUtils.getSessionFactory();
    }

    public User create(User user) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(user);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw new DatabaseException(e);
        }
        return user;
    }

    public void update(User user) throws DatabaseException {
        Session session = null;
        try {
            session = getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void delete(User user) throws DatabaseException {
        Session session = null;
        try{
            session = getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public User find(Long id) throws DatabaseException {
        Session session = null;
        User user = null;
        try {
            session = getSessionFactory().getCurrentSession();
            user =  (User) session.load(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    public Collection findAll() throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List list = null;
        try {
            list = session.createCriteria(User.class).list();
        } catch (Exception e) {
            t.rollback();
            throw new DatabaseException(e);
        }
        return list;
    }

    public Collection find(String firstName, String lastName) throws DatabaseException {
        Session session = getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List list = null;
        try {
            list = session.createCriteria(User.class).list();
        }catch (Exception e){
            t.rollback();
            throw new DatabaseException(e);
        }
        return list;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }


}
