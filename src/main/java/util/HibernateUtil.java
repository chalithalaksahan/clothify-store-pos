package util;

import Entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory session = createSessionFactory();

    private static  SessionFactory createSessionFactory(){
        StandardServiceRegistry builder = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        System.out.println("✅ Hibernate connected successfully!");

        Metadata metadataSource = new MetadataSources(builder)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(UserCredential.class)
                .addAnnotatedClass(Supplier.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Variant.class)
                .addAnnotatedClass(Location.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderItem.class)
                .addAnnotatedClass(InventoryTransaction.class)
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