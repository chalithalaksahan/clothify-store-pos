package mapper;

import Entity.Inventory;
import Entity.Product;
import Entity.Variant;
import dto.ProductDTO;

import java.util.List;

public interface ProductMapper {
    // 1. Extract Parent Product
    Product toProductEntity(ProductDTO dto);

    // 2. Extract Variant and link to Product
    Variant toVariantEntity(ProductDTO dto, Product parentProduct);

    // 3. Extract Inventory math and link to Variant
    Inventory toInventoryEntity(ProductDTO dto, Variant parentVariant);

    // 4. Combine all three back into a UI DTO
    ProductDTO toDto(Product product, Variant variant, Inventory inventory);

    List<ProductDTO> toDtoList(List<Product> all);

    ProductDTO toDto(Product product);
}
