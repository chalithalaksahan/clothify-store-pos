package service.custom;

import dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    boolean createProduct(ProductDTO product) throws SQLException;

    boolean updateProduct(ProductDTO product);

    boolean deleteProduct(String skuCode);

    ProductDTO searchProduct(String skuCode);

    List<ProductDTO> getAllProducts() throws SQLException;

    List<ProductDTO> searchByAll(String query) throws SQLException;
}
