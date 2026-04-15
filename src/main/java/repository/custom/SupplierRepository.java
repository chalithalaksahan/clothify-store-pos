package repository.custom;

import Entity.Supplier;
import repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier, String> {

    Long getRowCount();
}
