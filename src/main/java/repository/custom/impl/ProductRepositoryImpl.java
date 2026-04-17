package repository.custom.impl;

import Entity.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
            session.flush();
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
            Product product = session.find(Product.class, id);
            if (product == null) {
                return false;
            }
            session.remove(product);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Product getById(String id) throws SQLException {
        Session session = HibernateUtil.getSession();
        try {
            return session.find(Product.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Product> getAll() throws SQLException {
        Session session = HibernateUtil.getSession();
            String hql = "SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.variants";
            return session.createQuery(hql, Product.class).list();

    }

    @Override
    public List<Product> universalSearch(String search) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT DISTINCT p FROM Product p " +
                    "JOIN FETCH p.variants v " +
                    "JOIN FETCH v.inventory i " +
                    "LEFT JOIN FETCH p.category c " +
                    "WHERE v.sku LIKE :search " +
                    "OR p.name LIKE :search " +
                    "OR c.categoryName LIKE :search";

            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("search", "%" + search + "%");

            return query.list();
        }
    }
}
