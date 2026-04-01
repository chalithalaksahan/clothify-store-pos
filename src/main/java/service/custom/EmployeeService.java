package service.custom;

import model.Employee;
import model.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {
    String getEmployeeId();
    boolean addEmployee(Employee employee);
    boolean updateEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee searchEmployee(String id) throws SQLException;
    boolean deleteEmployee(String id);
}
