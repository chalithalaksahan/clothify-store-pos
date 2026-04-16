package service.custom;

import dto.ProductDTO;

import java.util.List;

public interface ProductService {
    boolean createProduct(ProductDTO product);

    boolean updateProduct(ProductDTO product);

    boolean deleteProduct(String skuCode);

    ProductDTO searchProduct(String skuCode);

    List<ProductDTO> getAllProducts();

}
