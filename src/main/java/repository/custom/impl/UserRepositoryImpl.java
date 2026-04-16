package repository.custom.impl;

import Entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.UserRepository;
import util.HibernateUtil;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(User user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

