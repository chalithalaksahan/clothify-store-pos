package util;

import model.User;
import model.UserCredential;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory session = createSessionFactory();

    private static  SessionFactory createSessionFactory(){
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
               builder.configure("hibernate.cfg.xml");
               builder.build();

        System.out.println("✅ Hibernate connected successfully!");


        Metadata metadataSource = new MetadataSources()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(UserCredential.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metadataSource.getSessionFactoryBuilder().build();

    }

    public static Session getSession(){
        return session.openSession();
    }

    public static void shutdown() {
        if (session != null) session.close();
    }
}