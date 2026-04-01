package service.custom.impl;

import jakarta.inject.Inject;
import model.Employee;
import repository.custom.EmployeeRepository;
import service.custom.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    EmployeeRepository repositoryType;


    @Override
    public String getEmployeeId() {
        Long rowCount = repositoryType.getRowCount();
        if (rowCount == null || rowCount == 0) {
            return "EMP-0001";
        } else {
            int newIdNum = rowCount.intValue()+1 ;
            return String.format("EMP-%04d", newIdNum);
        }
    }

    @Override
    public boolean addEmployee(Employee employee) {
        return repositoryType.create(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repositoryType.getAll();
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return repositoryType.update(employee);
    }

    @Override
    public boolean deleteEmployee(String id) {
        return repositoryType.delete(id);
    }
}
