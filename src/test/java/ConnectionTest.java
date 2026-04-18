import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectionTest {

    @Test
    public void testDatabaseConnection() {
        try {
            // 1. Arrange: Load your Hibernate config
            Configuration config = new Configuration().configure("hibernate.cfg.xml");

            // 2. Act: Try to build the Factory and open a session
            SessionFactory sessionFactory = config.buildSessionFactory();
            Session session = sessionFactory.openSession();

            // 3. Assert: Check if the session is actually open
            assertNotNull(session, "The database session should not be null!");
            assertTrue(session.isOpen(), "The database session should be open!");

            System.out.println("✅ Connection Success: Database is connected!");

            // Cleanup
            session.close();
            sessionFactory.close();

        } catch (Exception e) {
            // If anything goes wrong, the test fails
            e.printStackTrace();
            fail("❌ Connection Failed: Check your URL, Username, or Password!");
        }
    }
}