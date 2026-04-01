package service.custom;

import dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {
    boolean addEmployee(EmployeeDTO employeeDTO);
    boolean updateEmployee(EmployeeDTO employeeDTO);
    List<EmployeeDTO> getAllEmployees() throws SQLException;
    EmployeeDTO searchEmployee(String id) throws SQLException;
    boolean deleteEmployee(String id);
    String getEmployeeId();
}