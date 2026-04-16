package repository.custom;

import Entity.User;
import repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<User, String> {

    Long getRowCount();
}
