package repository.custom.impl;

import Entity.UserCredential;
import org.hibernate.Session;
import repository.custom.LoginRepository;
import util.HibernateUtil;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginRepositoryImpl implements LoginRepository {
    private static final Logger LOGGER = Logger.getLogger(LoginRepositoryImpl.class.getName());

    @Override
    public Optional<UserCredential> findByEmail(String email) {
        System.out.println("User Repo impl");
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        // Use try-with-resources to ensure the session is closed and use the entity class name in HQL
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM UserCredential uc LEFT JOIN FETCH uc.user WHERE uc.email = :email";

            UserCredential userCredential = session.createQuery(hql, UserCredential.class)
                    .setParameter("email", email)
                    .uniqueResult();

            return Optional.ofNullable(userCredential);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to find UserCredential by email", e);
            return Optional.empty();
        }

    }


}
