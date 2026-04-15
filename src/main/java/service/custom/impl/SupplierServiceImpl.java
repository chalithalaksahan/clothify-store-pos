package service.custom.impl;

import jakarta.inject.Inject;
import Entity.Supplier;
import repository.custom.SupplierRepository;
import service.custom.SupplierService;

import java.sql.SQLException;
import java.util.List;


public class SupplierServiceImpl implements SupplierService {

    @Inject
    SupplierRepository repositoryType;

    @Override
    public boolean addSupplier(Supplier supplier) {
        return repositoryType.create(supplier);
    }

    @Override
    public List<Supplier> getAll() throws SQLException {
        return repositoryType.getAll();
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        return repositoryType.update(supplier);
    }

    @Override
    public boolean deleteSupplier(String id) {
        return repositoryType.deleteById(id);
    }

    @Override
    public Supplier searchSupplier(String id) throws SQLException {
        return repositoryType.getById(id);
    }

    @Override
    public String getSupplierId() {
        Long rowCount = repositoryType.getRowCount();
        if (rowCount == null || rowCount == 0) {
            return "SUP-0001";
        } else {
            int newIdNum = rowCount.intValue()+1 ;
            return String.format("SUP-%04d", newIdNum);
        }
    }


}
