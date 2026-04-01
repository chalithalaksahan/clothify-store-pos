package repository.custom.impl;

import model.User;
import model.UserCredential;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.EmployeeRepository;
import util.HibernateUtil;

import java.sql.SQLException;
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
        Transaction transaction=null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            String hql = "UPDATE User u SET u.contactNo = :contactNo, " +
                    "u.firstName = :firstName, " +
                    "u.lastName = :lastName, " +
                    "u.hireDate = :hireDate, " +
                    "u.isActive = :isActive, " +
                    "u.salary = :salary, " +
                    "u.userRole = :userRole " + // <-- Removed the trailing comma here
                    "WHERE u.userId = :userId";

            int updatedEntities = session.createQuery(hql) // In newer Hibernate versions, you might need session.createMutationQuery(hql)
                    .setParameter("contactNo", user.getContactNo())
                    .setParameter("firstName", user.getFirstName())
                    .setParameter("lastName", user.getLastName())
                    .setParameter("hireDate", user.getHireDate())
                    .setParameter("isActive", user.isActive())
                    .setParameter("salary", user.getSalary())
                    .setParameter("userRole", user.getUserRole())
                    .setParameter("userId", user.getUserId())
                    .executeUpdate();

            transaction.commit();

            // Returns true if 1 or more rows were successfully updated
            return updatedEntities > 0;

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
    public User getById(String id) throws SQLException {
        Session session = HibernateUtil.getSession();
        User user = session.find(User.class, id);
        session.close();
        return user;
    }
}