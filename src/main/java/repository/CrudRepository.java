package repository;

import Entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T,ID> extends SuperRepository {
    boolean create(T t);
    boolean update(T t);
    boolean deleteById(ID id);
    T getById(ID id) throws SQLException;
    List<T> getAll() throws SQLException;
}
