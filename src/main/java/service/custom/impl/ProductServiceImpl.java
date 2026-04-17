package service.custom.impl;

import Entity.*;
import dto.ProductDTO;
import jakarta.inject.Inject;
import mapper.ProductMapper;
import repository.custom.CategoryRepository;
import repository.custom.ProductRepository;
import repository.custom.SupplierRepository;
import repository.custom.VariantRepository;
import service.custom.ProductService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Inject
    ProductMapper productMapper;

    @Inject
    ProductRepository productRepository;

    @Inject
    VariantRepository variantRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    SupplierRepository supplierRepository;

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

            Category category = categoryRepository.getById(updatedDto.getCategory().getCategoryCode());
            Supplier supplier = supplierRepository.getById(updatedDto.getSupplier().getSupplierId());

            existingProduct.setCategory(category);
            existingProduct.setSupplier(supplier);

            existingProduct.setName(updatedDto.getName());
            existingProduct.setDescription(updatedDto.getDescription());

            existingVariant.setColor(updatedDto.getColor());
            existingVariant.setSize(updatedDto.getSize());
            existingVariant.setUnitCost(new BigDecimal(updatedDto.getCostPrice()));
            existingVariant.setRetailPrice(new BigDecimal(updatedDto.getSellingPrice()));

            existingInventory.setQtyOnHand(Integer.parseInt(updatedDto.getQtyOnHand()));
            existingInventory.setMinQty(Integer.parseInt(updatedDto.getMinQty()));
            existingInventory.setReorderLevel(Integer.parseInt(updatedDto.getReOrderLvl()));

            boolean isProductUpdated = productRepository.update(existingProduct);
            boolean isVariantUpdated = variantRepository.update(existingVariant);

            // 3. Send it to the repository to be merged!
            return isProductUpdated && isVariantUpdated;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(String skuCode) {
        Variant variant = variantRepository.findBySku(skuCode);
        if (variant == null) {
            return false;
        }
            Product product = variant.getProduct();
        return productRepository.deleteById(String.valueOf(product.getId()));
    }

    @Override
    public ProductDTO searchProduct(String skuCode) {
        Variant variant = variantRepository.findBySku(skuCode);
        if (variant == null) {
            return null;
        }
        Product product = variant.getProduct();
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() throws SQLException {
        productRepository.getAll(). stream()
                .flatMap(product -> product.getVariants().stream())
                .forEach(variant -> System.out.println("Variant SKU: " + variant.getSku()));
        return  productMapper.toDtoList(productRepository.getAll());
    }

    @Override
    public List<ProductDTO> searchByAll(String searchText) throws SQLException {
        // If search is empty, just return all products
        if (searchText == null || searchText.trim().isEmpty()) {
            return getAllProducts();
        }

        List<Product> products = productRepository.universalSearch(searchText);
        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product p : products) {
            for (Variant v : p.getVariants()) {
                // Mapping logic (use your existing mapper)
                dtoList.add(productMapper.toDto(p, v,v.getInventory()));
            }
        }
        return dtoList;
    }
}
