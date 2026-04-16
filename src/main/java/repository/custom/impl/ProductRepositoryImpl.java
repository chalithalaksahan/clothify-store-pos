package repository.custom.impl;

import Entity.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.ProductRepository;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public boolean create(Product product) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.persist(product);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback(); // Undo the changes if it crashes
        }
        e.printStackTrace();
        return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    @Override
    public Product getById(String s) throws SQLException {
        return null;
    }

    @Override
    public List<Product> getAll() throws SQLException {
        return List.of();
    }
}
