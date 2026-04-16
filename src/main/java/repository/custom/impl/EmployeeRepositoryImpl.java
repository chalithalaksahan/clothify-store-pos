package repository.custom.impl;

import Entity.Supplier;
import Entity.User;
import Entity.UserCredential;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.EmployeeRepository;
import util.HibernateUtil;

import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Override
    public Long getRowCount() {
        Session session = HibernateUtil.getSession();
        Long count = session.createQuery("SELECT COUNT(e) FROM User e", Long.class).uniqueResult();
        session.close();
        return count;
    }

    @Override
    public boolean create(User user) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            // persist user
            session.persist(user);
            // persist credential explicitly to ensure it's stored (credentials own the FK)
            UserCredential cred = user.getUserCredential();
            if (cred != null) {
                // make sure the credential references the user
                cred.setUser(user);
                session.persist(cred);
            }
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
       Session session = HibernateUtil.getSession();
        List<User> users = session.createQuery("FROM User", User.class).list();
        session.close();
        return users;
    }

    @Override
    public boolean update(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession();){
           transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            User user = session.find(User.class, id);
            if (user == null) {
                session.getTransaction().rollback();
                return false;
            }
            session.remove(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public User getById(String id) {
      Session session = HibernateUtil.getSession();
        try {
            return session.find(User.class, id);
        } finally {
            session.close();
        }
    }
}