package repository.custom.impl;

import Entity.Category;
// ...existing imports...
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.CategoryRepository;
import util.HibernateUtil;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public boolean create(Category category) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.persist(category);
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
    public boolean update(Category category) {
       Session session = HibernateUtil.getSession();
       try {
           session.beginTransaction();
           session.merge(category);
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
    public boolean deleteById(String id) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Query<Category> query = session.createQuery("FROM Category c WHERE c.categoryCode = :code", Category.class);
            query.setParameter("code", id);
            Category category = query.uniqueResult();

            if (category == null){
                return false;
            }
            session.remove(category);
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
    public Category getById(String id) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Category> query = session.createQuery(
                    "FROM Category c WHERE c.categoryCode = :code", Category.class
            );
            query.setParameter("code", id);
            return  query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Category> getAll() {
        Session session = HibernateUtil.getSession();
        try {
            return session.createQuery("FROM Category", Category.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
