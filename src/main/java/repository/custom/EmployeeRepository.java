package repository.custom;

import model.Employee;
import repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    Long getRowCount();

}
