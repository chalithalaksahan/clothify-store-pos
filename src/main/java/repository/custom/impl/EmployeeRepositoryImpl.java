package repository.custom.impl;

import model.Employee;
import org.hibernate.Session;
import repository.custom.EmployeeRepository;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public Long getRowCount() {
        Session session = HibernateUtil.getSession();
        Long count = session.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).uniqueResult();
        session.close();
        return count;
    }

    @Override
    public boolean create(Employee employee) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.persist(employee);
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
    public List<Employee> getAll() {
        Session session = HibernateUtil.getSession();
        List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();
        session.close();
        return employees;
    }

    @Override
    public boolean update(Employee employee) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(employee);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Employee employee = session.find(Employee.class, id);
        if (employee == null) {
            session.getTransaction().rollback();
            session.close();
            return false;
        }
        session.remove(employee);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Employee getById(String id) throws SQLException {
        Session session = HibernateUtil.getSession();
        Employee employee = session.find(Employee.class, id);
        session.close();
        return employee;
    }
}
