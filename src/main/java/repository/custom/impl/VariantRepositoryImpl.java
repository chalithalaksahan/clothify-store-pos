package repository.custom.impl;

import Entity.Variant;
import org.hibernate.Session;
import repository.custom.VariantRepository;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class VariantRepositoryImpl implements VariantRepository {
    @Override
    public boolean create(Variant variant) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.persist(variant);
            session.getTransaction().commit();
            return true;
        }catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Variant variant) {
       Session session = HibernateUtil.getSession();
       try {
           session.beginTransaction();
           session.merge(variant);
           session.getTransaction().commit();
           return true;
       }catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           e.printStackTrace();
           return false;
       } finally {
           session.close();
       }
    }

    @Override
    public boolean deleteById(String id) {
       return false;
    }

    @Override
    public Variant getById(String s) throws SQLException {
        return null;
    }

    @Override
    public List<Variant> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Variant findBySku(String skuCode) {
        Session session = HibernateUtil.getSession();
        try {
            return session.createQuery("FROM Variant WHERE sku = :skuCode", Variant.class)
                    .setParameter("skuCode", skuCode)
                    .uniqueResult();
        } finally {
            session.close();
        }
    }
}
