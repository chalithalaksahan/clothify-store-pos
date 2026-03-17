package service.custom.impl;

import jakarta.inject.Inject;
import repository.custom.EmployeeRepository;
import service.custom.EmployeeService;

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
}
