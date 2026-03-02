package repository.custom.impl;

import model.UserCredential;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.UserRepository;
import util.HibernateUtil;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(UserCredential uc) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            // Persist the User first so it gets an ID, then persist the credential
            if (uc.getUser() != null) {
                session.persist(uc.getUser());
            }
            session.persist(uc);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}

