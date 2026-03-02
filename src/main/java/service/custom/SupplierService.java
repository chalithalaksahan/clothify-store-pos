package service.custom;

import model.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierService {
    boolean addSupplier(Supplier supplier);

    List<Supplier> getAll() throws SQLException;

    boolean updateSupplier(Supplier supplier);

    boolean deleteSupplier(String id);

    Supplier searchSupplier(String id) throws SQLException;
}
