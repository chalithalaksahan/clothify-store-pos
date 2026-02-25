package repository.custom.impl;

import model.UserCredential;
import org.hibernate.Session;
import repository.custom.UserCredentialRepository;
import util.HibernateUtil;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserCredentialRepositoryImpl implements UserCredentialRepository {

    private static final Logger LOGGER = Logger.getLogger(UserCredentialRepositoryImpl.class.getName());

    @Override
    public Optional<UserCredential> findByEmail(String email) {
        System.out.println("User Repo impl");
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        // Use try-with-resources to ensure the session is closed and use the entity class name in HQL
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM UserCredential uc JOIN FETCH uc.user WHERE uc.email = :email";

            UserCredential userCredential = session.createQuery(hql, UserCredential.class)
                    .setParameter("email", email)
                    .uniqueResult();

            return Optional.ofNullable(userCredential);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to find UserCredential by email", e);
            return Optional.empty();
        }

    }

    @Override
    public void save(UserCredential uc) {
        if (uc == null) {
            return;
        }
        org.hibernate.Transaction tx = null;
        try (org.hibernate.Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.persist(uc);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

}
