package service.custom.impl;

import Entity.*;
import dto.ProductDTO;
import jakarta.inject.Inject;
import mapper.ProductMapper;
import repository.custom.CategoryRepository;
import repository.custom.ProductRepository;
import repository.custom.VariantRepository;
import service.custom.ProductService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    @Inject
    ProductMapper productMapper;

    @Inject
    ProductRepository productRepository;

    @Inject
    VariantRepository variantRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Override
    public boolean createProduct(ProductDTO product) throws SQLException {
        Product productEntity = productMapper.toProductEntity(product);
        Variant variantEntity = productMapper.toVariantEntity(product, productEntity);
        Inventory inventoryEntity = productMapper.toInventoryEntity(product, variantEntity);

        Category realCategory = categoryRepository.getById(product.getCategory().getCategoryCode());
        if (realCategory == null) {
            System.out.println("Save failed: Category not found in database.");
            return false;
        }

        productEntity.setCategory(realCategory);

        variantEntity.setInventory(inventoryEntity);
        productEntity.getVariants().add(variantEntity);

        return productRepository.create(productEntity);
    }

    @Override
    public boolean updateProduct(ProductDTO updatedDto) {
        try {
            // 1. Fetch the EXISTING data using the SKU
            Variant existingVariant = variantRepository.findBySku(updatedDto.getSkuCode());

            if (existingVariant == null) {
                return false;
            }

            Product existingProduct = existingVariant.getProduct();
            Inventory existingInventory = existingVariant.getInventory();

            // 2. OVERWRITE the old data with the new DTO data
            existingProduct.setName(updatedDto.getName());
            existingProduct.setDescription(updatedDto.getDescription());

            existingVariant.setColor(updatedDto.getColor());
            existingVariant.setSize(updatedDto.getSize());
            existingVariant.setUnitCost(new BigDecimal(updatedDto.getCostPrice()));
            existingVariant.setRetailPrice(new BigDecimal(updatedDto.getSellingPrice()));

            existingInventory.setMinQty(Integer.parseInt(updatedDto.getMinQty()));
            existingInventory.setReorderLevel(Integer.parseInt(updatedDto.getReOrderLvl()));

            // 3. Send it to the repository to be merged!
            return productRepository.update(existingProduct);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


@Override
public boolean deleteProduct(String skuCode) {
    return false;
}

@Override
public ProductDTO searchProduct(String skuCode) {
    return null;
}

@Override
public List<ProductDTO> getAllProducts() {
    return List.of();
}
}
