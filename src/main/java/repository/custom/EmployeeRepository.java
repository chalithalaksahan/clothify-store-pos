package repository.custom;

import model.User;
import repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<User, String> {

    Long getRowCount();
}
