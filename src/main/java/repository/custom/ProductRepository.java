package repository.custom;

import Entity.Product;
import repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {


    List<Product> universalSearch(String searchText);
}
