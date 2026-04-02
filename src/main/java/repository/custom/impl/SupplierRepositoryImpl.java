package repository.custom.impl;

import model.Supplier;
import org.hibernate.Session;
import repository.custom.SupplierRepository;
import util.HibernateUtil;

import java.util.List;

public class SupplierRepositoryImpl implements SupplierRepository {


    @Override
    public boolean create(Supplier supplier) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.persist(supplier);
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
    public boolean update(Supplier supplier) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(supplier);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Supplier supplier = session.find(Supplier.class, id);
        if (supplier == null) {
            session.getTransaction().rollback();
            session.close();
            return false;
        }
        session.remove(supplier);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Supplier getById(String id) {
        Session session = HibernateUtil.getSession();
        Supplier supplier = session.find(Supplier.class, id);
        session.close();
        return supplier;
    }

    @Override
    public List<Supplier> getAll()  {
        Session session = HibernateUtil.getSession();
        List<Supplier> suppliers = session.createQuery("FROM Supplier", Supplier.class).list();
        session.close();
        return suppliers;
    }

    @Override
    public Long getRowCount() {
       Session session = HibernateUtil.getSession();
       Long count = session.createQuery("SELECT COUNT(s) FROM Supplier s", Long.class).uniqueResult();
       session.close();
       return count;
    }
}
